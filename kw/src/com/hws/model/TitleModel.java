package com.hws.model;

public class TitleModel {
	private String titleAddress;
	private String title;
	public String getTitleAddress() {
		return titleAddress;
	}
	public void setTitleAddress(String titleAddress) {
		this.titleAddress = titleAddress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TitleModel() {
		super();
	}
	@Override
	public String toString() {
		return "TitleModel [titleAddress=" + titleAddress + ", title=" + title + "]";
	}
	

}
