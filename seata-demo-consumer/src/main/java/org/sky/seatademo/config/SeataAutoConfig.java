package org.sky.seatademo.config;

import io.seata.spring.annotation.GlobalTransactionScanner;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SeataAutoConfig {

	/**
	 * init global transaction scanner
	 *
	 * @Return: GlobalTransactionScanner
	 */
	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		return new GlobalTransactionScanner("demo-seata-consumer", "demo-tx-grp");
	}
}
