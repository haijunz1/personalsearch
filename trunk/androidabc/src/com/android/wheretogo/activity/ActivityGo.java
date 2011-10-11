package com.android.wheretogo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.wheretogo.adapter.CustomerOverlayAllpic;
import com.android.wheretogo.adapter.CustomerOverlayMyloc;
import com.android.wheretogo.adapter.DbAdapter;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ActivityGo extends MapActivity {
	
	private static final int SATI = Menu.FIRST;
	private static final int STREET = Menu.FIRST + 1;
    private static final int HALFSIDE = 400;
    
	DbAdapter dbAdapter = null;
	String Tag = "Mapshow";
	MapView mv;
	CustomerOverlayAllpic cuslay1;
	CustomerOverlayMyloc cuslay2;
	MapController mapController;
	private SharedPreferences gps=null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		setTitle("GoogleMapView");
		mv = (MapView) findViewById(R.id.mv);
		mv.setBuiltInZoomControls(true);
		mv.setSatellite(true);
		mapController = mv.getController();
		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		
		Drawable drawable1 = this.getResources().getDrawable(R.drawable.note);
		Drawable drawable2 = this.getResources().getDrawable(R.drawable.myloc);
		List mapOverlays = mv.getOverlays();
		
		cuslay1 = new CustomerOverlayAllpic(drawable1,this);
		mapOverlays.add(cuslay1);
		
		cuslay2=new CustomerOverlayMyloc(drawable2,this);
		mapOverlays.add(cuslay2);
		
//		if( (gps = getSharedPreferences("gps", 0))!=null)
//			if( !gps.getString("longi", "").equals("")&&!gps.getString("lati", "").equals("")){
//				cuslay2=new CustomerOverlayMyloc(drawable2,this);
//				mapOverlays.add(cuslay2);
//				mapController.setCenter(cuslay2.getCenter());
//			}
		
		if(cuslay2.size()>0){
			mapController.setCenter(cuslay2.getCenter());
		}else if(cuslay1.size()>0){
			mapController.setCenter(cuslay1.getCenter());
		}else{
		mapController.setCenter(new GeoPoint(39979311,116343133));
		}
		mapController.setZoom(17);
	}



	// end of CustomItemizedOverlay

	public void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}
	


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SATI, 0, "卫星图");
		menu.add(0, STREET, 1, "普通图");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case SATI:
			mv.setSatellite(true);
			break;

		case STREET:
			mv.setSatellite(false);
			// mv.setStreetView(true);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
