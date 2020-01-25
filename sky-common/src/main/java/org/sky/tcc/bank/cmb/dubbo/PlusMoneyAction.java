package org.sky.tcc.bank.cmb.dubbo;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

public interface PlusMoneyAction {
	public String sayHello() throws RuntimeException;

	/**
	 * 一阶段给To帐户加钱
	 * 
	 * @param businessActionContext
	 * @param accountNo
	 * @param amount
	 */
	@TwoPhaseBusinessAction(name = "plusMoneyAction", commitMethod = "commit", rollbackMethod = "rollback")
	public boolean prepareAdd(BusinessActionContext businessActionContext,
			@BusinessActionContextParameter(paramName = "accountNo") String accountNo,
			@BusinessActionContextParameter(paramName = "amount") double amount);

	/**
	 * 二阶段提交
	 * 
	 * @param businessActionContext
	 * @return
	 */
	public boolean commit(BusinessActionContext businessActionContext);

	/**
	 * 二阶段回滚
	 * 
	 * @param businessActionContext
	 * @return
	 */
	public boolean rollback(BusinessActionContext businessActionContext);
}
