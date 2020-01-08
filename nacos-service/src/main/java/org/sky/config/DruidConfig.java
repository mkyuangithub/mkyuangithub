package org.sky.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
@EnableAutoConfiguration
public class DruidConfig {
	/*
	 * @Value("${spring.datasource.url}") private String dbUrl;
	 * 
	 * @Value("${spring.datasource.username}") private String username;
	 * 
	 * @Value("${spring.datasource.password}") private String password;
	 * 
	 * @Value("${spring.datasource.driverClassName}") private String
	 * driverClassName;
	 */

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource dataSource() {
		// DruidDataSource datasource = new DruidDataSource();
		return new DruidDataSource();
		/*
		 * datasource.setUrl(this.dbUrl); datasource.setUsername(username);
		 * datasource.setPassword(password);
		 * datasource.setDriverClassName(driverClassName); return datasource;
		 */
	}
}
