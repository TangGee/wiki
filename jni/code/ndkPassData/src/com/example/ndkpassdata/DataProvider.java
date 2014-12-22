package com.example.ndkpassdata;

public class DataProvider {
	
	/**
	 * 计算x和y的加法  apktools  
	 * 315 
	 * @param x
	 * @param y
	 * @return
	 */
	public native int add(int x ,int y);  // char   String   short   kiss  keep it simple and stupid  String[]  "123:234" 
	/**
	 * 给字符串后面拼装字符  加密运算   web   url  
	 * @param s
	 * @return
	 */
	public native String sayHelloInC(String s);
	//  
	/**
	 * 给c代码传递int数组   让c代码给这个数组进行操作
	 * 图形 声音的处理
	 * @param iNum
	 * @return
	 */
	public native int[] intMethod(int[] iNum); 

}
