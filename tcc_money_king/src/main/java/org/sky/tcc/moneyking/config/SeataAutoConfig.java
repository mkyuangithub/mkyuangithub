package org.sky.tcc.moneyking.config;

import io.seata.spring.annotation.GlobalTransactionScanner;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "spring/seata-dubbo-reference.xml" })
public class SeataAutoConfig {
}
