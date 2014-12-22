package com.example.redbaby;

import com.example.redbaby.utils.FileUtil;

import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase{
	
	
	public void test() throws Exception
	{
		Log.i("AAAA", "当前时间"+FileUtil.data());
		
		Log.i("AAAA", "上次访问时间"+FileUtil.getFileAccessTime(getContext().getFilesDir().getAbsolutePath())+"");
		Log.i("AAAA", "多久没访问"+FileUtil.howmanyTimeUnAccess(getContext().getFilesDir().getAbsolutePath())+"");
	}

}
