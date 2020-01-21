package org.sky.tcc.moneyking.service.biz;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.tcc.bankprovider.dubbo.PlusMoneyAction;
import org.sky.tcc.bankprovider.dubbo.MinusMoneyAction;
import org.springframework.beans.factory.annotation.Autowired;
import io.seata.spring.annotation.GlobalTransactional;

public class TccMoneyKingBizServiceImpl extends BaseService implements TccMoneyKingBizService {

	public void setMinusMoneyAction(MinusMoneyAction minusMoneyAction) {
		this.minusMoneyAction = minusMoneyAction;
	}

	public void setPlusMoneyAction(PlusMoneyAction plusMoneyAction) {
		this.plusMoneyAction = plusMoneyAction;
	}

	private MinusMoneyAction minusMoneyAction;

	private PlusMoneyAction plusMoneyAction;

	public void cmbHello() throws DemoRpcRunTimeException {
		logger.info(plusMoneyAction.sayHello());
	}

	public void icbcHello() throws DemoRpcRunTimeException {
		logger.info(minusMoneyAction.sayHello());
	}

	@Override
	@GlobalTransactional(timeoutMills = 300000, name = "tcc-bank-provider")
	public boolean transfer(String from, String to, double amount) throws DemoRpcRunTimeException {

		boolean answer = minusMoneyAction.prepareMinus(null, from, amount);
		if (!answer) {
			// 扣钱参与者，一阶段失败; 回滚本地事务和分布式事务
			throw new DemoRpcRunTimeException("账号:[" + from + "] 预扣款失败");
		}
		// 加钱参与者，一阶段执行
		answer = plusMoneyAction.prepareAdd(null, to, amount);

		if (!answer) {
			throw new DemoRpcRunTimeException("账号:[" + to + "] 预收款失败");
		}
		return true;
	}

}
