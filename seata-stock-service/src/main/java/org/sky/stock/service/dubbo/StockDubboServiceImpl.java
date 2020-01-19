package org.sky.stock.service.dubbo;

import org.apache.dubbo.config.annotation.Service;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.stock.service.biz.StockBizService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0", interfaceClass = StockDubboService.class, timeout = 30000,loadbalance = "roundrobin")
public class StockDubboServiceImpl extends BaseService implements StockDubboService {

	@Autowired
	private StockBizService stockBizService;

	@Override
	public void addStock(long productId, int stock) throws DemoRpcRunTimeException {
		try {
			stockBizService.addStock(productId, stock);
			logger.info("======>insert into t_stock with successful\n data:\n   productid: " + productId
					+ "\n   stock: " + stock);
		} catch (Exception e) {
			logger.error("error occured on insert stock with productid->: " + productId + " and stock->" + stock
					+ e.getMessage(), e);
			throw new DemoRpcRunTimeException("error occured on insert stock with productid->: " + productId
					+ " and stock->" + stock + e.getMessage(), e);
		}
	}

}
