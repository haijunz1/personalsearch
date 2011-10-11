package com.android.wheretogo.adapter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.wheretogo.activity.ActivityGo;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomerOverlayMyloc extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	private Context context;
	private SharedPreferences gps=null;
	public CustomerOverlayMyloc(Drawable defaultMarker,Context context) {
		super(defaultMarker);
	    this.context= context;
		boundCenterBottom(defaultMarker);
		setOverlayItemList();
		populate();
	}

	public void setOverlayItemList() {
		gps = context.getSharedPreferences("gps", 0);
		if(gps!=null){
			String lati = gps.getString("lati",null);
			String longi =gps.getString("longi",null);
		if(lati!=null&&longi!=null){
			lati=AdjustGpsData(lati);
			longi=AdjustGpsData(longi);
			GeoPoint point = new GeoPoint( Integer.parseInt(lati),Integer.parseInt(longi));
			OverlayItem overlayItem = new OverlayItem(point, "Hello",
					"I'm in tao, Greece!");
			overlayItemList.add(overlayItem);
		}
		}
		
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlayItemList.get(i);
	}

	@Override
	public int size() {
		return overlayItemList.size();
	}

	public void addOverlay(OverlayItem overlay) {
		overlayItemList.add(overlay);
		this.populate();
	}
	public String AdjustGpsData(String gpsdata){
		String result=null;
		result = Pattern.compile( "\\.").matcher(gpsdata).replaceAll(""); 
		return result;
	}

}
	
//}// end of CustomItemizedOverlay
