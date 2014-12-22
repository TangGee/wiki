package com.ym.superlottery.net.utils;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ym.superlottery.GlobalParamter;
import com.ym.superlottery.consts.ConstClass;

public class HttpClientUtils {
	
	private HttpClient client;
	private HttpPost post;
	private HttpGet get;
	
	public HttpClientUtils() {
		
		client=new DefaultHttpClient();
		//判断是否需要设置代理信息
		if(StringUtils.isNotBlank(GlobalParamter.PROXY) )
		{
			//设置代理信息
			HttpHost host=new HttpHost(GlobalParamter.PROXY, GlobalParamter.PORT);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
		}
	}
	
	/**
	 * 向指定的链接发送xml文件
	 * @param uri
	 * @param xml
	 */
	public InputStream sendXml(String uri,String xml)
	{
		post=new HttpPost(uri);
		
		StringEntity entity;
		try {
			entity = new StringEntity(xml, ConstClass.ENCODING);
			post.setEntity(entity);
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==200)
			{
				return response.getEntity().getContent();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
