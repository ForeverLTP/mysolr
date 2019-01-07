package com.hws.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import com.hws.index.SearchMethods;
import com.hws.model.ChineseMedicine;
import com.hws.utils.IndexUtils;

public class TestIndex extends IndexUtils{
	
	SearchMethods s = new SearchMethods();
	Map<Map<String,String>,Boolean> maps = new HashMap<Map<String,String>,Boolean>();
	Map<String,String> map1 = new HashMap<String,String>();
	Map<String,String> map2 = new HashMap<String,String>();
	Set<String> set3 = new HashSet<String>();
	Set<String> set4 = new HashSet<String>();
	
	@Test
	public void testSuggest() throws SolrServerException{
		s.getSuggestionContent(getSuggestionSolr("栀子"),getHttpSolrServer());
	}
	
	@Test
	public void Test() throws SolrServerException{
		
		//精确查询即在要查询的内容上添加双引号
		//map1.put("mcname", "\""+"金花丸"+"\"");
		map1.put("mall", "金花丸");
		map2.put("mingredient", "大黄");
		maps.put(map1, true);
		maps.put(map2, true);
		set3.add("mall");
		set3.add("mingredient");
		set4.add("mcname");
		set4.add("mingredient");
		set4.add("mindications");
		
		SolrQuery sq = getSolrQuery(maps);
		s.findHight(sq, set3, getHttpSolrServer());
		List<ChineseMedicine> list = translatH(s.findHight(sq, set3, getHttpSolrServer()), set3, set4);
		for(ChineseMedicine c:list){
			System.out.println(c.toString());
		}
	}

}
