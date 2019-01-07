package com.hws.model;

public class Instructions {
	private int iid;//说明书id
	private int mid;//cm对象id
	private String isource;//说明书来源
	private String iaddress;//说明书地址
	private String ipicture;//图片地址
	
	private ChineseMedicine cMedicine;//cm对象
	
	
	public ChineseMedicine getCm() {
		return cMedicine;
	}
	public void setCm(ChineseMedicine cMedicine) {
		this.cMedicine = cMedicine;
	}
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getIsource() {
		return isource;
	}
	public void setIsource(String isource) {
		this.isource = isource;
	}
	public String getIaddress() {
		return iaddress;
	}
	public void setIaddress(String iaddress) {
		this.iaddress = iaddress;
	}
	public String getIpicture() {
		return ipicture;
	}
	public void setIpicture(String ipicture) {
		this.ipicture = ipicture;
	}
	public Instructions() {
		super();
	}
	@Override
	public String toString() {
		return "Instructions [iid=" + iid + ", mid=" + mid + ", isource=" + isource + ", iaddress=" + iaddress
				+ ", ipicture=" + ipicture + ", cm=" + cMedicine + "]";
	}

}
