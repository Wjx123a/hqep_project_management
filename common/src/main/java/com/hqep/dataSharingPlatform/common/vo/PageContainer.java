package com.hqep.dataSharingPlatform.common.vo;

import java.util.List;

public class PageContainer<T> {

	private List<T> records;

	private Long total;

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
