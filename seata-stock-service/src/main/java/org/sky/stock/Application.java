package org.sky.stock;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.sky.stock" })

public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
