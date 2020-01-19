package org.sky.stock.dao;

import org.sky.exception.DemoRpcRunTimeException;

public interface StockDAO {

	public void addNewStock(long productId, int stock) throws DemoRpcRunTimeException;
}
