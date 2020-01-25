package org.sky.tcc.moneyking.service.biz;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.tcc.bank.cmb.dubbo.PlusMoneyAction;
import org.sky.tcc.bank.icbc.dubbo.MinusMoneyAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class TccMoneyKingBizServiceImpl extends BaseService implements TccMoneyKingBizService {

	@Autowired
	private MinusMoneyAction minusMoneyAction;
	
	@Autowired
	private PlusMoneyAction plusMoneyAction;

	@Override
	@GlobalTransactional(timeoutMills = 300000, name = "tcc-bank-sample")
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
