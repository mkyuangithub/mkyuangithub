package org.sky.tcc.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

public interface PlusMoneyAction {
	/**
	 * 一阶段给To帐户加钱
	 * 
	 * @param businessActionContext
	 * @param accountNo
	 * @param amount
	 */
	@TwoPhaseBusinessAction(name = "secondTccAction", commitMethod = "commit", rollbackMethod = "rollback")
	public boolean prepareAdd(BusinessActionContext businessActionContext,
			@BusinessActionContextParameter(paramName = "accountNo") long accountNo,
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
