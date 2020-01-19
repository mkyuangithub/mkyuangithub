package org.sky.stock.service.dubbo;

import org.sky.exception.DemoRpcRunTimeException;

public interface StockDubboService {
	public void addStock(long productId, int stock) throws DemoRpcRunTimeException;
}
