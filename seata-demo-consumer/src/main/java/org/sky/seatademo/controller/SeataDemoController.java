package org.sky.seatademo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.sky.controller.BaseController;
import org.sky.seatademo.service.biz.BusinessDubboService;
import org.sky.seatademo.vo.SeataProductVO;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("seatademo")
public class SeataDemoController extends BaseController {

	@Autowired
	private BusinessDubboService businessDubboService;

	@PostMapping(value = "/addSeataProduct", produces = "application/json")
	public ResponseEntity<String> addSeataProduct(@RequestBody String params) throws Exception {
		ResponseEntity<String> response = null;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> result = new HashMap<>();
		try {
			JSONObject requestJsonObj = JSON.parseObject(params);
			SeataProductVO inputProductPara = getSeataProductFromJson(requestJsonObj);
			SeataProductVO returnData = businessDubboService.addProductAndStock(inputProductPara);
			result.put("code", HttpStatus.OK.value());
			result.put("message", "add a new product successfully");
			result.put("productid", returnData.getProductId());
			result.put("productname", returnData.getProductName());
			result.put("stock", returnData.getStock());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("add a new product with error: " + e.getMessage(), e);
			result.put("message", "add a new product with error: " + e.getMessage());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}
	@PostMapping(value = "/addSeataProductFailed", produces = "application/json")
	public ResponseEntity<String> addSeataProductFailed(@RequestBody String params) throws Exception {
		ResponseEntity<String> response = null;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> result = new HashMap<>();
		try {
			JSONObject requestJsonObj = JSON.parseObject(params);
			SeataProductVO inputProductPara = getSeataProductFromJson(requestJsonObj);
			SeataProductVO returnData = businessDubboService.addProductAndStockFailed(inputProductPara);
			result.put("code", HttpStatus.OK.value());
			result.put("message", "add a new product successfully");
			result.put("productid", returnData.getProductId());
			result.put("productname", returnData.getProductName());
			result.put("stock", returnData.getStock());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("add a new product with error: " + e.getMessage(), e);
			result.put("message", "add a new product with error: " + e.getMessage());
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}
}
