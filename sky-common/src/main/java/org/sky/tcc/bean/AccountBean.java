package org.sky.tcc.bean;

import java.io.Serializable;

public class AccountBean implements Serializable {
	/**
	 * 账户
	 */
	private long accountId;
	/**
	 * 余额
	 */
	private double amount;
	/**
	 * 冻结金额
	 */
	private double freezedAmount;

	public double getAmount() {
		return amount;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFreezedAmount() {
		return freezedAmount;
	}

	public void setFreezedAmount(double freezedAmount) {
		this.freezedAmount = freezedAmount;
	}

}
