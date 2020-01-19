package org.sky.service;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.platform.util.DubboResponse;
import org.sky.vo.ProductVO;

public interface ProductStockBusinessService {
	public ProductVO addProductAndStock(ProductVO prod) throws DemoRpcRunTimeException;
}
