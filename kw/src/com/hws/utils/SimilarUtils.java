package com.hws.utils;

import java.util.ArrayList;
import java.util.List;


public class SimilarUtils {
	/**
	 * 字符串相似度算法
	 * 
	 *   
	 */

	/**
	 * 计算两个字符串相同字符个数
	 * @param target
	 * @param value
	 * @return
	 */
	public static  int compare(String target,String value){
		int count = 0;
		List<Character> targetList = new ArrayList<Character>();
		String remove =  target.replaceAll("[^\u4E00-\u9FA5]", "");//清除
		//将target字符串变成字符数组
		char[] targetTOChar = remove.toCharArray();
		//将数组全部数据转移到list集合中
		for(int i = 0;i<targetTOChar.length;i++){
			targetList.add(targetTOChar[i]);
		}
		//计算两个字符串相同字符个数
		for(int i = 0; i<value.length();i++){
			if(targetList.contains(value.charAt(i))){
				count++;
			}
		}
		return count;
		
	}
	
	/**
	 * 计算相似度算法
	 * 公式：相似度=Kq*q/(Kq*q+Kr*r+Ks*s) (Kq > 0 , Kr>=0,Ka>=0)
	 * @param num
	 * @param target
	 * @return
	 */
	public static double getSimilarValue(int num,String value){
		
		//存在的部分的权重比不存在的大一倍
		int  length = value.length();
		double top = 2*num;//商的类型跟随除数故设置为double类型
		int bottom = 2*num+(length-num)*1;
		double similarity = top / bottom;
		
		return similarity;
		
	}
}
