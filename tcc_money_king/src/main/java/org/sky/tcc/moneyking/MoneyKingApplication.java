package org.sky.tcc.moneyking;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@ServletComponentScan
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.sky.tcc" })

public class MoneyKingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyKingApplication.class, args);
	}

}
