package com.hws.model;

public class SRType {

	private int sresourceid;// 标准来源id
	private String bookname;// 标准来源书名称

	public int getSresourceid() {
		return sresourceid;
	}

	public void setSresourceid(int sresourceid) {
		this.sresourceid = sresourceid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	@Override
	public String toString() {
		return "SRType [sresourceid=" + sresourceid + ", bookname=" + bookname + "]";
	}

}
