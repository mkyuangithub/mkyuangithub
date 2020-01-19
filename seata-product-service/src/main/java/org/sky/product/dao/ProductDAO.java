package org.sky.product.dao;

import org.sky.exception.DemoRpcRunTimeException;

public interface ProductDAO {

	public long addNewProduct(String productName) throws DemoRpcRunTimeException;
}
