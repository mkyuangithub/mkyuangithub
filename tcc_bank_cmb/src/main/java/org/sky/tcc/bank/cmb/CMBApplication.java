package org.sky.tcc.bank.cmb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.sky.tcc.bank" })
public class CMBApplication {

	public static void main(String[] args) {
		SpringApplication.run(CMBApplication.class, args);

	}

}
