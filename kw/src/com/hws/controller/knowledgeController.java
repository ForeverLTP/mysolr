package com.hws.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hws.model.DiseaseCure;
import com.hws.model.Instructions;
import com.hws.model.Literature;
import com.hws.service.IndexService;
import com.hws.service.SQLService;
import com.hws.service.impl.IndexServiceImpl;
import com.hws.utils.IndexUtils;
import com.hws.utils.ReptileUtils;

@Controller
public class knowledgeController {

	IndexService is = new IndexServiceImpl();

	@Autowired
	SQLService sqlService;

	@RequestMapping(value = "getLiterature")
	public String getLiterature(Model model) {

		List<Literature> list = sqlService.getLiterature();

		model.addAttribute("LIST", list);
		return "literature.jsp";
	}

	@RequestMapping(value = "literatrueSearch")
	@ResponseBody
	public List<String> literatrueSearch(String value) {
		String fields[] = { "literature" };
		IndexUtils.setDefultFields(fields);
		List<String> strList = is.stringSearch(null, value, "literature", true);
		return strList;
	}

	@RequestMapping(value = "getInstruction")
	public String getInstruction(Model model) {

		List<Instructions> list = sqlService.getAllInstructions();

		model.addAttribute("LIST", list);
		return "instructions.jsp";
	}

	@RequestMapping(value = "instructionsSearch")
	@ResponseBody
	public List<Instructions> instructionsSearch(String value) {
		// String[] fields = {"mcname","iid","isource"};
		// List<ChineseMedicine> list = is.singleSearch(fields, value, "mcname",
		// true);
		String values = "%" + value + "%";

		List<Instructions> list = sqlService.getInstructionsByName(values);
		return list;
	}

	@RequestMapping(value = "downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String downloadUrl, String suffix)
			throws IOException {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/" + downloadUrl;

		URL url = new URL(basePath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		try {
			String fileName = "aaa." + suffix;
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			String len = String.valueOf(inputStream.available() / 1000 + "k");
			response.setHeader("Content-Length", len);
			OutputStream out = response.getOutputStream();
			byte[] getData = IndexUtils.readInputStream(inputStream);
			out.write(getData);
			inputStream.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "initDiseaseCure")
	public String initDiseaseCure(Model model, HttpServletRequest request) {

		
		HttpClient client = ReptileUtils.getHttpClient();
		HttpSession session = request.getSession();
		// List<DiseaseCure> title = new ArrayList<DiseaseCure>();
		// List<DiseaseCure> content = new ArrayList<DiseaseCure>();
		// Map<String,List<String>> map =new HashMap<String,List<String>>();
		// HttpClient client = new DefaultHttpClient();过时
		String url = "http://med.39.net/cds/jbzd/list2-1-2-7658-1.shtml";
		//String url = "http://med.39.net/cds/jbzd/list2-1-3-7659-1.shtml";
		try {
			List<DiseaseCure> titleList = ReptileUtils.ObjectParser(client, url, true);
			List<DiseaseCure> contentList = ReptileUtils.ObjectParser(client, url, false);
			session.setAttribute("TITLELIST", titleList);
			model.addAttribute("CONTENTLIST", contentList);
			/*
			 * List<String> list = ReptileUtils.URLParser(client, url);
			 * for(String str:list){ String html = "http://med.39.net/cds/jbzd/"
			 * + str; }
			 */

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "diseasecure.jsp";

	}

	@RequestMapping(value = "getDiseaseCures")
	@ResponseBody
	public List<DiseaseCure> getDiseaseCures(Model model, String value) {
		String head = "http://med.39.net/cds/jbzd/";
		String url = head + value;
		HttpClient client = ReptileUtils.getHttpClient();
		List<DiseaseCure> contentList = null;
		if ("" != value) {
			try {
				contentList = ReptileUtils.ObjectParser(client, url, false);// false表示查询 内容
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return contentList;

	}
	
	@RequestMapping(value = "getDiseaseCure")
	@ResponseBody
	public DiseaseCure getDiseaseCure(Model model, String value,String title) {
		
		DiseaseCure dc = new DiseaseCure();
		String head = "http://med.39.net/cds/jbzd/";
		String url = head + value;
		HttpClient client = ReptileUtils.getHttpClient();
		String content = null;
		if ("" != value) {
			try {
				content = ReptileUtils.StringParser(client, url);// false表示查询 内容
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != content){
				dc.setThreeleveltitle(title);
				dc.setContent(content);
			}
		}
		
		return dc;

	}
}
