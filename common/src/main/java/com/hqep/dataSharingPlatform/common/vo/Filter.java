package com.hqep.dataSharingPlatform.common.vo;

public class Filter<T> {

	private T filterObj;

	private String keyWords;

	private Integer page;

	private Integer pageSize;

	private String sortField;

	private String order;

	public T getFilterObj() {
		return filterObj;
	}

	public void setFilterObj(T filterObj) {
		this.filterObj = filterObj;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
