package org.sky.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.sky.platform.util.BloomFilterHelper;
import org.sky.platform.util.RedisUtil;
import org.sky.vo.UserVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private RedisUtil redisUtil;

	@PostMapping(value = "/addEmailToBloom", produces = "application/json")
	public ResponseEntity<String> addUser(@RequestBody String params) {
		ResponseEntity<String> response = null;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> result = new HashMap<>();
		try {
			JSONObject requestJsonObj = JSON.parseObject(params);
			UserVO inputUser = getUserFromJson(requestJsonObj);
			BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,
					into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 500000, 0.00001);
			redisUtil.addByBloomFilter(myBloomFilterHelper, "email_existed_bloom", inputUser.getEmail());
			result.put("code", HttpStatus.OK.value());
			result.put("message", "add into bloomFilter successfully");
			result.put("email", inputUser.getEmail());
			returnResultStr = JSON.toJSONString(result);
			logger.info("returnResultStr======>" + returnResultStr);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("add a new product with error: " + e.getMessage(), e);
			result.put("message", "add a new product with error: " + e.getMessage());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PostMapping(value = "/checkEmailInBloom", produces = "application/json")
	public ResponseEntity<String> findEmailInBloom(@RequestBody String params) {
		ResponseEntity<String> response = null;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> result = new HashMap<>();
		try {
			JSONObject requestJsonObj = JSON.parseObject(params);
			UserVO inputUser = getUserFromJson(requestJsonObj);
			BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,
					into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8), 500000, 0.00001);
			boolean answer = redisUtil.includeByBloomFilter(myBloomFilterHelper, "email_existed_bloom",
					inputUser.getEmail());
			logger.info("answer=====" + answer);
			result.put("code", HttpStatus.OK.value());
			result.put("email", inputUser.getEmail());
			result.put("exist", answer);
			returnResultStr = JSON.toJSONString(result);
			logger.info("returnResultStr======>" + returnResultStr);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("add a new product with error: " + e.getMessage(), e);
			result.put("message", "add a new product with error: " + e.getMessage());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	private UserVO getUserFromJson(JSONObject requestObj) {
		String userName = requestObj.getString("username");
		String userAddress = requestObj.getString("address");
		String userEmail = requestObj.getString("email");
		int userAge = requestObj.getInteger("age");
		UserVO u = new UserVO();
		u.setName(userName);
		u.setAge(userAge);
		u.setEmail(userEmail);
		u.setAddress(userAddress);
		return u;

	}
}
