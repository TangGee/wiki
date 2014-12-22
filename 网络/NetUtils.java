package com.ym.superlottery.net.utils;

import com.ym.superlottery.GlobalParamter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

//权限//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
public class NetUtils {

	
	/**
	 * 检查有无网络
	 */
	public static boolean checkNet(Context context)
	{
		//1 判断wifi链接么
		boolean isWIFI =isWIFIConnection(context);
		
		
		//2 判断 mobile 是否链接
		boolean isMOBILE =isMOBILEConnection(context);
		if(isMOBILE)
		{
			//如果是mobile链接 判断是否是 wap
			//如果是wap检查被选中apn的代理信息是否有内容 空表示net
			readAPN(context);
		}
		
		if(!isMOBILE&&!isWIFI)
			return false;
		
		return true;
		
		
		
	}

	
	/**
	 * 是否MOBILE联网
	 * @param context
	 * @return
	 */
	private static boolean isMOBILEConnection(Context context) {
		ConnectivityManager cm=	(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(info!=null)
		{
			return info.isConnected();
		}
		return false;
	}

	private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
		ContentResolver resolver=context.getContentResolver();
		Cursor cursor=resolver.query(PREFERRED_APN_URI , null, null, null, null);
		if(cursor!=null&&cursor.moveToFirst())
		{
			GlobalParamter.PROXY=cursor.getString(cursor.getColumnIndex("proxy"));
			GlobalParamter.PORT=cursor.getInt(cursor.getColumnIndex("port"));
			
		}
		cursor.close();
	}

	/**
	 * wifi是否链接
	 * @return
	 */
	private static boolean isWIFIConnection(Context context) {
		ConnectivityManager cm=	(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(info!=null)
		{
			return info.isConnected();
		}
		return false;
	}
}
