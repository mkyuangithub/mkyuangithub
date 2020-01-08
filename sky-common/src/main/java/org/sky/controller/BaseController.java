package org.sky.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sky.platform.util.AppConstants;
import org.sky.platform.util.ResponseResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;

public abstract class BaseController {
	protected Logger logger = LogManager.getLogger(this.getClass());

	protected void setResponseByResult(Map<String, Object> resultMap, ResponseResult responseResult) {
		if (responseResult != null && responseResult.getCode() == HttpStatus.OK.value()) {
			resultMap.put(AppConstants.RESULT_CODE, 0);
			resultMap.put(AppConstants.MESSAGE_CONSTANTS, responseResult.getMessage());
		} else {
			resultMap.put(AppConstants.RESULT_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
			if (responseResult != null) {
				resultMap.put(AppConstants.MESSAGE_CONSTANTS, responseResult.getMessage());
			} else {
				resultMap.put(AppConstants.MESSAGE_CONSTANTS, "发生了内部错误.");
			}
		}
	}

	protected ResponseEntity<String> getStringResponseEntity(HttpHeaders headers, Map<String, Object> resultMap,
			ResponseResult responseResult) {
		ResponseEntity<String> response = null;
		// setResponseByResult(resultMap, responseResult);
		// String resultStr = JSON.toJSONString(resultMap);
		if (responseResult.getData() != null) {
			response = new ResponseEntity<>(responseResult.getData().toString(), headers, HttpStatus.OK);
		} else {
			resultMap.put(AppConstants.RESULT_CODE, 0);
			resultMap.put(AppConstants.MESSAGE_CONSTANTS, responseResult.getMessage());
			String resultStr = JSON.toJSONString(resultMap);
			response = new ResponseEntity<>(resultStr, headers, HttpStatus.OK);
		}
		return response;
	}

}
