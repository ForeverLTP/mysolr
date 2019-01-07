package com.hws.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.hws.model.ChineseMedicine;
import com.hws.model.Instructions;
import com.hws.model.StandardSource;

/**
 * 工具类
 * 
 * @author hws
 *
 */
public class IndexUtils {

	private static HttpSolrServer httpSolrServer;// 用来连接solr服务器
	private static SolrQuery solrQuery;// 查询对象
	private static Set<String> set1 = null;
	private static Set<String> defultField;
	private static Map<Map<String, String>, Boolean> maps;
	private static Map<String, String> map1;

	private static List<ChineseMedicine> cmList;
	private static ChineseMedicine cm;
	private static StandardSource ss;
	private static Instructions ins;
	
	private static List<String> strList = null;

	/**
	 * 获取server连接solr服务器对象
	 * 
	 * @return
	 */
	public static HttpSolrServer getHttpSolrServer() {

		httpSolrServer = new HttpSolrServer("http://localhost:8088/solr");
		return httpSolrServer;

	}

	/**
	 * 获取查询对象可支持单条件、多条件（分词）查询 Map<String,String>表示输入的查询内容和所在的域，boolean表示是与还是非
	 * 
	 * @param Map<Map<String,String>,Boolean>
	 *            maps
	 * @return
	 */
	public static SolrQuery getSolrQuery(Map<Map<String, String>, Boolean> maps) {

		solrQuery = new SolrQuery();
		StringBuffer strB = new StringBuffer();
		int flag = 0;
		if (null != maps) {
			for (Map<String, String> map : maps.keySet()) {
				map1 = map;
				flag++;
				String temp = "";
				if (!maps.get(map)) {
					temp = "-";
				}
				for (String sMap : map.keySet()) {
					if (1 == flag) {
						strB.append(temp + sMap + ":" + map.get(sMap));
					} else {
						strB.append(" AND " + temp + sMap + ":" + map.get(sMap));
					}
				}
			}
		} else {
			map1 = null;
			strB.append("*:*");
		}
		//solrQuery= setBoost(solrQuery,map1);
		solrQuery.setQuery(strB.toString());
		return solrQuery;
	}
	
	public static SolrQuery setBoost(SolrQuery solrQuery,Map<String,String> map1){
		/*String value = null;
		set1 = map1.keySet();
		for(String str:set1){
			if(str.equals("mcname")){
				//value += str
			}
		}*/
		String scoreMethod = "sum(if(exists(mcname),1000,0)"
				+ ",if(exists(mpname),10,0)"
				+ ",if(exists(mingredient),10,0)"
				+ ",if(exists(mindications),10,0)"
				+ ",if(exists(mnegativereactions),100,0)"
				+ ",if(exists(mban),10,0)"
				+ ",if(exists(mnotice),10,0)"
				+ ",if(exists(minteraction),10,0)"
				+ ",if(exists(tname),10,0)"
				+ ",if(exists(bookname),10,0))";
		solrQuery.set("defType","edismax");
		solrQuery.set("bf", scoreMethod);
		return solrQuery;
	}

	/**
	 * 智能提示查询
	 * 
	 * @param vlaue
	 * @return
	 */
	public static SolrQuery getSuggestionSolr(String value) {

		solrQuery = new SolrQuery();
		solrQuery.set("q", value);// 查询的词
		solrQuery.set("qt", "/suggest");// 请求到suggest中

		return solrQuery;

	}

	/**
	 * 设置SolrQuery的高亮显示参数
	 * 
	 * @param solrQuery
	 * @param fields
	 * @return SolrQuery
	 */
	public static SolrQuery setHighting(SolrQuery sQuery, Set<String> fields) {

		sQuery.setHighlight(true);
		// 设置高亮样式
		// sQuery.setHighlightSimplePre("<font color=\"red\">");
		sQuery.setHighlightSimplePre("<font color=\"red\">");
		sQuery.setHighlightSimplePost("</font>");
		// 设置高亮显示域(空表示不显示高亮)
		if (null != fields) {
			for (String field : fields) {
				sQuery.addHighlightField(field);
			}
		}
		return sQuery;
	}

	/**
	 * 用来将查询到的高亮和非高亮字段信息注入到cm对象中
	 * 
	 * @param mapH
	 * @param fields
	 * @param fields1
	 * @return
	 */
	public List<ChineseMedicine> translatH(Map<Map<String, Map<String, List<String>>>, SolrDocumentList> mapH,
			Set<String> fields, Set<String> fields1) {
		cmList = new ArrayList<ChineseMedicine>();
		for (Map<String, Map<String, List<String>>> highting : mapH.keySet()) {
			// 每一个文档对象即查询出来的一条记录即一个cm对象
			for (SolrDocument doc : mapH.get(highting)) {
				cm = new ChineseMedicine();
				ss = new StandardSource();
				ins = new Instructions();
				cm.setInstuctions(ins);
				cm.setsSource(ss);
				for (String field1 : fields1) {
					// 如果输出的域不在高亮显示域中就正常输出
					if (!fields.contains(field1)) {
						// 调用工具方法进行cm对象设置属性值
						setCM(cm, field1, doc);
					} else {
						setCMH(cm, field1, doc, highting);
					}
				}
				cmList.add(cm);
			}
		}
		return cmList;
	}

