package com.hws.test;


import org.junit.Test;


public class T {

	@Test
	public void test() {
		
		String a = " <font color=\"#red\"> ";
		a=a.replaceAll("<font color=\"#red\">", "");
		System.out.println(a);
		
	}
	
	@Test
	public void test1(){
		
		String a1 = "儿s童";
		String a2 = "儿s";
		a1.contains(a2);
		System.out.println(a1.contains(a2));
	}
	
	@Test
	public void test2(){
		
	}

}
