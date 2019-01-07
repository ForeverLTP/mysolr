package com.hws.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hws.utils.IndexUtils;

public class Test1 {
	
	 public void  downLoad(HttpServletRequest request,HttpServletResponse response,String downloadUrl) throws IOException{  
	        URL url = new URL(downloadUrl);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();  
	        try {
	            String fileName = "aaa.jpg";
	            fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
	            response.setContentType("application/octet-stream");
	            response.addHeader("Content-Disposition", "attachment;filename="+fileName);
	            String len = String.valueOf(inputStream.available()/1000+"k");
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

}
