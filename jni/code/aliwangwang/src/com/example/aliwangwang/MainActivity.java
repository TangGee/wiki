package com.example.aliwangwang;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	static{
		System.loadLibrary("hello");
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	// �жϵ�½�����Ƿ���ȷ
	public native int login(String password);
			// �����������ַ  
			//  ����   ����ֵ  404   500   200  302
	
	public void click(View view){
		int i=login("123");
		Toast.makeText(getApplicationContext(), i+"", 0).show();
	}

}
