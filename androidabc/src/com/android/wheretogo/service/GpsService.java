package com.android.wheretogo.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GpsService extends Service {
	Location location;
	String bestProvider;
	LocationManager lm;
	LocationListener locationListener;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate() {
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(true);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		bestProvider = lm.getBestProvider(criteria, true);
		location = lm.getLastKnownLocation(bestProvider);
		
		locationListener = new LocationListener() {

			// 当位置改变时触发
			@Override
			public void onLocationChanged(Location location) {
				Log.i("yao", location.toString());
				updateLocation(location);
			}

			// Provider失效时触发
			@Override
			public void onProviderDisabled(String arg0) {
				Log.i("yao", arg0);

			}

			// Provider可用时触发
			@Override
			public void onProviderEnabled(String arg0) {
				Log.i("yao", arg0);
			}

			// Provider状态改变时触发
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				Log.i("yao", "onStatusChanged");
			}
		};
		lm.requestLocationUpdates(bestProvider, 500, 0, locationListener);
	}
	
	public void onDestroy() {
		lm.removeUpdates(locationListener);
	}
	//将gps数据截为小数点后六位
	public String AdjustGpsData(String gpsdata){
		String result=null;
		Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]{6}");
		Matcher matcher = pattern.matcher(gpsdata);
		matcher.find();
		return result= matcher.group();
	}
	private void updateLocation(Location location) {
		if (location != null) {
			SharedPreferences gps = getSharedPreferences("gps", 0);
			gps.edit().putString("longi", AdjustGpsData(String.valueOf(location.getLongitude())))
			.putString("lati", AdjustGpsData(String.valueOf(location.getLatitude())))
			.commit();
		} else {
			Log.i("yao", "没有获取到定位对象Location");
		}

	}
}
