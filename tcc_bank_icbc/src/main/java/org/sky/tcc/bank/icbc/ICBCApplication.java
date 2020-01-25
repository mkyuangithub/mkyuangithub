package org.sky.tcc.bank.icbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.sky.tcc.bank" })
public class ICBCApplication {

	public static void main(String[] args) {
		SpringApplication.run(ICBCApplication.class, args);

	}

}
