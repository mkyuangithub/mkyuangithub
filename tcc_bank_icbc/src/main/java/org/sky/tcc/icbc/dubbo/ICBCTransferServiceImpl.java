package org.sky.tcc.icbc.dubbo;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.tcc.action.MinusMoneyAction;
import org.sky.tcc.dubbo.ICBCTransferService;
import org.springframework.beans.factory.annotation.Autowired;

import io.seata.rm.tcc.api.BusinessActionContext;

@Service(version = "1.0.0", interfaceClass = ICBCTransferService.class, timeout = 30000)
public class ICBCTransferServiceImpl extends BaseService implements ICBCTransferService {
	@Autowired
	private MinusMoneyAction minusMoneyAction;

	@Override
	public boolean transfer(long from, long to, double amount) throws DemoRpcRunTimeException {
		BusinessActionContext businessActionContext = new BusinessActionContext();
		Map<String, Object> actionContext = new HashMap();
		actionContext.put("accountNo", from);
		actionContext.put("amount", amount);
		businessActionContext.setActionContext(actionContext);
		boolean answer = false;
		answer = minusMoneyAction.prepareMinus(null, from, amount);
		if (!answer) {
			return minusMoneyAction.rollback(businessActionContext);
		}
		return minusMoneyAction.commit(businessActionContext);

	}

}
