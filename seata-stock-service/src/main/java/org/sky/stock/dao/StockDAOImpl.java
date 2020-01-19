package org.sky.stock.dao;

import javax.annotation.Resource;

import org.sky.dao.BaseDAO;
import org.sky.exception.DemoRpcRunTimeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockDAOImpl extends BaseDAO implements StockDAO {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addNewStock(long productId, int stock) throws DemoRpcRunTimeException {
		String prodSql = "insert into t_stock(product_id,stock)values(?,?)";
		try {
			jdbcTemplate.update(prodSql, productId, stock);
		} catch (Exception e) {
			throw new DemoRpcRunTimeException(e.getMessage(), e);
		}
	}

}
