package com.example.ndkcallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DataProvider {
	//C调用java空方法
	public void helloFromJava(){
		System.out.println("哈哈哈  我被调用了");
	}
	//C调用java中的带两个int参数的方法
	public int Add(int x,int y){
		 int result=x+y;
		System.out.println("result:"+result);
		return result;
	}
	//C调用java中参数为string的方法
	public void printString(String s){
		System.out.println(s);
	}
	
	public static void demo(){
		System.out.println("哈哈哈,我是静态方法");
		
	}
	
	public native void callMethod1();
	public native void callMethod2();
	public native void callMethod3();
	public native void callMethod4();
	public native void callMethod5();
}
