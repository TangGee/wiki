package com.example.helloworldformc;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	// 1 ����һ��c�����Ľӿ�   �൱����java�����ж�����һ���ӿ� �ӿڵ�ʵ�ַ�����C����ʵ�ֵ�
	public native String helloWorldFromC();
	// ��̬����
	public native String hello_world_from_c();
	//
	//public native String hello_1_11_11world_form__1_1_c();
	// 5 ����java������ ����⺯�� 
	static{
		System.loadLibrary("hello");// ע������ ȥ��ǰ���lib �����.so
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void click(View view){
		// ����һ����˾ ��˾������ ��c����д���� 	
		// ��6��
		Toast.makeText(getApplicationContext(), hello_world_from_c(), 0).show();
		
	}
}
