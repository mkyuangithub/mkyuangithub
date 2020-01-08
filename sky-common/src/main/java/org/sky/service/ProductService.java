package org.sky.service;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.platform.util.DubboResponse;
import org.sky.vo.ProductVO;

public interface ProductService {
	public DubboResponse addProductAndStock(ProductVO prod) throws DemoRpcRunTimeException;
}
