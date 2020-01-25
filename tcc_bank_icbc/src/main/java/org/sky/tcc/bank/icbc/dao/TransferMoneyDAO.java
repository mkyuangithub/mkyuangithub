package org.sky.tcc.bank.icbc.dao;

import org.sky.tcc.bean.AccountBean;

public interface TransferMoneyDAO {
	public void addAccount(AccountBean account) throws Exception;

	public int updateAmount(AccountBean account) throws Exception;

	public AccountBean getAccount(String accountNo) throws Exception;

	public AccountBean getAccountForUpdate(String accountNo) throws Exception;

	public int updateFreezedAmount(AccountBean account) throws Exception;
}
