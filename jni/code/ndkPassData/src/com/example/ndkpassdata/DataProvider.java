package com.example.ndkpassdata;

public class DataProvider {
	
	/**
	 * ����x��y�ļӷ�  apktools  
	 * 315 
	 * @param x
	 * @param y
	 * @return
	 */
	public native int add(int x ,int y);  // char   String   short   kiss  keep it simple and stupid  String[]  "123:234" 
	/**
	 * ���ַ�������ƴװ�ַ�  ��������   web   url  
	 * @param s
	 * @return
	 */
	public native String sayHelloInC(String s);
	//  
	/**
	 * ��c���봫��int����   ��c��������������в���
	 * ͼ�� �����Ĵ���
	 * @param iNum
	 * @return
	 */
	public native int[] intMethod(int[] iNum); 

}
