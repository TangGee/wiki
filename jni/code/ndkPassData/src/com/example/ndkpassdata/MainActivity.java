package com.example.ndkpassdata;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	DataProvider provider;
	static{
		System.loadLibrary("hello");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		provider=new DataProvider();
		
	}
	
	public void click1(View view){
		int result=provider.add(3, 5);
		System.out.println(result);
	}
	
	public void click2(View view){
		String str=provider.sayHelloInC("yll");
		Toast.makeText(getApplicationContext(), str, 0).show();
		
	}
	public void click3(View view){
		int[] arr=new int[]{1,2,3,4,5};
		provider.intMethod(arr);
		for(int i:arr){
			System.out.println(i);
		}
		
	}
}
