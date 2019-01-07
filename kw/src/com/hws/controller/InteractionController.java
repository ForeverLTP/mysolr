package com.hws.controller;

import java.util.ArrayList;
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
public class InteractionController {

	IndexService is = new IndexServiceImpl();

	@Autowired
	SQLService sqlService; 

	@RequestMapping(value = "initInteraction")
	public String initInteraction(Model model) {
		// 相互作用页面不操作的时候显示的一个元素
		// 18表示的是mid为18的药品
		ChineseMedicine chineseMedicine = sqlService.getCM(18);
		model.addAttribute("CHINESEMEDICINE", chineseMedicine);

		// 设置默认域
		String fields[] = { "mcname", "minteraction" };
		IndexUtils.setDefultFields(fields);
		if (!chineseMedicine.getmInteraction().equals("如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。")) {
			List<ChineseMedicine> cmList = is.singleSearch(fields, chineseMedicine.getmInteraction(), "mcname",
					true);
			model.addAttribute("CMLIST", cmList);
		} else {
			model.addAttribute("CMLIST", null);
		}
		return "interaction.jsp";

	}

	/**
	 * 获取药品的相互作用情况 Map<List<ChineseMedicine>,List<ChineseMedicine>>
	 * 左边的是通过名称查询的药品集合 右边是通过某一个名称查询的相互作用情况
	 * 
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "interactionSearch")
	@ResponseBody
	public List<ChineseMedicine> getInteraction(String value) {

		// Map<List<ChineseMedicine>,List<ChineseMedicine>> map = new
		// HashMap<List<ChineseMedicine>,List<ChineseMedicine>>();
 		String[] fields = { "mcname", "minteraction" };
		List<ChineseMedicine> cmListLeft = is.singleSearch(fields, value, "mcname", true);
		if(0 != cmListLeft.size()){
		ChineseMedicine chineseMedicine = cmListLeft.get(0);// 获取集合中第一个元素
		List<ChineseMedicine> cmListRight = null;
		if (!chineseMedicine.getmInteraction().trim().equals("如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。")) {
			cmListRight = is.singleSearch(null, chineseMedicine.getmInteraction(), "mcname", true);
			for(int i=0;i<cmListRight.size();i++){
				cmListRight.get(i).setmIndications("1");
			}
		}
		if (null != cmListRight) {
			cmListLeft.addAll(cmListRight);// 该方法不能添加空指针
		}
		}else{
			cmListLeft = null;
		}
		return cmListLeft;

	}

	@RequestMapping(value = "getSuitInteraction")
	@ResponseBody
	public List<ChineseMedicine> getSuitInteraction(String value) {

		List<ChineseMedicine> cmListRight = null;
		if (!value.trim().equals("如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。")) {
			cmListRight = is.singleSearch(null, value, "mcname", true);
		}
		return cmListRight;

	}

	/**
	 * 对比相互作用
	 * 
	 * @param value1
	 *            对应mcname1
	 * @param value2
	 *            对应mcname2
	 * @return
	 */
	@RequestMapping(value = "getCompareInteraction")
	@ResponseBody
	public List<ChineseMedicine> getCompareInteraction(String value1, String value2) {

		List<ChineseMedicine> all = new ArrayList<ChineseMedicine>();
		ChineseMedicine cm1 = sqlService.getCMByName(value1);
		ChineseMedicine cm2 = sqlService.getCMByName(value2);
		
		/*String indications1 = null;
		String indications2 = null;
		String interaction1 = null;
		String interaction2 = null;
		String newstr1 = ""; 
		String newstr2 = "";
		
		List<ChineseMedicine> all = new ArrayList<ChineseMedicine>();
		//Map<String, ChineseMedicine> map = new HashMap<String, ChineseMedicine>();
		if ("" == value1 || "" == value2) {
			all = null;
		} else {
			String[] fields = { "mcname", "minteraction", "mindications" };
			ChineseMedicine cm1 = sqlService.getCMByName(value1);
			ChineseMedicine cm2 = sqlService.getCMByName(value2);
			if (null != cm1 || null != cm2) {

				// 给对比双方药品进行上蓝颜色
				if (!cm1.getmInteraction().trim().equals("如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。")) {
					List<ChineseMedicine> list2 = is.singleSearch(fields, cm1.getmInteraction(), "mcname", true);
					
					    
					
					for (ChineseMedicine cm : list2) {
						newstr2 = cm.getMcName().replaceAll("<[.[^>]]*>","");
					    newstr2 = newstr2.replaceAll(" ", ""); 
					    
						if (value2.trim().equals(newstr2)) {
							
							indications2 = cm.getMcName();
							indications2 = indications2.replaceAll("red", "#79C3EB");
							
							break;
						}
					}
					List<ChineseMedicine> list1 = is.singleSearch(fields, cm2.getmIndications(), "mcname", true);
					for (ChineseMedicine cm : list1) {
						newstr1 = cm.getMcName().replaceAll("<[.[^>]]*>","");
						newstr1 = newstr1.replaceAll(" ", "");
						
						if (value1.trim().equals(newstr1)) {
							interaction1 = cm.getmInteraction();
							interaction1 = interaction1.replaceAll("red", "#79C3EB");
							
							break;
						}
					}

				}else{
					indications2 = cm2.getmIndications();
					interaction1 = cm1.getmInteraction();
				}

				if (!cm2.getmInteraction().trim().equals("如与其他药物同时使用可能会发生药物相互作用，详情请咨询医师或药师。")) {
					List<ChineseMedicine> list1 = is.singleSearch(fields, cm2.getmInteraction(), "mcname", true);
					for (ChineseMedicine cm : list1) {
						newstr1 = cm.getMcName().replaceAll("<[.[^>]]*>","");
						newstr1 = newstr1.replaceAll(" ", "");
						if (value1.trim().equals(newstr1)) {
							indications1 = cm.getMcName();
							break;
						}
					}
					List<ChineseMedicine> list2 = is.singleSearch(fields, cm1.getmIndications(), "mcname", true);
					for (ChineseMedicine cm : list2) {
						newstr2 = cm.getMcName().replaceAll("<[.[^>]]*>","");
						newstr2 = newstr2.replaceAll(" ", "");
						if (value2.trim().equals(newstr2)) {
							
							interaction2 = cm.getmInteraction();
							break;
						}
					}

				}else{
					indications1 = cm1.getmIndications();
					interaction2 = cm2.getmInteraction();
				}
			}
			cm1.setmInteraction(interaction1);
			cm1.setmIndications(indications1);
			cm2.setmIndications(indications2);
			cm2.setmInteraction(interaction2);*/
			
			all.add(cm1);
			all.add(cm2);
		//}
		return all;//ajax如果传回的是null值的话就会执行error块

	}
}
