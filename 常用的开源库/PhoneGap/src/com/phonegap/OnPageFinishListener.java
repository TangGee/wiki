package com.phonegap;

import android.webkit.WebView;

/**
 * @author baigaojing
 * @version 1.1
 * date 2011-11-5
 * 监听网页加载完成事件
 * */
public interface OnPageFinishListener {
	/**
	 * @param view 当前的WebView对象
	 * @param url 加载完成的链接
	 * */
	void onPageFinish(WebView view, String url);
}
