package com.example.helloworldfromc2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	// ��һ�� �������صķ��� 
	public native String h_e_1_llo(); // 
	
	static{
		// libhello.so
		System.loadLibrary("hello");
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(getApplicationContext(), h_e_1_llo(), 0).show();
	}


}
