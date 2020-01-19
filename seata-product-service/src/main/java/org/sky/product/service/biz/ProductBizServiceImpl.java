package org.sky.product.service.biz;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.product.dao.ProductDAO;
import org.sky.product.service.biz.ProductBizService;
import org.sky.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBizServiceImpl extends BaseService implements ProductBizService {
	@Autowired
	ProductDAO productDAO;

	@Override
	public long addProduct(String productName) throws DemoRpcRunTimeException {
		long newProdId = 0;
		try {
			newProdId = productDAO.addNewProduct(productName);
		} catch (Exception e) {
			logger.error("error occured on Biz Service Side: " + e.getMessage(), e);
			throw new DemoRpcRunTimeException("error occured on Biz Service Side: " + e.getMessage(), e);
		}
		return newProdId;
	}

}
