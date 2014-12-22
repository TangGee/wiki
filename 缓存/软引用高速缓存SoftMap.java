package com.ym.superlottery.view.utils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * 軟引用的Map
 * @author tlinux
 *
 * @param <K>
 * @param <V>
 */
public class SoftMap<K,V> extends HashMap<K, V>{
	
	
	private HashMap<K, SoftValue> myHashMap=new HashMap<K, SoftValue>();
	//Reference记录被回收的引用
	private  ReferenceQueue<V> referenceQueue=new ReferenceQueue<V>();
	
	//降低對象的引用級別  
	//降低value的級別
    public SoftMap()
    {
//    	Object v=new Object();
//    	SoftReference sr=new SoftReference(v);
    	
    }
    
    @Override
    public V put(K key, V value) {
    	
    	SoftValue softReference=new SoftValue(key,value,referenceQueue);
    	myHashMap.put(key, softReference);
    	
    	return null;
    }
	
@Override
public V get(Object key) {
	clear();
	Reference<V> sr= myHashMap.get(key);
	if(sr!=null)
	{
		//如果垃圾回收此方法返回null
		return sr.get();
	}
	return null; 
}

@Override
public boolean containsKey(Object key) {
	clear();

	if(myHashMap.get(key)!=null)
		return true;
	return false;
}

public void clear()
{
	SoftValue rf=null;
	while((rf=(SoftValue) referenceQueue.poll())!=null)
	{
		myHashMap.remove(rf);
		
	}
	
	
	
}

/**
 * 曾将版本的袋子
 * @author tlinux
 *
 */
private class SoftValue extends SoftReference<V>
{

	public Object key;
	public SoftValue(K key,V r, ReferenceQueue<? super V> q) {
		super(r, q);
		this.key=key;
		
		
	}

	
	
}

	
}
