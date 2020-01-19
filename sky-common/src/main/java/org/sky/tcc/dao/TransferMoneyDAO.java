package org.sky.tcc.dao;

import org.sky.tcc.bean.AccountBean;

public interface TransferMoneyDAO {
	public void addAccount(AccountBean account) throws Exception;

	public int updateAmount(AccountBean account) throws Exception;

	public AccountBean getAccount(long accountNo) throws Exception;

	public AccountBean getAccountForUpdate(long accountNo) throws Exception;

	public int updateFreezedAmount(AccountBean account) throws Exception;
}
