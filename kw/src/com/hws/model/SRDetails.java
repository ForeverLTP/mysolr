package com.hws.model;

public class SRDetails {
	private	int mid;//中成药类型id
	private int sresourceid;//标准来源id
	private int spagenumber;//页码
	private String sprescription;//处方
	private String make;//制法
	private String identify;//鉴别
	private String scheck;//检查
	private String standardencde;//标准编号
	
	private SRType srType;//类型对象
	private ChineseMedicine cm;//cm对象
	
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
	public int getSpagenumber() {
		return spagenumber;
	}
	public void setSpagenumber(int spagenumber) {
		this.spagenumber = spagenumber;
	}
	public String getSprescription() {
		return sprescription;
	}
	public void setSprescription(String sprescription) {
		this.sprescription = sprescription;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getScheck() {
		return scheck;
	}
	public void setScheck(String scheck) {
		this.scheck = scheck;
	}
	public String getStandardencde() {
		return standardencde;
	}
	public void setStandardencde(String standardencde) {
		this.standardencde = standardencde;
	}
	public SRType getSrType() {
		return srType;
	}
	public void setSrType(SRType srType) {
		this.srType = srType;
	}
	public ChineseMedicine getCm() {
		return cm;
	}
	public void setCm(ChineseMedicine cm) {
		this.cm = cm;
	}
	@Override
	public String toString() {
		return "SRDetails [mid=" + mid + ", sresourceid=" + sresourceid + ", spagenumber=" + spagenumber
				+ ", sprescription=" + sprescription + ", make=" + make + ", identify=" + identify + ", scheck="
				+ scheck + ", standardencde=" + standardencde + ", srType=" + srType + ", cm=" + cm + "]";
	}
	public SRDetails() {
		super();
	}
	
}
