package com.hws.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;

public abstract class HTTPUtils {
    public static HttpResponse getRawHtml(HttpClient client, String personalUrl) throws URISyntaxException, MalformedURLException {
        //获取响应文件，即html，采用get方法获取响应数据
    	URL url = new URL(personalUrl);   
        URI url1 = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);   
        HttpGet getMethod = new HttpGet(url1);
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        try {
            //执行get方法
            response = client.execute(getMethod);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // getMethod.abort();
        }
        return response;
    }

}