package org.sky.product.service.biz;

import org.sky.exception.DemoRpcRunTimeException;

public interface ProductBizService {
	public long addProduct(String productName) throws DemoRpcRunTimeException;
}
