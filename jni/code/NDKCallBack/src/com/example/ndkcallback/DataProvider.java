package com.example.ndkcallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DataProvider {
	//C����java�շ���
	public void helloFromJava(){
		System.out.println("������  �ұ�������");
	}
	//C����java�еĴ�����int�����ķ���
	public int Add(int x,int y){
		 int result=x+y;
		System.out.println("result:"+result);
		return result;
	}
	//C����java�в���Ϊstring�ķ���
	public void printString(String s){
		System.out.println(s);
	}
	
	public static void demo(){
		System.out.println("������,���Ǿ�̬����");
		
	}
	
	public native void callMethod1();
	public native void callMethod2();
	public native void callMethod3();
	public native void callMethod4();
	public native void callMethod5();
}
