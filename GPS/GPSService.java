package com.ym.phonehelper.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

public class GPSService extends Service{

	private LocationManager lm;
    private MyLocationListener listener;;	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("AAAA"," 服務開啓");
		
		//用到位置服务
	            lm=(LocationManager) getSystemService(LOCATION_SERVICE);
//	            List<String>provider= lm.getAllProviders();
//	            for(String l:provider)
//	            {
//	            	Log.i("AAAA",l);
//	            }
	            //第二个参数建议60000  第三个是多少米更新位置
	            listener=new MyLocationListener();
	            //给位置提供者设置条件
	            Criteria criteria=new Criteria();
	            criteria.setAccuracy(Criteria.ACCURACY_FINE);
	            String proveder=lm.getBestProvider(criteria, true);
	            if(TextUtils.isEmpty(proveder))
	            {
	            	proveder=LocationManager.GPS_PROVIDER;
	            }
	            lm.requestLocationUpdates(proveder, 60000,50,listener);
	        

		
	}
	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	  	super.onDestroy();
    	lm.removeUpdates(listener);
    	listener=null;
	}
	
	  private class  MyLocationListener implements LocationListener{

	    	//当位置改变回调
			@Override
			public void onLocationChanged(Location location) {
				try {
					double d1=location.getLongitude();
					double d2=location.getLatitude();
				//	pointDouble=GPSUtils.getHouxin(d1,d2);
					//经度
					String longtiude="j:"+d1+"\n";
					String  latitude="w:"+d2+"\n";
					String accuracy="a:"+location.getAccuracy()+"\n";
					//转换火星坐标
					//发短信给安全号码 
					SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
					
					Log.i("AAAA", location+latitude+accuracy);
					sp.edit().putString("lastlocation", location+latitude+accuracy).commit();
					
				} catch (Exception e) {
				}
	
				
				
			}

			//状态改变回调 开启到关闭
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}

			//某个位置提供者可用 回调 
			@Override
			public void onProviderEnabled(String provider) {
				
			}

			//某个位置提供者不可用 回调
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
	    	
	    }


}
