package org.sky.tcc.icbc;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.sky.tcc" })
public class TCCBankCMBApplication {

	public static void main(String[] args) {
		SpringApplication.run(TCCBankCMBApplication.class, args);
	}

}
