package com.hws.model;

public class Literature {
	
	private int lid;//参考文献id
	private String literature;//参考文献
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getLiterature() {
		return literature;
	}
	public void setLiterature(String literature) {
		this.literature = literature;
	}
	public Literature() {
		super();
	}
	@Override
	public String toString() {
		return "Literature [lid=" + lid + ", literature=" + literature + "]";
	}

}
