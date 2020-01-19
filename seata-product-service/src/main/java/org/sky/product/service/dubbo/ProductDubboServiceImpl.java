package org.sky.product.service.dubbo;

import org.apache.dubbo.config.annotation.Service;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.product.service.biz.ProductBizService;
import org.sky.product.service.dubbo.ProductDubboService;
import org.sky.service.BaseService;
import org.sky.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0", interfaceClass = ProductDubboService.class, timeout = 30000, loadbalance = "roundrobin")
public class ProductDubboServiceImpl extends BaseService implements ProductDubboService {

	@Autowired
	ProductBizService productBizService;

	@Override
	public long addProduct(String productName) throws DemoRpcRunTimeException {
		long result = 0;
		try {
			result = productBizService.addProduct(productName);
			logger.info("======>insert into t_product with product_id->" + result + " and productname->" + productName
					+ " successfully");
		} catch (Exception e) {
			logger.error("error occured on insert product with: product_id->\" + newProdId + \" and productname->\"\r\n"
					+ "					+ productName" + e.getMessage(), e);
			throw new DemoRpcRunTimeException(
					"error occured on insert product with: product_id->\" + newProdId + \" and productname->\"\r\n"
							+ "					+ productName" + e.getMessage(),
					e);
		}
		return result;
	}

}
