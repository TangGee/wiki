package com.ym.dao.imp;

import android.content.Context;

import com.ym.bean.News;
import com.ym.dao.NewsDAO;
import com.ym.dao.base.DAOSupport;

public class NewsDaoImpl extends DAOSupport<News> implements NewsDAO<News>{

	public NewsDaoImpl(Context context) {

		super(context);
	}
}
