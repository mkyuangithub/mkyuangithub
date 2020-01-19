package org.sky.tcc.icbc.dubbo;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.tcc.action.PlusMoneyAction;
import org.sky.tcc.dubbo.CMBTransferService;
import org.springframework.beans.factory.annotation.Autowired;

import io.seata.rm.tcc.api.BusinessActionContext;

@Service(version = "1.0.0", interfaceClass = CMBTransferService.class, timeout = 30000)
public class CMBTransferServiceImpl extends BaseService implements CMBTransferService {
	@Autowired
	private PlusMoneyAction plusMoneyAction;

	@Override
	public boolean transfer(long from, long to, double amount) throws DemoRpcRunTimeException {
		BusinessActionContext businessActionContext = new BusinessActionContext();
		Map<String, Object> actionContext = new HashMap();
		actionContext.put("accountNo", to);
		actionContext.put("amount", amount);
		businessActionContext.setActionContext(actionContext);
		boolean answer = false;
		answer = plusMoneyAction.prepareAdd(null, to, amount);
		if (!answer) {
			return plusMoneyAction.rollback(businessActionContext);
		}
		return plusMoneyAction.commit(businessActionContext);
	}

}
