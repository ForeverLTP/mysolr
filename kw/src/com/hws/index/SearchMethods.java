package com.hws.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocumentList;

import com.hws.utils.IndexUtils;

public class SearchMethods extends IndexUtils {

	/**
	 * 查找高亮信息
	 * 
	 * @param sQuery
	 * @param fields
	 * @param httpSolrServer
	 * @return
	 * @throws SolrServerException
	 */
	public Map<Map<String, Map<String, List<String>>>, SolrDocumentList> findHight(SolrQuery sQuery, Set<String> fields,
			HttpSolrServer httpSolrServer) throws SolrServerException {
		// 创建 对象用来返回查询集合和高亮集合
		Map<Map<String, Map<String, List<String>>>, SolrDocumentList> mapH = new HashMap<Map<String, Map<String, List<String>>>, SolrDocumentList>();

		// 调用工具方法设置高亮参数
		SolrQuery solrQuery = setHighting(sQuery, fields);
		// 设置行数,Integer.MAX_VALUE保证查询数据全部显示
		solrQuery.setRows(Integer.MAX_VALUE);
		QueryResponse qResponse = httpSolrServer.query(solrQuery);
		Map<String, Map<String, List<String>>> highlighting = qResponse.getHighlighting();

		SolrDocumentList docs = qResponse.getResults();
		mapH.put(highlighting, docs);
		/*
		 * for(SolrDocument doc:docs){ for(String field:fields){ List<String>
		 * titleList = highlighting.get(doc.get("id")).get(field); if (titleList
		 * !=null && titleList.size()>0) { //能取到高亮,输出高亮
		 * System.out.println(titleList.get(0));
		 * 
		 * } } }
		 */
		return mapH;
	}

	/**
	 * 查找信息
	 * 
	 * @param sQuery
	 * @param fields
	 * @param httpSolrServer
	 * @return
	 * @throws SolrServerException
	 */
	public SolrDocumentList find(SolrQuery sQuery, HttpSolrServer httpSolrServer,int rowCount) throws SolrServerException {

		// 设置行数,Integer.MAX_VALUE保证查询数据全部显示
		sQuery.setRows(rowCount);
		QueryResponse qResponse = httpSolrServer.query(sQuery);
		SolrDocumentList docs = qResponse.getResults();
		return docs;
	}
	
	public List<String> getSuggestionContent(SolrQuery sQuery ,HttpSolrServer httpSolrServer) throws SolrServerException{
		
		QueryResponse qr = httpSolrServer.query(sQuery);  
		List<String> lists = null;
        // 上面取结果的代码  
        SpellCheckResponse sp = qr.getSpellCheckResponse();// 获取拼写检查的结果集  
        if (sp != null) {  
        	lists = new ArrayList<String>();
            for (Suggestion s : sp.getSuggestions()) {  
            	
               List<String> list = s.getAlternatives();// 获取所有 的检索词  
               for(String str:list){
            	   lists.add(str);
               }
            }  
//          String t = sp.getFirstSuggestion(word);// 获取第一个推荐词  
            
        }  
		return lists;
		
	}
	
}
