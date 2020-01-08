package org.sky.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sky.platform.util.DubboResponse;
import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;

@Component
@Aspect
public class ServiceExceptionHandler {
	protected Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * 返回值类型为Response的Service
	 */
	@Pointcut(value = "execution(* org.sky.service.*.*(..))")
	private void servicePointcut() {
	}

	/**
	 * 任何持有@Transactional注解的方法
	 */
	@Pointcut(value = "@annotation(org.springframework.transaction.annotation.Transactional)")
	private void transactionalPointcut() {
	}

	/**
	 * 异常处理切面 将异常包装为Response，避免dubbo进行包装
	 *
	 * @param pjp 处理点
	 * @return Object
	 */
	@Around("servicePointcut() && !transactionalPointcut()")
	public Object doAround(ProceedingJoinPoint pjp) {
		Object[] args = pjp.getArgs();
		try {
			return pjp.proceed();
		} catch (Exception e) {
			processException(pjp, args, e);
			return DubboResponse.error("exception occured on dubbo service side: " + e.getMessage());
		} catch (Throwable throwable) {
			processException(pjp, args, throwable);
			return DubboResponse.error("exception occured on dubbo service side: " + throwable.getMessage());
		}
	}

	/**
	 * 任何持有@Transactional注解的方法异常处理切面 将自定义的业务异常转为RuntimeException:
	 * 1.规避dubbo的包装，让customer可以正常获取message 2.抛出RuntimeException使事务可以正确回滚 其他异常不处理
	 *
	 * @param pjp 处理点
	 * @return Object
	 */
	@Around("servicePointcut() && transactionalPointcut()")
	public Object doTransactionalAround(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (Exception e) {
			Object[] args = pjp.getArgs();
			// dubbo会将异常捕获进行打印，这里就不打印了
			processException(pjp, args, e);
			// logger.error("service with @Transactional exception occured on dubbo service
			// side: " + e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 处理异常
	 *
	 * @param joinPoint 切点
	 * @param args      参数
	 * @param throwable 异常
	 */
	private void processException(final ProceedingJoinPoint joinPoint, final Object[] args, Throwable throwable) {
		String inputParam = "";
		if (args != null && args.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (Object arg : args) {
				sb.append(",");
				sb.append(arg);
			}
			inputParam = sb.toString().substring(1);
		}
		logger.error("\n 方法: {}\n 入参: {} \n 错误信息: {}", joinPoint.toLongString(), inputParam,
				Throwables.getStackTraceAsString(throwable));
	}
}
