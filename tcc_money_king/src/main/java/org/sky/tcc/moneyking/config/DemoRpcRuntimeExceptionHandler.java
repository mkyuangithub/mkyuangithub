package org.sky.tcc.moneyking.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.sky.exception.DemoRpcRunTimeException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoRpcRuntimeExceptionHandler {
	protected Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * service层的RuntimeException统一处理器
	 * 可以将RuntimeException分装成RpcRuntimeException抛给调用端处理 或自行处理
	 * 
	 * @param exception
	 */
	@AfterThrowing(throwing = "exception", pointcut = "execution(* org.sky.service.*.*(..))")
	public void afterThrow(Throwable exception) {
		if (exception instanceof RuntimeException) {
			logger.error("DemoRpcRuntimeExceptionHandler side->exception occured: " + exception.getMessage(),
					exception);
			throw new DemoRpcRunTimeException(exception);
		}
		// logger.error("DemoRpcRuntimeExceptionHandler side->exception occured: " +
		// exception.getMessage(), exception);
	}
}
