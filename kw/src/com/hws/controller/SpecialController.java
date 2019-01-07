package com.hws.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hws.model.ChineseMedicine;
import com.hws.service.IndexService;
import com.hws.service.SQLService;
import com.hws.service.impl.IndexServiceImpl;
import com.hws.utils.IndexUtils;

@Controller
public class SpecialController {
	
	IndexService is = new IndexServiceImpl();
	
	
	@Autowired
	SQLService sqlService;
	
	@RequestMapping(value="initSpecial")
	public String initSpecial(Model model){
		
		String fields1[] ={"mcname","mnegativereactions","mban","mnotice"};
		IndexUtils.setDefultFields(fields1);//设置默认显示域
		List<ChineseMedicine> list = sqlService.getAllCM();
		model.addAttribute("LIST", list);
		return "special.jsp";
	}
	
	@RequestMapping(value="getSpecials")
	@ResponseBody
	public List<ChineseMedicine> getSpecials(String value){
		value = value.trim();
		String[] values = value.split("\\s+");//通过空格切分
		Boolean[] flag = new Boolean[values.length];
		String fields[]=new String[values.length];
		Arrays.fill(flag,true);//初始化数组
		Arrays.fill(fields,"mspecial");//初始化数组
		
		//该页面初始化的时候设置了默认域所以使用null即可
		List<ChineseMedicine> list = is.multipleSearch(null, IndexUtils.setMultipleParam(fields, values, flag));
		return list;
	}

}
