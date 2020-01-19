package org.sky.service;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.vo.ProductVO;

public interface StockService {

	public int addStock(ProductVO prod) throws DemoRpcRunTimeException;
}
