package com.example.ndkcallback;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	DataProvider dp;
	static{
		System.loadLibrary("hello");
		
	}
	public void helloFromJava(){
		System.out.println("哈哈哈  我被调用了");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dp=new DataProvider();
	}
	public void click1(View view){
		dp.callMethod1();
		
	}
	public void click2(View view){
		dp.callMethod2();
	}
	public void click3(View view){
		dp.callMethod3();
	}
	public void click4(View view){
		dp.callMethod4();
	}
	public void click5(View view){
		dp.callMethod5();
	}
}
