package com.example.error;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class DemoActivity extends Activity {
	
	public native String  helloWorld();
	
	static{
		System.loadLibrary("hello");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		Toast.makeText(getApplicationContext(),  helloWorld(), 0).show();
	}
	
}
