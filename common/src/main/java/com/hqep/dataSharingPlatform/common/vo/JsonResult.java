/**
 * Project Name:api-utils
 * File Name:RestResult.java
 * Package Name:com.hanguilin.apiutils.returns
 * Date:2019年10月3日下午12:06:18
 * Copyright (c) 2019, hanguilin All Rights Reserved.
 *
 */
package com.hqep.dataSharingPlatform.common.vo;

import com.github.pagehelper.Page;
import com.hqep.dataSharingPlatform.common.utils.StringBuilderUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
* @author  swq
* @date 2019年10月3日 下午12:06:18
* @version 1.0
* @since
*/
public class JsonResult<T> {
	//0：成功；	-1失败
	private int code;
	private T data;
	private Long count;
	private String msg;

	//success：成功 ； error：失败
	private String state;
	//succeed：成功 ； fail：失败
	private String status;

	private String successMsg;
	private String errorMsg;
	private String opertionNum;
	private Object opertionData;

	public JsonResult() {
		super();
	}

	public JsonResult(int code, String msg, T data, Long count) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}

	public JsonResult(
			int code, String msg, T data, Long count,String state,String status
			,String successMsg,String errorMsg,String opertionNum,Object opertionData) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
		this.state = state;
		this.status  = status;
		this.successMsg  = successMsg;
		this.errorMsg  = errorMsg;
		this.opertionNum  = opertionNum;
		this.opertionData  = opertionData;
	}

	public static JsonResult success(String msg) {
		return new JsonResult(ErrorCode.SUCCESS, msg, null, null);
	}

	public static JsonResult success(String msg, Object data) {
		Long count = null;
		if(data instanceof Page) count = ((Page<?>) data).getTotal();
		return new JsonResult(ErrorCode.SUCCESS, msg, data, count);
	}

	public static JsonResult success(String msg, Object data, Long count) {
		if(data instanceof Page){
			count = ((Page<?>) data).getTotal();
		}
		return new JsonResult(ErrorCode.SUCCESS, msg, data, count);
	}

	public static JsonResult fail(String msg) {
		return new JsonResult(ErrorCode.FAIL, msg, null, null);
	}

	public static JsonResult fail(String msg, Object data) {
		return new JsonResult(ErrorCode.FAIL, msg, data, null);
	}

	public static JsonResult fail(String msg, Object data, Long count) {
		return new JsonResult(ErrorCode.FAIL, msg, data, count);
	}

	public static JsonResult unAuth(String msg) {
		return new JsonResult(ErrorCode.NOT_CERTIFIED, msg, null, null);
	}

	public  static JsonResult autoResultMap(int i, Object opertionInfo, String successMsg, String errorMsg){
		String str = StringBuilderUtil.concatString("有", String.valueOf(i), "条数据操作成功");
		if (i > 0) {
			return new JsonResult(ErrorCode.SUCCESS,str,opertionInfo,(long)i,"success","succeed",successMsg,errorMsg,String.valueOf(i),opertionInfo);
		} else {
			return new JsonResult(ErrorCode.FAIL,str,opertionInfo,(long)i,"error","fail",successMsg,errorMsg,String.valueOf(i),opertionInfo);
		}
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getOpertionNum() {
		return opertionNum;
	}

	public void setOpertionNum(String opertionNum) {
		this.opertionNum = opertionNum;
	}

	public Object getOpertionData() {
		return opertionData;
	}

	public void setOpertionData(Object opertionData) {
		this.opertionData = opertionData;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
}
