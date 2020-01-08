/*
 * @Title: ApiResponse.java 
 * @Package: com.carrefour.vos 
 * @author:  rick_wanshihua@carrefour.com.cn
 * @CreateDate:Aug 21, 2019 11:16:18 AM
 * @UpdateUser: rick_wanshihua@carrefour.com.cn
 * @UpdateDate: Aug 21, 2019 11:16:18 AM
 * @UpdateRemark:第一版
 * @Description: 后台调用结果类
 * @Version: [V1.0]
 */
package org.sky.platform.util;

import org.springframework.http.HttpStatus;

/*
 * @ClassName：ApiResponse
 * @author:  rick_wanshihua@carrefour.com.cn
 * @CreateDate: Aug 21, 2019 11:16:18 AM
 * @UpdateUser: rick_wanshihua@carrefour.com.cn
 * @UpdateDate: Aug 21, 2019 11:16:18 AM
 * @UpdateRemark:第一版
 * @Description: 后台调用结果类
 * @Version: [V1.0]
 */
public class ResponseResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	private int code;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 返回数据对象
	 */
	private transient Object data;

	public ResponseResult(int code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
	}
	
	public ResponseResult(int code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ResponseResult success(String message) {
		return new ResponseResult(HttpStatus.OK.value(), message, null);
	}
	
	public static ResponseResult error(String message) {
		return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
	}

	public static ResponseResult error(int code, String message) {
		return new ResponseResult(code, message, null);
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	

}
