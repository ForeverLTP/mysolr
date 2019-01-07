package com.hws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hws.model.ChineseMedicine;
import com.hws.model.SRDetails;
import com.hws.service.IndexService;
import com.hws.service.SQLService;
import com.hws.service.impl.IndexServiceImpl;

@Controller
public class IndicationsController {
	
	IndexService is = new IndexServiceImpl();

	List<ChineseMedicine> list = null;
	List<String> strList =null; 
	
	@Autowired
	SQLService sqlService;
	
	
	@RequestMapping(value="IndicationsInit")
	public String IndicationsInit(Model model){
		
		String[] fields = { "mcname", "bookname", "spagenumber","sprescription"};
		List<ChineseMedicine> list = is.findAll(fields);//索引库
		model.addAttribute("LIST", list);
		return "indications.jsp";
		
	}
	
	
	
	/**
	 * mcname智能提示
	 * @param field
	 * @param value
	 * @return
	 */
	@RequestMapping(value="mcnameSuggest")
	@ResponseBody
	public List<String> mcnameSuggest(String field,String value){
		String strValue = value + "*";
		strList = is.mcnameSuggest(strValue, field);
		
		return strList;
	}
	
	@RequestMapping(value="findSRDetails")
	public String findSRDetails(Model model,int mid){
		
		SRDetails srd = sqlService.getSRDetails(mid);
		model.addAttribute("SRD", srd);
		
		return "indications_details.jsp";
		
	}

}