	/**
	 * 用来将查询到的非高亮信息注入到cm对象中
	 * 
	 * @param docList
	 * @param fields1
	 * @return
	 */
	public static List<ChineseMedicine> translat(SolrDocumentList docList, Set<String> fields1) {
		cmList = new ArrayList<ChineseMedicine>();
		for (SolrDocument doc : docList) {
			cm = new ChineseMedicine();
			ss = new StandardSource();
			cm.setsSource(ss);
			ins = new Instructions();
			cm.setInstuctions(ins);
			for (String field1 : fields1) {
				setCM(cm, field1, doc);
			}
			cmList.add(cm);
		}
		return cmList;

	}

	/**
	 * 将doc文件转换为字符串集合
	 * 
	 * @param docList
	 * @param field
	 *            查询的域
	 * @return
	 */
	public static List<String> translatString(SolrDocumentList docList, String field) {
		strList = new ArrayList<String>();
		for (SolrDocument doc : docList) {

			strList.add(doc.get(field).toString());
		}
		return strList;

	}

	/**
	 * 将高亮转换为字符串集合
	 * 
	 * @param mapH 查询的高亮集合
	 * @param field
	 *            查询的域
	 * @return
	 */
	public static List<String> translatHString(Map<Map<String, Map<String, List<String>>>, SolrDocumentList> mapH,String field) {
		strList = new ArrayList<String>();
		if (null != mapH) {
			for (Map<String, Map<String, List<String>>> highting : mapH.keySet()) {
				for (SolrDocument doc : mapH.get(highting)) {
					List<String> list = highting.get(doc.get("id")).get(field);
					if (list != null && list.size() > 0) {
						String str = list.get(0);
						strList.add(str);

					}
				}
				break;
			}
		}
		return strList;

	}

