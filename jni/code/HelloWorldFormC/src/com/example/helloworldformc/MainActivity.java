package com.example.helloworldformc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 1 定义一个c方法的接口   相当于在java代码中定义了一个接口 接口的实现方法是C语言实现的
	public native String helloWorldFromC();
	// 变态命名
	public native String hello_world_from_c();
	//
	//public native String hello_1_11_11world_form__1_1_c();
	// 5 步在java代码中 引入库函数 
	static{
		System.loadLibrary("hello");// 注意事项 去掉前面的lib 后面的.so
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void click(View view){
		// 弹出一个土司 土司的内容 是c代码写出来 	
		// 第6步
		Toast.makeText(getApplicationContext(), hello_world_from_c(), 0).show();
		
	}
}
