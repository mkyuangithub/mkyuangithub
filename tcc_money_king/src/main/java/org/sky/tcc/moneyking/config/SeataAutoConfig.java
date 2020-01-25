package org.sky.tcc.moneyking.config;

import io.seata.spring.annotation.GlobalTransactionScanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "spring/dubbo-reference.xml" })
public class SeataAutoConfig {
	@Bean
	public GlobalTransactionScanner globalTransactionScanner() {
		return new GlobalTransactionScanner("tcc-bank-sample", "demo-tx-grp");
	}
}
