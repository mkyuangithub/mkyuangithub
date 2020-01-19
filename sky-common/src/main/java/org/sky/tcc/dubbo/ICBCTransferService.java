package org.sky.tcc.dubbo;

public interface ICBCTransferService {
	public boolean transfer(long from, long to, double amount);
}
