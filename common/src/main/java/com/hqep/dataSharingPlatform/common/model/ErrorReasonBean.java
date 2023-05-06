package com.hqep.dataSharingPlatform.common.model;


/**
 * @类名: ErrsorReasonBean
 * @功能描述 登陆错误信息返回实体
 * @作者信息 Wang_XD
 * @创建时间 2019/9/23
 */
public class ErrorReasonBean {

	private String code;
	private String text;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ReasonBean{" +
				"code='" + code + '\'' +
				", text='" + text + '\'' +
				'}';
	}

	public ErrorReasonBean(String code, String text) {
		super();
		this.code = code;
		this.text = text;
	}

	public ErrorReasonBean(){
		super();
	}
}
