package org.sky.stock.service.biz;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.service.BaseService;
import org.sky.stock.dao.StockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockBizServiceImpl extends BaseService implements StockBizService {
	@Autowired
	private StockDAO stockDAO;

	@Override
	public void addStock(long productId, int stock) throws DemoRpcRunTimeException {
		try {
			stockDAO.addNewStock(productId, stock);
		} catch (Exception e) {
			throw new DemoRpcRunTimeException("error occured on Biz Service Side: " + e.getMessage(), e);
		}
	}

}
