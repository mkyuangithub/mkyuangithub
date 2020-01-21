package org.sky.tcc.moneyking;

import org.sky.tcc.moneyking.service.biz.TccMoneyKingBizService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransferApplication {

	protected static ApplicationContext applicationContext;

	protected static TccMoneyKingBizService transferService;

	public static void main(String[] args) {
		try {
			applicationContext = new ClassPathXmlApplicationContext("spring/seata-dubbo-reference.xml");
			transferService = (TccMoneyKingBizService) applicationContext.getBean("tccMoneyKingBizService");
			transferService.transfer("a", "b", 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
