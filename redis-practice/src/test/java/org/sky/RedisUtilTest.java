package org.sky;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sky.Application;
import org.sky.config.RedisConfig;
import org.sky.platform.util.RedisUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.sky.vo.UserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@ContextConfiguration(classes = { RedisConfig.class })
public class RedisUtilTest {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private RedisUtil redisUtil;

	@Test
	public void testRedisConnection() throws Exception {
		UserVO userVo = new UserVO();
		userVo.setAddress("address");
		userVo.setName("simpledemo");
		userVo.setAge(123);
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		operations.set("test", userVo);
		redisUtil.expireKey("test", 30, TimeUnit.SECONDS);
		UserVO vo = (UserVO) operations.get("test");
		logger.info("test vo==============>" + vo);
		assertTrue(vo.getName().equals("simpledemo"));
	}

}
