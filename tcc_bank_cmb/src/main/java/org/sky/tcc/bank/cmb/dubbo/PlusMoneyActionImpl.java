package org.sky.tcc.bank.cmb.dubbo;

import org.sky.service.BaseService;
import org.sky.tcc.bank.cmb.dao.TransferMoneyDAO;
import org.sky.tcc.bean.AccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;

public class PlusMoneyActionImpl extends BaseService implements PlusMoneyAction {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TransferMoneyDAO transferMoneyDAO;

	@Override
	public String sayHello() throws RuntimeException {
		return "hi I am cmb-dubbo";
	}

	@Override
	public boolean prepareAdd(BusinessActionContext businessActionContext, String accountNo, double amount) {
		logger.info("======>inti prepare add");
		final String xid = RootContext.getXID();
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					// 校验账户
					AccountBean account = transferMoneyDAO.getAccountForUpdate(accountNo);
					if (account == null) {
						logger.info(
								"======>prepareAdd: 账户[" + accountNo + "]不存在, txId:" + businessActionContext.getXid());
						return false;
					}
					// 待转入资金作为 不可用金额
					double freezedAmount = account.getFreezedAmount() + amount;
					account.setFreezedAmount(freezedAmount);
					transferMoneyDAO.updateFreezedAmount(account);
					logger.info(String.format(
							"PlusMoneyActionImpl.prepareAdd account[%s] amount[%f], dtx transaction id: %s.", accountNo,
							amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in PlusMoneyActionImpl.prepareAdd: " + t.getMessage(),
							t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	@Override
	public boolean commit(BusinessActionContext businessActionContext) {
		logger.info("======>into PlusMoneyActionImpl.commit() method");
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
					// 加钱
					double newAmount = account.getAmount() + amount;
					account.setAmount(newAmount);
					// 冻结金额 清除
					account.setFreezedAmount(account.getFreezedAmount() - amount);
					transferMoneyDAO.updateAmount(account);

					logger.info(String.format("======>add account[%s] amount[%f], dtx transaction id: %s.", accountNo,
							amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in PlusMoneyActionImpl.commit: " + t.getMessage(), t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

	@Override
	public boolean rollback(BusinessActionContext businessActionContext) {
		logger.info("======>into PlusMoneyActionImpl.rollback() method");
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
						// 账户不存在, 无需回滚动作
						return true;
					}
					// 冻结金额 清除
					account.setFreezedAmount(account.getFreezedAmount() - amount);
					transferMoneyDAO.updateFreezedAmount(account);

					logger.info(String.format("======>Undo account[%s] amount[%f], dtx transaction id: %s.", accountNo,
							amount, xid));
					return true;
				} catch (Throwable t) {
					logger.error("======>error occured in PlusMoneyActionImpl.rollback: " + t.getMessage(),
							t.getCause());
					status.setRollbackOnly();
					return false;
				}
			}
		});
	}

}
