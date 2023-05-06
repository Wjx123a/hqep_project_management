/**
 * Project Name:api-utils
 * File Name:RestResult.java
 * Package Name:com.hanguilin.apiutils.returns
 * Date:2019年10月3日下午12:06:18
 * Copyright (c) 2019, hanguilin All Rights Reserved.
 *
 */
package com.hqep.dataSharingPlatform.common.model;

/**
* @Description:    java类作用描述
* @Author:         shaowenqiang
* @CreateDate:     2021/3/26 14:17
* @Version:        1.0
*/
public class RequestJsonParam {
//0：成功；	-1失败
	private int pageNum;

	private int pageSize;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
