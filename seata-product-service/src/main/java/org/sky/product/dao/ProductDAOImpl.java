package org.sky.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.sky.dao.BaseDAO;
import org.sky.exception.DemoRpcRunTimeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductDAOImpl extends BaseDAO implements ProductDAO {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public long addNewProduct(String productName) throws DemoRpcRunTimeException {
		String prodSql = "insert into t_product(product_name)values(?)";
		long newProdId = 0;
		try {

			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(prodSql, new String[] { "id" });
					ps.setString(1, productName);
					return ps;
				}
			}, keyHolder);
			newProdId = keyHolder.getKey().longValue();
		} catch (Exception e) {
			throw new DemoRpcRunTimeException(
					"error occured on insert product with: product_id->\" + newProdId + \" and productname->\"\r\n"
							+ "					+ productName" + e.getMessage(),
					e);
		}
		return newProdId;
	}

}
