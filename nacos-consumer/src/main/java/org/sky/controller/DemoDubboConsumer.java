package org.sky.controller;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sky.platform.util.AppConstants;
import org.sky.platform.util.DubboResponse;
import org.sky.platform.util.ResponseResult;
import org.sky.platform.util.ResponseStatusEnum;
import org.sky.platform.util.ResponseUtil;
import org.sky.service.HelloNacosService;
import org.sky.service.ProductService;
import org.sky.vo.ProductVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("nacosconsumer")
public class DemoDubboConsumer extends BaseController {

	@Reference(version = "1.0.0", loadbalance = "roundrobin")
	private HelloNacosService helloNacosService;

	@Reference(version = "1.0.0")
	private ProductService productService;

	@PostMapping(value = "/sayHello", produces = "application/json")
	public ResponseEntity<String> sayHello(@RequestBody String params) throws Exception {
		ResponseEntity<String> response;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> resultMap = new HashMap<>();
		JSONObject requestJsonObj = JSON.parseObject(params);
		try {
			String name = getHelloNameFromJson(requestJsonObj);
			String answer = helloNacosService.sayHello(name);
			logger.info("answer======>" + answer);
			Map<String, String> result = new HashMap<>();
			result.put("result", answer);
			String resultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(resultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("dubbo-clinet has an exception occured: " + e.getMessage(), e);
			String resultStr = e.getMessage();
			response = new ResponseEntity<>(resultStr, headers, HttpStatus.EXPECTATION_FAILED);
		}
		return response;

	}

	@PostMapping(value = "/addProductAndStock", produces = "application/json")
	public ResponseEntity<String> addProduct(@RequestBody String params) throws Exception {
		ResponseEntity<String> response = null;
		DubboResponse<ProductVO> dubboResponse;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		JSONObject requestJsonObj = JSON.parseObject(params);
		Map<String, Object> result = new HashMap<>();
		try {
			ProductVO inputProductPara = getProductFromJson(requestJsonObj);
			dubboResponse = productService.addProductAndStock(inputProductPara);
			ProductVO returnData = dubboResponse.getData();
			if (returnData != null && dubboResponse.getCode() == HttpStatus.OK.value()) {
				result.put("code", HttpStatus.OK.value());
				result.put("message", "add a new product successfully");
				result.put("productid", returnData.getProductId());
				result.put("productname", returnData.getProductName());
				result.put("stock", returnData.getStock());
				returnResultStr = JSON.toJSONString(result);
				response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
			} else {
				result.put("message", dubboResponse.getMessage());
				returnResultStr = JSON.toJSONString(result);
				response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (Exception e) {
			logger.error("add a new product with error: " + e.getMessage(), e);
			result.put("message", "add a new product with error: " + e.getMessage());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

	private String getHelloNameFromJson(JSONObject requestObj) {
		String helloName = requestObj.getString("name");
		return helloName;
	}

	private ProductVO getProductFromJson(JSONObject requestObj) {
		String productName = requestObj.getString("productname");
		int stock = requestObj.getIntValue("stock");
		ProductVO prod = new ProductVO();
		prod.setProductName(productName);
		prod.setStock(stock);
		return prod;
	}

}
