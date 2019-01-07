package com.hws.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hws.model.CMType;
import com.hws.model.ChineseMedicine;
import com.hws.model.SRType;
import com.hws.service.IndexService;
import com.hws.service.SQLService;
import com.hws.service.impl.IndexServiceImpl;
import com.hws.utils.IndexUtils;
import com.hws.utils.SimilarUtils;

@Controller
public class HomeController {

	IndexService is = new IndexServiceImpl();

	List<ChineseMedicine> list = null;
	List<String> strList =null; 
	ChineseMedicine cm = null;
	//List<List<String>> strLists = null;

	@Autowired
	SQLService sqlService;

	/**
	 * 数据初始化
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "init")
	public String init(Model model,HttpServletRequest request) {

		//默认域
		String[] fields = { "mcname", "mingredient", "mindications" };
		List<ChineseMedicine> list = is.findAll(fields);//索引库
		List<CMType> types = sqlService.findAllType();//连接数据库
		List<SRType> srTypes = sqlService.findAllSRType();//连接数据库
		
		HttpSession session = request.getSession();
		model.addAttribute("LIST", list);
		model.addAttribute("TYPES", types);
		session.setAttribute("SRTYPES", srTypes);
		//model.addAttribute("SRTYPES", srTypes);
		return "index.jsp";

	}

	/**
	 * 全文检索 ajax局部页面刷新
	 * 
	 * @param value
	 * @param field
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "fullsearch")
	@ResponseBody
	public List<ChineseMedicine> fullTextSearch(String value, String field) throws IOException {

		String[] fields = { "mcname", "mingredient", "mindications" };
		// fields 要显示的域
		// value 查询内容
		// field 要查询的域
		// true 设置是否排除还是不排除
		list = is.singleSearch(fields, value, field, true);
		return list;
	}

	/**
	 * 普通搜索
	 * 
	 * @param fields
	 * @param values
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "multipleSearch")
	@ResponseBody
	public List<ChineseMedicine> multipleSearch(String[] fields, String[] values, Boolean[] flag,String[] checkFlag) {

		if (null != values) {

			// null表示使用默认域（这个域是指要展示数据的域即页面显示数据的域）
			list = is.multipleSearch(null, IndexUtils.setMultipleParam(fields, values, flag));
			if(null !=checkFlag){
				List<String> str = Arrays.asList(checkFlag);
				Iterator<ChineseMedicine> it = list.iterator();
				while(it.hasNext()){
					ChineseMedicine cMedicine = it.next();
					if(str.contains("1")){
						if("" != values[0]){
						//获取字符串相似度
						double similarValue = SimilarUtils.getSimilarValue(SimilarUtils.compare( cMedicine.getMcName(),values[0]), values[0]);	
						if(similarValue<0.8){
							it.remove();
						}
						}
					}
					
					if(str.contains("2")){
						if("" != values[1]){
						//获取字符串相似度
							double similarValue = SimilarUtils.getSimilarValue(SimilarUtils.compare( cMedicine.getmIndications(),values[1]), values[1]);	
						if(similarValue<0.8){
							it.remove();
						}
						}
					}
					
					if(str.contains("3")){
						if("" != values[2]){
						//获取字符串相似度
							double similarValue = SimilarUtils.getSimilarValue(SimilarUtils.compare( cMedicine.getmIngredient(),values[2]), values[2]);	
						if(similarValue<0.8){
							it.remove();
						}
						}
					}
				}
			
			}
		
		} else {
			// 没有查询内容的话就查询全部记录
			list = is.multipleSearch(null, null);
		}
		return list;

	}

	/**
	 * 高级搜索
	 * 
	 * @param fields
	 * @param values
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "singleFieldSearch")
	@ResponseBody
	public List<ChineseMedicine> singleFieldSearch(String[] fields, String[] values, Boolean[] flag) {

		for (int i = 0; i < values.length; i++) {
			if (values[i].hashCode() != 0) {
				switch (i) {
				case 0:
					break;// 不做处理
				case 1:
					break;// 不做处理
				case 2:
					values[i] = "\"" + values[i] + "\"";// 对搜索内容添加双引号
					break;
				case 3:
					// 以空格为标准进行切割
					String[] content = values[i].split("\\s+");
					int cLength = values[i].length();
					for (int j = 0; j < content.length; j++) {
						values[i] += "\"" + content[j] + "\"" + " AND mall:";
					}
					values[i] = values[i].substring(cLength, values[i].length() - 10);
					break;
				}
			}
		}

		list = is.multipleSearch(null, IndexUtils.setMultipleParam(fields, values, flag));
		return list;

	}

	/**
	 * 前缀查询
	 * 
	 * @param value
	 * @param sFields
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "prefixSearch")
	@ResponseBody
	public List<String> prefixSearch(String value) {

		/*switch (fields) {
		case "fulltext":
			fields = "mall";
			break;
		}
		values = values + "*" ;
		strList = is.prefixSearch(values, fields, flag);*/

		strList = is.prefixSearch(value);
		
		
		return strList;

	}
	
	/**
	 * 查看药品详细信息
	 * @param model
	 * @param mid
	 * @return
	 */
	@RequestMapping(value="cmdetails")
	public String getDetails(Model model,int mid){

		cm = sqlService.getCM(mid);
		model.addAttribute("CM", cm);
		
		return "cmdetails.jsp";
		
	}
	
	/**
	 * 查看处方
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getIndications")
	public String getIndications(Model model,String mid){
		
		String[] fields = { "mcname", "bookname", "spagenumber","sprescription"};
		IndexUtils.setDefultFields(fields);//设置默认域(在Indications.jsp后续操作中需要使用到默认域)
		String field = "id";//id是唯一的目的是精确查询
		list = is.singleSearch(fields, mid, field, true);//索引库
		model.addAttribute("LIST", list);
		return "indications.jsp";
		
	}
}