	/**
	 * 工具方法 设置cm对象的高亮属性值
	 * 
	 * @param field
	 * @param doc
	 */
	public static void setCMH(ChineseMedicine cm, String field1, SolrDocument doc,
			Map<String, Map<String, List<String>>> highting) {
		List<String> titleList = null;
		cm.setmId(Integer.parseInt(doc.get("id").toString()));// 配置文件已经将id域指向了mid
		switch (field1) {
		case "mcname":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setMcName(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}

			break;
		case "mingredient":

			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setmIngredient(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "mindications":

			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				String str = titleList.get(0);

				// 能取到高亮,输出高亮
				cm.setmIndications(str);
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "bookname":

			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				String str = titleList.get(0);

				// 能取到高亮,输出高亮
				cm.setBookname(str);
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "spagenumber":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.getsSource().setSpagenumber(titleList.get(0));

			} else {
				setCM(cm, field1, doc);
			}

			break;
		case "sprescription":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.getsSource().setSprescription(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "iid":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.getInstuctions().setIid(Integer.parseInt(titleList.get(0)));
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "isource":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.getInstuctions().setIsource(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}
			break;
		case "minteraction":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setmInteraction(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}

			break;
		case "mnegativereactions":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setmNegativereactions(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}

			break;
		case "mban":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setmBan(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}

			break;
		case "mnotice":
			titleList = highting.get(doc.get("id")).get(field1);
			if (titleList != null && titleList.size() > 0) {
				// 能取到高亮,输出高亮
				cm.setmNotice(titleList.get(0));
			} else {
				setCM(cm, field1, doc);
			}

			break;
		default:
			break;
		}
	}

	/**
	 * 工具方法 设置cm属性值
	 * 
	 * @param field
	 * @param doc
	 */
	public static void setCM(ChineseMedicine cm, String field, SolrDocument doc) {

		// 设置mid属性
		cm.setmId(Integer.parseInt(doc.get("id").toString()));// 配置文件已经将id域指向了mid

		switch (field) {
		case "mcname":
			cm.setMcName(doc.get("mcname").toString());
			break;
		case "mingredient":
			// 防止报错因为有些域是空值
			if (null != doc.get("mingredient")) {
				cm.setmIngredient(doc.get("mingredient").toString());
			} else {
				cm.setmIngredient("");
			}
			break;
		case "mindications":
			if (null != doc.get("mindications")) {
				cm.setmIndications(doc.get("mindications").toString());
			} else {
				cm.setmIndications("");
			}
			break;
		case "tname":
			if (null != doc.get("tname")) {
				cm.setTname(doc.get("tname").toString());
			} else {
				cm.setTname("");
			}
			break;
		case "bookname":
			if (null != doc.get("bookname")) {
				cm.setBookname(doc.get("bookname").toString());
			} else {
				cm.setBookname("");
			}
			break;
		case "spagenumber":
			if (null != doc.get("spagenumber")) {
				cm.getsSource().setSpagenumber(doc.get("spagenumber").toString());
			} else {
				cm.getsSource().setSpagenumber("");
			}
			break;
		case "sprescription":
			if (null != doc.get("sprescription")) {
				cm.getsSource().setSprescription(doc.get("sprescription").toString());
			} else {
				cm.getsSource().setSprescription("");
			}
			break;
		case "mnotice":
			if (null != doc.get("mnotice")) {
				cm.setmNotice(doc.get("mnotice").toString());
			} else {
				cm.setmNotice("");
			}
			break;
		case "minteraction":
			if (null != doc.get("minteraction")) {
				cm.setmInteraction(doc.get("minteraction").toString());
			} else {
				cm.setmInteraction("");
			}
			break;
		case "mnegativereactions":
			if (null != doc.get("mnegativereactions")) {
				cm.setmNegativereactions(doc.get("mnegativereactions").toString());
			} else {
				cm.setmNegativereactions("");
			}
			break;
		case "mban":
			if (null != doc.get("mban")) {
				cm.setmBan(doc.get("mban").toString());
			} else {
				cm.setmBan("");
			}
			break;
		case "iid":
			if (null != doc.get("iid")) {
				cm.getInstuctions().setIid(Integer.parseInt(doc.get("iid").toString()));
			}
			break;
		case "isource":
			if (null != doc.get("isource")) {
				cm.getInstuctions().setIsource(doc.get("iid").toString());
			}else{
				cm.getInstuctions().setIsource("");
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 设置域
	 */
	public static Set<String> setFields(String[] fields) {
		set1 = new HashSet<String>();
		for (String str : fields) {
			set1.add(str);
		}
		return set1;
	}

	/**
	 * 设置首页默认域
	 */
	public static void setDefultFields(String[] fields) {

		defultField = new HashSet<String>();
		for (String field : fields) {
			defultField.add(field);
		}
	}

	/**
	 * 获取默认域
	 */
	public static Set<String> getDefultFields() {

		return defultField;
	}

	/**
	 * 创建hashmap
	 */
	public static Map<String, String> getMap() {

		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	/**
	 * 设置单查询参数
	 * 
	 * @param fields
	 *            显示数据的域即显示页面table的头
	 * @param value
	 *            搜索的内容
	 * @param sFields
	 *            查询的域即表示从哪个地方查询的（目的是可以让系统知道查询哪个域）
	 * @return
	 */
	public static Map<Map<String, String>, Boolean> setSingleQueryParam(String value, String sFields, Boolean flag) {

		// 为了保证没有输入就是全查询
		if ("" == value.trim()) {
			maps = null;
		} else {
			Map<String, String> map = getMap();
			map.put(sFields, value);
			maps = new HashMap<Map<String, String>, Boolean>();
			maps.put(map, flag);
		}

		return maps;
	}

	/**
	 * 设置多域查询参数 3个数组一一对应即下标一致
	 * 
	 * 参数是json格式的需要进行转换
	 * 
	 * @param fields
	 *            查询域
	 * @param values
	 *            查询内容
	 * @param flag
	 *            每个域的标志：是与还是非
	 * @return
	 */
	public static Map<Map<String, String>, Boolean> setMultipleParam(String[] fields, String[] values, Boolean[] flag) {

		maps = new HashMap<Map<String, String>, Boolean>();
		boolean isExist = false;
		// 判断是否没有数据输入(solr索引库不能进行空查询)
		for (String str : values) {
			if (str.hashCode() == 0 || str.hashCode() == 1604226371) {

			} else {
				isExist = true;
				break;// 一旦数组中的values有内容就跳出循环
			}
		}

		if (isExist) {// 等于真的时候说明有内容即执行查询
			for (int i = 0; i < fields.length; i++) {
				map1 = getMap();
				// 传递上来的数组数据使用null判断不出来
				// 当对应域的位置上有查询内容的时候才添加
				// hashCode()==1604226371代表的是 ---请选择---字符串
				// hashCode()==0表示的是空文本框
				if (values[i].hashCode() == 0 || values[i].hashCode() == 1604226371) {

				} else {
					map1.put(fields[i], values[i]);
					maps.put(map1, flag[i]);
				}

			}
		} else {
			maps = null;
		}

		return maps;

	}
	
	/** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }  
}
