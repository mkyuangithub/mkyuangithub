package org.sky.tcc.bank.icbc.dubbo;

import org.sky.service.BaseService;
import org.sky.tcc.bank.icbc.dao.TransferMoneyDAO;
import org.sky.tcc.bean.AccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;

public class MinusMoneyActionImpl extends BaseService implements MinusMoneyAction {
	/**
	 * 扣钱账户 DAO
	 */
	@Autowired
	private TransferMoneyDAO transferMoneyDAO;

	/**
	 * 扣钱数据源事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public String sayHello() throws RuntimeException {
		return "hi I am icbc-dubbo";
	}

	@Override
	public boolean prepareMinus(BusinessActionContext businessActionContext, String accountNo, double amount) {
		logger.info("==========into prepareMinus");
		// 分布式事务ID
		final String xid = RootContext.getXID();

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					// 校验账户余额
					AccountBean account = transferMoneyDAO.getAccountForUpdate(accountNo);
					if (account == null) {
						throw new RuntimeException("账户不存在");
					}
					if (account.getAmount() - amount < 0) {
						throw new RuntimeException("余额不足");
					}
					// 冻结转账金额
					double freezedAmount = account.getFreezedAmount() + amount;
					account.setFreezedAmount(freezedAmount);
					transferMoneyDAO.updateFreezedAmount(account);
					logger.info(String.format("======>prepareMinus account[%s] amount[%f], dtx transaction id: %s.",
							accountNo, amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in MinusMoneyActionImpl.prepareMinus: " + t.getMessage(),
							t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	@Override
	public boolean commit(BusinessActionContext businessActionContext) {
		logger.info("======>into MinusMoneyActionImpl.commit() method");
		// 分布式事务ID
		final String xid = RootContext.getXID();
		// 账户ID
		final String accountNo = String.valueOf(businessActionContext.getActionContext("accountNo"));
		// 转出金额
		final double amount = Double.valueOf(String.valueOf(businessActionContext.getActionContext("amount")));
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					AccountBean account = transferMoneyDAO.getAccountForUpdate(accountNo);
					// 扣除账户余额
					double newAmount = account.getAmount() - amount;
					if (newAmount < 0) {
						throw new RuntimeException("余额不足");
					}
					account.setAmount(newAmount);
					// 释放账户 冻结金额
					account.setFreezedAmount(account.getFreezedAmount() - amount);
					transferMoneyDAO.updateAmount(account);
					logger.info(String.format("======>minus account[%s] amount[%f], dtx transaction id: %s.", accountNo,
							amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in MinusMoneyActionImpl.commit: " + t.getMessage(),
							t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	@Override
	public boolean rollback(BusinessActionContext businessActionContext) {
		logger.info("======>into MinusMoneyActionImpl.rollback() method");
		// 分布式事务ID
		final String xid = RootContext.getXID();
		// 账户ID
		final String accountNo = String.valueOf(businessActionContext.getActionContext("accountNo"));
		// 转出金额
		final double amount = Double.valueOf(String.valueOf(businessActionContext.getActionContext("amount")));
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					AccountBean account = transferMoneyDAO.getAccountForUpdate(accountNo);
					if (account == null) {
						// 账户不存在，回滚什么都不做
						return true;
					}
					// 释放冻结金额
					if (account.getFreezedAmount() >= amount) {
						account.setFreezedAmount(account.getFreezedAmount() - amount);
						transferMoneyDAO.updateFreezedAmount(account);
					}
					logger.info(
							String.format("======>Undo prepareMinus account[%s] amount[%f], dtx transaction id: %s.",
									accountNo, amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in MinusMoneyActionImpl.rollback: " + t.getMessage(),
							t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

}
