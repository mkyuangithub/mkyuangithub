package org.sky.stock.service.biz;

import org.sky.exception.DemoRpcRunTimeException;

public interface StockBizService {
	public void addStock(long productId, int stock) throws DemoRpcRunTimeException;
}
