/*
 * @Title: ResponseUtil.java
 * @Package: com.carrefour.cn.cdm.member.utils
 * @author:  cun.yan@cloud-linking.net
 * @CreateDate:Sep 24, 2019 11:23:10 AM
 * @UpdateUser: cun.yan@cloud-linking.net
 * @UpdateDate:Sep 24, 2019 11:23:10 AM
 * @UpdateRemark:第一版
 * @Description: 返回响应工具类
 * @Version: [V1.0]
 */
package org.sky.platform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.sky.platform.util.ResponseConstant.*;

/*
 * @Title: ResponseConstant.java
 * @Package: com.carrefour.cn.cdm.member.utils
 * @author:  cun.yan@cloud-linking.net
 * @CreateDate:Sep 24, 2019 11:23:10 AM
 * @UpdateUser: cun.yan@cloud-linking.net
 * @UpdateDate:Sep 24, 2019 11:23:10 AM
 * @UpdateRemark:第一版
 * @Description: 返回响应工具类
 * @Version: [V1.0]
 */
public class ResponseUtil {

	private static Logger logger = LogManager.getLogger(ResponseUtil.class);

	private static HttpHeaders headers;

	private static ResponseStatusEnum statusEnum;

	static {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		statusEnum = ResponseStatusEnum.SUCCESS;
	}

	public ResponseUtil(Builder builder) {
		headers = builder.headers;
	}

	/**
	 * 静态内部类builder模式用于设置header
	 */
	public static class Builder {
		private HttpHeaders headers;

		public Builder setHeaders(HttpHeaders headers) {
			this.headers = headers;
			return this;
		}

		public ResponseUtil build() {
			return new ResponseUtil(this);
		}
	}

	/**
	 * @Title: getResponseResult
	 * @Description: 根据枚举和data返回ResponseResult对象 service返回结果的时候可以调用此方法
	 * @param data 数据 没有传null
	 * @return ResponseResult
	 */
	public static ResponseResult getResponseResult(Object data) {
		ResponseResult responseResult = new ResponseResult(statusEnum.getCode(), statusEnum.getMessage(), data);
		return responseResult;
	}

	/**
	 * @Title: getResponseResult
	 * @Description: 根据枚举和data返回ResponseResult对象 service返回结果的时候可以调用此方法
	 * @param responseStatusEnum 返回状态枚举 默认为Success
	 * @param data               数据 没有传null
	 * @return ResponseResult
	 */
	public static ResponseResult getResponseResult(ResponseStatusEnum responseStatusEnum, Object data) {
		if (responseStatusEnum == null) {
			responseStatusEnum = statusEnum;
		}
		ResponseResult responseResult = new ResponseResult(responseStatusEnum.getCode(),
				responseStatusEnum.getMessage(), data);
		return responseResult;
	}

	/**
	 * @Title: getResponseEntity
	 * @Description: 根据枚举和data返回ResponseEntity对象 controller返回结果给前台的时候 可以调用此方法
	 * @param data 传给前台的数据 没有传null
	 * @return ResponseResult
	 */
	public static ResponseEntity<String> getResponseEntity(Object data) {
		String resultStr = assembleResultMap(statusEnum.getCode(), statusEnum.getMessage(), data);
		return new ResponseEntity<>(resultStr, headers, HttpStatus.OK);
	}

	/**
	 * @Title: getResponseEntity
	 * @Description: 根据枚举和data返回ResponseEntity对象 controller返回结果给前台的时候 可以调用此方法
	 * @param responseStatusEnum 状态枚举 默认为Success
	 * @param data               传给前台的数据 没有传null
	 * @return ResponseResult
	 */
	public static ResponseEntity<String> getResponseEntity(ResponseStatusEnum responseStatusEnum, Object data) {
		if (responseStatusEnum == null) {
			responseStatusEnum = statusEnum;
		}

		String resultStr = assembleResultMap(responseStatusEnum.getCode(), responseStatusEnum.getMessage(), data);
		return new ResponseEntity<>(resultStr, headers, HttpStatus.OK);
	}

	/**
	 * @Title: getResponseEntity
	 * @Description: 根据返回结果和data返回ResponseEntity对象 controller收到service返回的结果
	 *               然后将结果给前台的时候 可以调用此方法
	 * @param responseResult 返回结果
	 * @return ResponseEntity
	 */
	public static ResponseEntity<String> getResponseEntity(ResponseResult responseResult) {
		if (responseResult == null) {
			responseResult = getResponseResult(null);
		}

		String resultStr = assembleResultMap(responseResult.getCode(), responseResult.getMessage(),
				responseResult.getData());
		return new ResponseEntity<>(resultStr, headers, HttpStatus.OK);
	}

	/**
	 * @Title: getResponseString
	 * @Description: 根据枚举和data返回string对象 service和controller都可以调用此方法
	 *               返回一个结果集的jsonString
	 * @param responseStatusEnum 状态枚举 默认为Success
	 * @param data               传给前台的数据 没有传null
	 * @return string
	 */
	public static String getResponseString(ResponseStatusEnum responseStatusEnum, Object data) {
		if (responseStatusEnum == null) {
			responseStatusEnum = statusEnum;
		}
		return assembleResultMap(responseStatusEnum.getCode(), responseStatusEnum.getMessage(), data);

	}

	/**
	 * @Title: assembleResultMap
	 * @Description: 根据枚举和data返回string对象 组装code、message和data
	 * @param code    返回code码 可以为空
	 * @param data    传给前台的数据 没有传null 对于实现序列化的data 里面为null的字符串会变成""
	 * @param message 消息 提示信息 可以为空
	 * @return string 返回一个有code、message和data的map的jsonString
	 */
	private static String assembleResultMap(int code, String message, Object data) {

		logger.info("assembleResultMap method start,parameter value code is {}, " + "message is {}, data is {}", code,
				message, data);

		if (data == null) {
			data = RESPONSE_RESULT_STRING_EMPTY;
		}
		Map<String, Object> resultMap = new LinkedHashMap<>(16);
		resultMap.put(AppConstants.RESULT_CODE, code);
		resultMap.put(AppConstants.MESSAGE_CONSTANTS, message);
		resultMap.put(RESPONSE_RESULT_STRING_DATA, data);
		return JSON.toJSONString(resultMap, SerializerFeature.WriteNullStringAsEmpty);
	}

}
