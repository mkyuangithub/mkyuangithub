package org.sky.product.service.dubbo;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.vo.ProductVO;

public interface ProductDubboService {
	public long addProduct(String productName) throws DemoRpcRunTimeException;
}
