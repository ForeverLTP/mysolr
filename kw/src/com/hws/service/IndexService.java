package com.hws.service;

import java.util.List;
import java.util.Map;

import com.hws.model.ChineseMedicine;

public interface IndexService {
	
	/**
	 * 首页显示所有药品信息
	 * @return
	 * fields 默认域 (设置默认域)
	 */
	public List<ChineseMedicine> findAll(String[] fields);
	
	/**
	 * 全文检索 查询所有有价值的域
	 * param value:输入的查询内容
	 * param sFields:查询的域
	 * @param fields:要显示的域
	 * @return
	 */
	public List<ChineseMedicine> singleSearch(String[] fields,String value,String sFields,Boolean flag);
	
	/**
	 * 多域查询
	 * @param fields 页面显示的域
	 * @param maps
	 */
	public List<ChineseMedicine> multipleSearch(String[] fields,Map<Map<String, String>,Boolean> maps);
	/**
	 * 前缀查询
	 * @param value
	 * @return
	 */
	public List<String> prefixSearch(String value);
	/**
	 * 
	 * @param value
	 * @param field
	 * @return
	 */
	public List<String> mcnameSuggest(String value,String field);
	
	public List<String> stringSearch(String[] fields,String value,String field,Boolean flag);
	
	
}
