package com.android.wheretogo.adapter;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.android.wheretogo.activity.ActivityMypic;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CustomerOverlayAllpic extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	private Context context;
	private ArrayList<Integer> longi = new ArrayList<Integer>();
	private ArrayList<Integer> lati = new ArrayList<Integer>();
	private ArrayList<Integer> picIdList = new ArrayList<Integer>();
	private final int HALFSIDE = 600;
	DbAdapter dbAdapter = null;

	public CustomerOverlayAllpic(Drawable defaultMarker, Context context) {
		super(defaultMarker);
		boundCenterBottom(defaultMarker);
		this.context = context;
		dbAdapter = new DbAdapter(context);
		dbAdapter.open();
		readLatiandlongi();
		setOverlayItemList();
		this.populate();
	}

	// 读取所有的图片的lati和longi信息，放到两个list中
	public void readLatiandlongi() {
		Cursor cursor = null;
		try {
			cursor = dbAdapter.getAllPictures();
			int count = cursor.getCount();

			final int latIndex = cursor.getColumnIndex("latitude");
			final int longIndex = cursor.getColumnIndex("longitude");
			final int idIndex = cursor.getColumnIndex("_id");

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				String temp1 = cursor.getString(latIndex);
				String temp2 = cursor.getString(longIndex);
				String temp3 = cursor.getString(idIndex);
				if (temp1 != null && temp2 != null&&(!lati.contains(temp1)||!longi.contains(temp2))) {
					temp1 = Pattern.compile("\\.").matcher(temp1)
							.replaceAll("");
					temp2 = Pattern.compile("\\.").matcher(temp2)
							.replaceAll("");
					int latitemp = Integer.parseInt(temp1);
					int longitemp = Integer.parseInt(temp2);
					int _id = Integer.parseInt(temp3);
					lati.add(latitemp);
					longi.add(longitemp);
					picIdList.add(_id);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// Log.d(Tag, e.getMessage());
		} finally {
			cursor.close();
		}
	}

	public void setOverlayItemList() {
		for (int i = 0; i < lati.size(); i++) {
			GeoPoint point = new GeoPoint(lati.get(i), longi.get(i));
			OverlayItem overlayItem = new OverlayItem(point, "Hello",
					"I'm in tao, Greece!");
			overlayItemList.add(overlayItem);
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

//	protected boolean onTap(int index) {
//		OverlayItem item = overlayItemList.get(index);
//		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//		dialog.setTitle(item.getTitle());
//		dialog.setMessage("lati in All index："
//				+ item.getPoint().getLatitudeE6() + "\n" + "longi: "
//				+ item.getPoint().getLongitudeE6() + "\n");
//		dialog.show();
//		return true;
//	}

	public boolean onTap(GeoPoint p, MapView mapView) {
		ArrayList<Integer> picList = new ArrayList<Integer>();
		picList = readSitePicInfo(p.getLongitudeE6(), p.getLatitudeE6(),
				HALFSIDE);
		if (!picList.isEmpty()) {

			Intent intent = new Intent(context, ActivityMypic.class);
			intent.putExtra("picIdList", picList);
			intent.putExtra("type", 1);
			context.startActivity(intent);
		}

		// new AlertDialog.Builder(context).setMessage(
		// "纬度：" + p.getLatitudeE6() + "\n" +
		// "经度：" + p.getLongitudeE6()).show();
		return false;
	}

	private ArrayList<Integer> readSitePicInfo(int siteLongitude,
			int siteLatitude, int halfSide) {

		ArrayList<Integer> picList = new ArrayList<Integer>();
		int i, latitude = 0, longitude = 0;
		for (i = 0; i < lati.size(); i++) {
			latitude = lati.get(i);
			longitude = longi.get(i);
			if (siteLongitude - halfSide <= longitude
					&& longitude <= siteLongitude + halfSide
					&& siteLatitude - halfSide <= latitude
					&& latitude <= siteLatitude + halfSide) {
				picList.add(picIdList.get(i));
			}
		}
		return picList;
	}
}
