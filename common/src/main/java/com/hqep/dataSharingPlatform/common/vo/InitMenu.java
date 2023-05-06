package com.hqep.dataSharingPlatform.common.vo;

import java.util.List;

public class InitMenu {

	private String title;

	private String href;

	private String icon;

	private String target;

	private Long id;

	private List<InitMenu> child;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<InitMenu> getChild() {
		return child;
	}

	public void setChild(List<InitMenu> child) {
		this.child = child;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
