package org.sky.tcc.dubbo;

public interface CMBTransferService {
	public boolean transfer(long from, long to, double amount);
}
