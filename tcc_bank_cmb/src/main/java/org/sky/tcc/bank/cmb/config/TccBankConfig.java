package org.sky.tcc.bank.cmb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.spring.annotation.GlobalTransactionScanner;

@Configuration
@ImportResource(locations = { "spring/spring-bean.xml", "spring/dubbo-bean.xml" })
public class TccBankConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DruidDataSource druidDataSource() {
		return new DruidDataSource();

	}

	@Bean
	public DataSourceTransactionManager transactionManager(DruidDataSource druidDataSource) {
		return new DataSourceTransactionManager(druidDataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DruidDataSource druidDataSource) {
		return new JdbcTemplate(druidDataSource);
	}

	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		return new GlobalTransactionScanner("tcc-bank-cmb", "demo-tx-grp");
	}

}
