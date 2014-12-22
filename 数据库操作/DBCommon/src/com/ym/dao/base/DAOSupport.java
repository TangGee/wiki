
package com.ym.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ym.test.annotation.Column;
import com.ym.test.annotation.ID;
import com.ym.test.annotation.TableName;
import com.ym.test.dbhelper.MyDBHelper;


public abstract class DAOSupport<T> implements DAO<T>{

	protected String tableName;
	
	private MyDBHelper helper;
	private Context context;
	

	public DAOSupport(Context context) {

		this.context=context;
		helper=new MyDBHelper(context);
		initTableNmae();
	}
	
	/**
	 * ���
	 */
	public long insert(T t) {
		
		ContentValues values=new ContentValues();
		fillColumn(t,values);
		return helper.getWritableDatabase().insert(tableName, null, values);
	}


	/**
	 * 更新
	 */
	public int update(T t) {
		ContentValues values=new ContentValues();
		fillColumn(t,values);
		
		
		return helper.getWritableDatabase().update(tableName, values, "_id=?", new String[]{getId(t).toString()});
	}

	/**
	 * 删除 这里必须要获取表名才ok 所以创建了一个初始化的表名 在构造方法中调用 
	 * 记得在子类中调用super()
	 */
	public int delete(Serializable id) {
		return helper.getWritableDatabase().delete(tableName, "_id=?", new String[]{id.toString()});
	}

	public List<T> findAll() {
		
		List<T> allList=null;
		
		Cursor cursor=helper.getReadableDatabase().query(tableName, null, null, null, null, null, null);
		
		if(cursor!=null)
			allList=new ArrayList<T>();
		else
			return null;
		while(cursor.moveToNext())
		{
			
			addArgToBean(cursor,allList);
		}
		cursor.close();
		
		
		return allList;
	}
	


	/**
	 * 填充bean 并放到list中  
	 * @param cursor
	 * @param allList
	 */
	private void addArgToBean(Cursor cursor, List<T> allList) {
		try {
			T t=getTClass().newInstance();

			
			Field[] fields=t.getClass().getDeclaredFields();
			for(Field f: fields)
			{
				f.setAccessible(true);
				Column column=f.getAnnotation(Column.class);
				if(column!=null)
				{
					if(f.getType()==int.class)
					{
						f.set(t, cursor.getInt(cursor.getColumnIndex(column.value())));
					}else{
						f.set(t, cursor.getString(cursor.getColumnIndex(column.value())));
					}
				}
			}
			allList.add(t);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化表名
	 * 通过反射或去bean类的注解
	 */
	public void initTableNmae()
	{
		
		Class<T> clazz=getTClass();
		TableName name=clazz.getAnnotation(TableName.class);
		if(name!=null)
           tableName=name.value();
		else
			throw new NoClassDefFoundError("bean 类上可能咩有注解哦");
	}
	
	/**
	 * 通过字段上的注解ID获取id
	 * @param t
	 * @return
	 */
	private Serializable getId(T t)
	{
		Field[] fields=t.getClass().getDeclaredFields();
		for(Field f:fields)
		{
			f.setAccessible(true);
			
			ID id=f.getAnnotation(ID.class);
			if(id!=null&&id.autoincrement())
			{
				
				try {
					Serializable myid = (Serializable) f.get(t);
					return (myid);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
		}
		return null;
	}

	/**
	 * 填充value 对于update和insert有用
	 * @param t
	 * @param values
	 */
	private void fillColumn(T t, ContentValues values) {

		Field[] fields=t.getClass().getDeclaredFields();
		for(Field f:fields)
		{
			f.setAccessible(true);
			
			ID id=f.getAnnotation(ID.class);
			if(id!=null&&id.autoincrement())
				continue;
					
			
			Column column=f.getAnnotation(Column.class);
			if(column!=null)
			{
				try {
				String	value = (String) f.get(t);
					values.put(column.value(), value );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	
	/**
	 * 获取泛型的真正class类 由于泛型的实现是靠语法糖(编译后删除)
	 * @return
	 */
	public Class<T> getTClass()
	{
		
		ParameterizedType type=(ParameterizedType)this.getClass().getGenericSuperclass();
		return (Class<T>) (type).getActualTypeArguments()[0];
	}




}
