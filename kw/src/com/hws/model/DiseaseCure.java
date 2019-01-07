package com.hws.model;

import java.util.List;

public class DiseaseCure {
	                                     
	private String oneleveltitle;//一级标题
	private List<TitleModel> twoTitleList;//二级标题
	private String threeleveltitle;//三级标题
	private String content;//内容
	private int did;//疾病诊治标注id
	private int dtypeid;//类型id
	private String daddress;//详细内容地址
	//private String titleaddress;//二级标题地址
	
	
	public String getDaddress() {
		return daddress;
	}
	public List<TitleModel> getTwoTitleList() {
		return twoTitleList;
	}
	public void setTwoTitleList(List<TitleModel> twoTitleList) {
		this.twoTitleList = twoTitleList;
	}
	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	public int getDtypeid() {
		return dtypeid;
	}
	public void setDtypeid(int dtypeid) {
		this.dtypeid = dtypeid;
	}
	public String getOneleveltitle() {
		return oneleveltitle;
	}
	public void setOneleveltitle(String oneleveltitle) {
		this.oneleveltitle = oneleveltitle;
	}
	
	public String getThreeleveltitle() {
		return threeleveltitle;
	}
	public void setThreeleveltitle(String threeleveltitle) {
		this.threeleveltitle = threeleveltitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	
	@Override
	public String toString() {
		return "DiseaseCure [oneleveltitle=" + oneleveltitle + ", twoTitleList=" + twoTitleList + ", threeleveltitle="
				+ threeleveltitle + ", content=" + content + ", did=" + did + ", dtypeid=" + dtypeid + ", daddress="
				+ daddress  + "]";
	}
	public DiseaseCure() {
		super();
	}
}
