package org.sky.seatademo.service.biz;

import org.apache.dubbo.config.annotation.Reference;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.product.service.dubbo.ProductDubboService;
import org.sky.seatademo.vo.SeataProductVO;
import org.sky.service.BaseService;
import org.sky.stock.service.dubbo.StockDubboService;
import org.springframework.stereotype.Service;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

@Service
public class BusinessDubboServiceImpl extends BaseService implements BusinessDubboService {
	@Reference(version = "1.0.0")
	private ProductDubboService productDubboService;
	@Reference(version = "1.0.0")
	private StockDubboService stockDubboService;

	@Override
	@GlobalTransactional(timeoutMills = 300000, name = "demo-seata-consumer")
	public SeataProductVO addProductAndStock(SeataProductVO vo) throws DemoRpcRunTimeException {
		SeataProductVO rtnProduct = new SeataProductVO();
		try {
			//logger.info("======>start global transaction: " + RootContext.getXID());
			long prodId = productDubboService.addProduct(vo.getProductName());
			stockDubboService.addStock(prodId, vo.getStock());
			rtnProduct.setProductId(prodId);
			rtnProduct.setProductName(vo.getProductName());
			rtnProduct.setStock(vo.getStock());
			return rtnProduct;
		} catch (Exception e) {
			logger.error("error occured on dubbo BusinessService side: " + e.getMessage(), e);
			throw new DemoRpcRunTimeException("error occured on dubbo BusinessService side: " + e.getMessage(), e);
		}
	}

	@Override
	@GlobalTransactional(timeoutMills = 120000, name = "demo-seata-consumer")
	public SeataProductVO addProductAndStockFailed(SeataProductVO vo) throws DemoRpcRunTimeException {
		SeataProductVO rtnProduct = new SeataProductVO();
		try {
			logger.info("======>start global transaction: " + RootContext.getXID());
			long prodId = productDubboService.addProduct(vo.getProductName());
			stockDubboService.addStock(prodId, vo.getStock());
			rtnProduct.setProductId(prodId);
			rtnProduct.setProductName(vo.getProductName());
			rtnProduct.setStock(vo.getStock());
			throw new Exception("Mk throw the exception to enforce rollback all transaction");
		} catch (Exception e) {
			logger.error("error occured on dubbo BusinessService side: " + e.getMessage(), e);
			throw new DemoRpcRunTimeException("error occured on dubbo BusinessService side: " + e.getMessage(), e);
		}
	}
}
