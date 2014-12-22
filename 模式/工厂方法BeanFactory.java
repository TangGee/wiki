package com.ym.superlottery.engine.utils;

import java.io.IOException;
import java.util.Properties;

import com.ym.superlottery.engine.UserEngine;

/**
 * 工厂文件
 * @author tlinux
 *
 */
public class BeanFactory {
	//一句配置文件加载文件
	private static Properties properties;
	
	static {
		try {
			properties=new Properties();
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static<T>  T getImpl(Class<T> clazz)
	{
		String key=clazz.getSimpleName();
		String className=(String) properties.get(key);
		
		try {
			return (T) clazz.forName(className).newInstance();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
