package com.hws.model;

public class CMType {
	
	private int mtypeid;//类型id
	private String tname;//类型名称
	public int getMtypeid() {
		return mtypeid;
	}
	public void setMtypeid(int mtypeid) {
		this.mtypeid = mtypeid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
	@Override
	public String toString() {
		return "Type [mtypeid=" + mtypeid + ", tname=" + tname + "]";
	}

	
}
