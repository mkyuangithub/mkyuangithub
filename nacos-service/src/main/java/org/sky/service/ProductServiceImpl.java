package org.sky.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.dubbo.config.annotation.Service;
import org.sky.exception.DemoRpcRunTimeException;
import org.sky.platform.util.DubboResponse;
import org.sky.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

@Service(version = "1.0.0", interfaceClass = ProductService.class, timeout = 120000)
public class ProductServiceImpl extends BaseService implements ProductService {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public DubboResponse<ProductVO> addProductAndStock(ProductVO prod) throws DemoRpcRunTimeException {
		DubboResponse<ProductVO> response = null;
		int newProdId = 0;
		String prodSql = "insert into t_product(product_name)values(?)";
		String stockSql = "insert into t_stock(product_id,stock)values(?,?)";
		try {
			if (prod != null) {
				KeyHolder keyHolder = new GeneratedKeyHolder();

				jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(prodSql, new String[] { "id" });
						ps.setString(1, prod.getProductName());
						return ps;
					}
				}, keyHolder);
				newProdId = keyHolder.getKey().intValue();
				logger.info("======>insert into t_product with product_id:" + newProdId);
				if (newProdId > 0) {
					jdbcTemplate.update(stockSql, newProdId, prod.getStock());
					logger.info("======>insert into t_stock with successful");
					ProductVO returnData = new ProductVO();
					returnData.setProductId(newProdId);
					returnData.setProductName(prod.getProductName());
					returnData.setStock(prod.getStock());
					//response = new DubboResponse(HttpStatus.OK.value(), "success", returnData);
					throw new Exception("Mk throwed exception to enforce rollback[insert into t_stock]");
					//return response;
				}

			} else {
				throw new DemoRpcRunTimeException("error occured on ProductVO is null");
			}
		} catch (Exception e) {
			logger.error("error occured on Dubbo Service Side: " + e.getMessage(), e);
			throw new DemoRpcRunTimeException("error occured on Dubbo Service Side: " + e.getMessage(), e);
		}
		return response;
	}

}
