package com.example.redbaby.utils;

public class FileUtil {

	public static native long getFileAccessTime(String path);
	public static native long howmanyTimeUnAccess(String path);
	public static native long data();
	
	static{
		System.loadLibrary("fileutil");
	}
	
}
