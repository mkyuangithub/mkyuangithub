/*
 * @Title: ResponseStatusEnum.java
 * @Package: com.carrefour.cn.cdm.member.utils
 * @author:  cun.yan@cloud-linking.net
 * @CreateDate:Sep 20, 2019 16:56:34 PM
 * @UpdateUser: cun.yan@cloud-linking.net
 * @UpdateDate: Sep 20, 2019 16:56:34 PM
 * @UpdateRemark:第一版
 * @Description:自定义方法返回状态枚举类
 * @Version: [V1.0]
 */
package org.sky.platform.util;

/*
 * @Title: ResponseStatusEnum.java
 * @Package: com.carrefour.cn.cdm.member.utils
 * @author:  cun.yan@cloud-linking.net
 * @CreateDate:Sep 20, 2019 16:56:34 PM
 * @UpdateUser: cun.yan@cloud-linking.net
 * @UpdateDate: Sep 20, 2019 16:56:34 PM
 * @UpdateRemark:第一版
 * @Description:方法返回状态枚举类
 * @Version: [V1.0]
 */
public enum ResponseStatusEnum {

	SUCCESS(200, "Success"), QUERY_RESULT_IS_NULL(3000, "查询结果记录为空."), INPUT_PARAMETER_IS_NULL(3001, "入参为空,请检查!"),
	INPUT_VALIDATOR_FAILED(3002, "入参校验失败."), MODIFY_MEMBERINFO_FAILED(3003, "会员信息更新失败,请刷新界面!"),
	MODIFY_ADDRESSINFO_FAILED(3004, "会员地址信息更新失败,请刷新界面!"), EXIST_CO_CARD(3005, "存在有效的联名卡,不允许禁用!"),
	ENABLE_MEMBER_CARD_FAIL(3006, "启用会员卡失败,请刷新界面!"), DISABLE_MEMBER_CARD_FAIL(3008, "禁用会员卡失败,请刷新界面!"),
	ENABLE_ACCOUNT_INFO_FAIL(3007, "启用账户失败,请刷新界面!"), DISABLE_ACCOUNT_INFO_FAIL(3009, "禁用账户失败,请刷新界面!"),
	MEMBER_ID_IS_NULL(3010, "未知的会员编号!"), ACCOUNT_NO_IS_NULL(3011, "未知的账户编号!"), OFFSET_OR_LIMIT_IS_NULL(3012, "分页参数必传"),
	SERVICE_EXCEPTIONS(5000, "服务异常,请联系相关人员查看!");

	private int code;

	private String message;

	ResponseStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @Title: codeOf
	 * @Description: 输入code返回对应的状态枚举
	 * @param code 枚举code
	 * @return ResponseStatusEnum 对应的枚举
	 */
	public static ResponseStatusEnum codeOf(Integer code) {
		if (code == null) {
			return null;
		}
		for (ResponseStatusEnum item : ResponseStatusEnum.values()) {
			if (item.getCode() == code) {
				return item;
			}
		}
		return null;
	}

	/**
	 * @Title: valueOf
	 * @Description: 输入code返回对应的枚举信息
	 * @param code 枚举code
	 * @return String 对应的枚举信息描述
	 */
	public static String valueOf(Integer code) {
		if (code == null) {
			return null;
		}
		for (ResponseStatusEnum item : ResponseStatusEnum.values()) {
			if (item.getCode() == code) {
				return item.getMessage();
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
