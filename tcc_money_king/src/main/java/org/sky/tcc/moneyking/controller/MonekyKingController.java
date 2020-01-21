package org.sky.tcc.moneyking.controller;

import java.util.HashMap;
import java.util.Map;

import org.sky.controller.BaseController;
import org.sky.tcc.bean.AccountBean;
import org.sky.tcc.moneyking.service.biz.TccMoneyKingBizService;
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
@RequestMapping("moneyking")
public class MonekyKingController extends BaseController {

	@Autowired
	private TccMoneyKingBizService tccMoneyKingBizService;

	@PostMapping(value = "/transfermoney", produces = "application/json")
	public ResponseEntity<String> transferMoney(@RequestBody String params) throws Exception {
		ResponseEntity<String> response = null;
		String returnResultStr;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		Map<String, Object> result = new HashMap<>();
		try {
			logger.info("input params=====" + params);
			JSONObject requestJsonObj = JSON.parseObject(params);
			Map<String, AccountBean> acctMap = getAccountFromJson(requestJsonObj);
			AccountBean acctFrom = acctMap.get("account_from");
			AccountBean acctTo = acctMap.get("account_to");
			boolean answer = tccMoneyKingBizService.transfer(acctFrom.getAccountId(), acctTo.getAccountId(),
					acctFrom.getAmount());
//			tccMoneyKingBizService.icbcHello();
//			tccMoneyKingBizService.cmbHello();
			result.put("account_from", acctFrom.getAccountId());
			result.put("account_to", acctTo.getAccountId());
			result.put("transfer_money", acctFrom.getAmount());
			result.put("message", "transferred successfully");
			returnResultStr = JSON.toJSONString(result);
			logger.info("transfer money successfully======>\n" + returnResultStr);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("transfer money with error: " + e.getMessage(), e);
			result.put("message", "transfer money with error[ " + e.getMessage() + "]");
			returnResultStr = JSON.toJSONString(result);
			response = new ResponseEntity<>(returnResultStr, headers, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}
}
