package org.sky.tcc.moneyking.service.biz;

import org.sky.exception.DemoRpcRunTimeException;

public interface TccMoneyKingBizService {
	public boolean transfer(String from, String to, double amount) throws DemoRpcRunTimeException;

	public void cmbHello() throws DemoRpcRunTimeException;

	public void icbcHello() throws DemoRpcRunTimeException;
}
