package com.hws.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import com.hws.index.SearchMethods;
import com.hws.model.ChineseMedicine;
import com.hws.service.IndexService;
import com.hws.utils.IndexUtils;

public class IndexServiceImpl extends IndexUtils implements IndexService {

	SearchMethods s = new SearchMethods();
	Map<Map<String, String>, Boolean> maps = null;
	Set<String> set4 = null;
	Set<String> set3 = null;
	List<ChineseMedicine> list = null;
	List<String> strList = null;
	List<List<String>> strLists = null;

	/**
	 * 首页显示所有药品信息
	 * 
	 * @return List<ChineseMedicine>
	 */
	@Override
	public List<ChineseMedicine> findAll(String[] fields) {

		maps = setSingleQueryParam("", null, null);// 此处的maps为空
		setDefultFields(fields);// 设置默认域可全局使用
		set4 = getDefultFields();
		SolrQuery sq = getSolrQuery(maps);
		try {
			list = translat(s.find(sq, getHttpSolrServer(), Integer.MAX_VALUE), set4);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 单查询 param value:输入的查询内容 param sFields:查询的域 param fields 要显示的域
	 */
	@Override
	public List<ChineseMedicine> singleSearch(String[] fields, String value, String sFields, Boolean flag) {

		// 判断是否使用默认域显示域
		if (null == fields) {
			set4 = getDefultFields();
		} else {
			set4 = setFields(fields);
		}
		set3 = new HashSet<String>();
		set3.add(sFields);
		if(sFields.equals("mall")){
			set3 = set4;
		}
		maps = setSingleQueryParam(value, sFields, flag);
		SolrQuery sq = getSolrQuery(maps);
		try {
			// s.findHight(sq, set, getHttpSolrServer())中的set是设置高亮显示的域
			// 第二个set表示那些域可以显示高亮(与第一个相同)
			// 第三个set表示页面上有哪些域
			list = translatH(s.findHight(sq, set3, getHttpSolrServer()), set4, set4);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 前缀查询 光标只能显示在一个文本框中即一个域所以参数域不是数组
	 */
	@Override
	public List<String> prefixSearch(String value) {

		try {
			strList = s.getSuggestionContent(getSuggestionSolr(value), getHttpSolrServer());
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return strList;

	}

	/**
	 * 多域查询
	 */
	@Override
	public List<ChineseMedicine> multipleSearch(String[] fields, Map<Map<String, String>, Boolean> maps) {

		if (null == fields) {
			set4 = getDefultFields();// 默认显示域
		} else {
			set4 = setFields(fields);
		}
		SolrQuery sq = getSolrQuery(maps);
		Set<String> hightFields = new HashSet<String>();
		if (null != maps) {
			for (Map<String, String> map : maps.keySet()) {
				hightFields.addAll(map.keySet());
			}
		}else{
			hightFields = set4;
		}
		if(hightFields.contains("mall")){
			hightFields = set4;
		}
		try {
			list = translatH(s.findHight(sq, hightFields, getHttpSolrServer()), set4, set4);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 参考文献查询 parm fields 显示的域 parm fields 查询的域
	 */
	@Override
	public List<String> stringSearch(String[] fields, String value, String field, Boolean flag) {

		if (null == fields) {
			set4 = getDefultFields();// 默认显示域
		} else {
			set4 = setFields(fields);
		}
		maps = setSingleQueryParam(value, field, flag);
		SolrQuery sq = getSolrQuery(maps);

		try {
			Map<Map<String, Map<String, List<String>>>, SolrDocumentList> map = s.findHight(sq, set4,
					getHttpSolrServer());
			strList = translatHString(map, field);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return strList;
	}

	/**
	 * 药品名称智能提示
	 * 
	 * @param value
	 *            查询的内容
	 * @param field
	 *            提示的域
	 * @return
	 */
	@Override
	public List<String> mcnameSuggest(String value, String field) {

		maps = setSingleQueryParam(value, field, true);
		SolrQuery sq = getSolrQuery(maps);

		try {
			strList = translatString(s.find(sq, getHttpSolrServer(), 3), field);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return strList;
	}

}
