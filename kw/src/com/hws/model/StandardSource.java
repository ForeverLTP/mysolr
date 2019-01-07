package com.hws.model;

public class StandardSource {
	private int mid;
	private int sresourceid;
	private String spagenumber;
	private String sprescription;
	
	
	public StandardSource() {
		super();
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getSresourceid() {
		return sresourceid;
	}
	public void setSresourceid(int sresourceid) {
		this.sresourceid = sresourceid;
	}
	public String getSpagenumber() {
		return spagenumber;
	}
	public void setSpagenumber(String spagenumber) {
		this.spagenumber = spagenumber;
	}
	public String getSprescription() {
		return sprescription;
	}
	public void setSprescription(String sprescription) {
		this.sprescription = sprescription;
	}
	
	@Override
	public String toString() {
		return "Standardsource [mid=" + mid + ", sresourceid=" + sresourceid + ", spagenumber=" + spagenumber
				+ ", sprescription=" + sprescription + "]";
	}

}
