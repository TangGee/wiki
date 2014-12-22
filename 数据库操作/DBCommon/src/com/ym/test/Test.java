package com.ym.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.ym.bean.News;
import com.ym.dao.NewsDAO;
import com.ym.dao.imp.NewsDaoImpl;

public class Test extends AndroidTestCase{

	
	public void insert( ) throws Exception
	{
		NewsDAO<News> newsDAO=new NewsDaoImpl(getContext());
		
		News news =new News(" 你好郭德纲11111111111","qweqwe");
		newsDAO.insert(news);
	}
	
	public void findAll( ) throws Exception
	{
		NewsDAO<News> newsDAO=new NewsDaoImpl(getContext());
		
		List<News> newss=newsDAO.findAll();
		for(News s: newss)
		{
			Log.i("AAAA", s.toString());
			
		}
		
	}
	
	
	
}
