package org.sky.tcc.moneyking.service.biz;

import org.apache.dubbo.config.annotation.Reference;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.tcc.dubbo.CMBTransferService;
import org.sky.tcc.dubbo.ICBCTransferService;
import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class TccMoneyKingBizServiceImpl extends BaseService implements TccMoneyKingBizService {
	@Reference(version = "1.0.0")
	private ICBCTransferService icbcTransferService;

	@Reference(version = "1.0.0")
	private CMBTransferService cmbTransferService;

	@GlobalTransactional
	@Override
	public boolean transfer(long from, long to, double amount) throws DemoRpcRunTimeException {
		boolean answer = icbcTransferService.transfer(from, to, amount);
		if (!answer) {
			// 扣钱参与者，一阶段失败; 回滚本地事务和分布式事务
			throw new DemoRpcRunTimeException("账号:[" + from + "] 预扣款失败");
		}
		// 加钱参与者，一阶段执行
		answer = cmbTransferService.transfer(from, to, amount);

		if (!answer) {
			throw new DemoRpcRunTimeException("账号:[" + to + "] 预收款失败");
		}
		return true;
	}

}
