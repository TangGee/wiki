package com.ym.dao.base;

import java.io.Serializable;
import java.util.List;

public interface DAO<T> {
	
	
	long insert(T t);
	int update(T t);
	int delete(Serializable id);
	List<T> findAll();
	
	

}
