package org.sky.service;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", interfaceClass = HelloNacosService.class, timeout = 120000)
public class HelloNacosServiceImpl extends BaseService implements HelloNacosService {

	public String sayHello(String name) {
		return "hello: " + name;
	}
}
