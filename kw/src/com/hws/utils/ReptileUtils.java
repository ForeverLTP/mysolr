package com.hws.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hws.model.DiseaseCure;
import com.hws.model.TitleModel;

/**
 * 爬虫工具类
 * 
 * @author Administrator
 *
 */
public class ReptileUtils {
	
	static CharFileter cf = new CharFileter();
	
	static HttpClient client = null;
	static{
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	public static HttpClient getHttpClient(){
		return client;
	}
	public static List<String> getUrls(HttpClient client, String targetUrl) {

		List<String> list = new ArrayList<String>();
		// 采用Jsoup解析
		Document doc = Jsoup.parse(targetUrl);
		// 获取html标签中的内容
		Elements elements = doc.select(".lc_left_c h2 a");
		for (Element ele : elements) {
			String url = ele.attr("href");// 获取相对路径
			list.add(url);
		}
		return list;

	}

	/**
	 * 解析url地址
	 * 
	 * @param client
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static List<String> URLParser(HttpClient client, String url) throws Exception {
		// 用来接收解析的数据
		List<String> data = null;
		// 获取网站响应的html，这里调用了HTTPUtils类
		HttpResponse response = HTTPUtils.getRawHtml(client, url);
		// 获取响应状态码
		int StatusCode = response.getStatusLine().getStatusCode();
		// 如果状态响应码为200，则获取html实体内容或者json文件
		if (StatusCode == 200) {
			String entity = EntityUtils.toString(response.getEntity(), "utf-8");
			data = getUrls(client, entity);
			EntityUtils.consume(response.getEntity());
		} else {
			// 否则，消耗掉实体
			EntityUtils.consume(response.getEntity());
		}
		return data;
	}

	/**
	 * 解析返回对象
	 * 
	 * @param client
	 * @param url
	 * @param flag
	 * @param sign
	 *            ： ture表示查找title false：表示查找内容
	 * @return
	 * @throws Exception
	 */
	public static List<DiseaseCure> ObjectParser(HttpClient client, String url, boolean sign) throws Exception {
		// 用来接收解析的数据
		List<DiseaseCure> data = null;
		// 获取网站响应的html，这里调用了HTTPUtils类
		HttpResponse response = HTTPUtils.getRawHtml(client, url);
		// 获取响应状态码
		int StatusCode = response.getStatusLine().getStatusCode();
		// 如果状态响应码为200，则获取html实体内容或者json文件
		if (StatusCode == 200) {
			String entity = EntityUtils.toString(response.getEntity(), "utf-8");
			if (sign) {
				data = getTitle(client, entity);
			} else {
				data = getContent(client, entity);
			}

			EntityUtils.consume(response.getEntity());
		} else {
			// 否则，消耗掉实体
			EntityUtils.consume(response.getEntity());
		}
		return data;
	}

	/**
	 * 解析url地址获取标题
	 * 
	 * @param client
	 * @param html
	 *            ：网址
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public static List<DiseaseCure> getTitle(HttpClient client, String html) throws Exception {
		// 获取的数据，存放在集合中
		List<DiseaseCure> titleList = new ArrayList<DiseaseCure>();
		// List<DiseaseCure> data = new ArrayList<DiseaseCure>();
		// Map<List<DiseaseCure>, List<DiseaseCure>> map = new
		// HashMap<List<DiseaseCure>, List<DiseaseCure>>();
		int count = 0;// 目的:设置类型id

		// int flag = 0;
		// 采用Jsoup解析
		Document doc = Jsoup.parse(html);
		// 获取html标签中的内容
		Elements elements = doc.select("#form1");// 把中间内容全部拷贝
		for (Element ele : elements) {

			Elements elementsOne = ele.select(".lc_left_c h2 a");// 一级标题
			int i = 0;
			for (Element one : elementsOne) {
				// 目的为了使得大标题和二级标题对应
				i++;
				DiseaseCure dCure = new DiseaseCure();// 创建封装对象
				List<TitleModel> tmList = new ArrayList<TitleModel>();
				// DiseaseCure title = new DiseaseCure();// 创建封装对象
				dCure.setOneleveltitle(one.text());
				String address1 = "#LeftNav_" + i + " li a";
				Elements elementsTwo = ele.select(address1);// 二级标题
				for (Element two : elementsTwo) {
					count++;
					TitleModel tm = new TitleModel();// 标题模板
					dCure.setDtypeid(count);
					tm.setTitle(two.text());
					tm.setTitleAddress(two.attr("href"));
					tmList.add(tm);
					// int j = 0;
					// dCure.setTwoleveltitle(two.text());
					// title = dCure;
					// dCure.setTitleaddress(two.attr("href"));
					// titleList.add(dCure);
					/*
					 * if (j == 0) { Elements elementsThree =
					 * ele.select(".lc_Rlb_c li");// 三级标题 for (Element three :
					 * elementsThree) {
					 * 
					 * dCure.setThreeleveltitle(three.select("a").text());
					 * dCure.setDaddress(three.select("a").attr("href"));
					 * dCure.setContent(three.select("p").html());
					 * data.add(dCure); } }
					 */
					// j++;
				}
				dCure.setTwoTitleList(tmList);
				titleList.add(dCure);
			}
		}
		// map.put(titileList, data);
		// 返回数据
		return titleList;
	}

	public static String StringParser(HttpClient client, String url) throws Exception {
		// 用来接收解析的数据
		String data = null;
		// 获取网站响应的html，这里调用了HTTPUtils类
		HttpResponse response = HTTPUtils.getRawHtml(client, url);
		// 获取响应状态码
		int StatusCode = response.getStatusLine().getStatusCode();
		// 如果状态响应码为200，则获取html实体内容或者json文件
		if (StatusCode == 200) {
			String entity = EntityUtils.toString(response.getEntity(), "utf-8");
			data = getString(client, entity);
			EntityUtils.consume(response.getEntity());
		} else {
			// 否则，消耗掉实体
			EntityUtils.consume(response.getEntity());
		}
		return data;
	}
	/**
	 * 获取粗略内容
	 * @param client
	 * @param html
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static List<DiseaseCure> getContent(HttpClient client, String html) throws UnsupportedEncodingException {

		
		List<DiseaseCure> data = new ArrayList<DiseaseCure>();
		Document doc = Jsoup.parse(html);
		Elements ele = doc.select(".lc_Rlb_c li");
		for (Element el : ele) {
			DiseaseCure dCure = new DiseaseCure();// 创建封装对象
			String threeTitle = el.select("a").text();
			String titleAddress = el.select("a").attr("href");
			String content = el.select("p").html();
			dCure.setThreeleveltitle(threeTitle);
			dCure.setDaddress(titleAddress);
			dCure.setContent(content);
			data.add(dCure);
		}

		return data;

	}

	/**
	 * 获取具体内容
	 * @param client
	 * @param html
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getString(HttpClient client, String html) throws UnsupportedEncodingException {

		Document doc = Jsoup.parse(html);
		Elements ele = doc.select(".lc_Rrn_c");
		String data = null;
		for (Element el : ele) {
			data = el.html();
			
		}

		return data;

	}

}
