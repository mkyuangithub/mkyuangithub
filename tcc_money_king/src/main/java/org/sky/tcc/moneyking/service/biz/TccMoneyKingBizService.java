package org.sky.tcc.moneyking.service.biz;

import org.sky.exception.DemoRpcRunTimeException;

public interface TccMoneyKingBizService {
	public boolean transfer(long from, long to, double amount) throws DemoRpcRunTimeException;
}
