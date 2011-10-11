package com.android.wheretogo.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import com.android.wheretogo.adapter.DbAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Log;

public class ActivityDbupdate extends Activity {
	/** Called when the activity is first created. */
	final String Tag = "SQLite";
	final String DB_name = "test.db";
	String amount = null;
	DbAdapter dbAdapter = null;
	ArrayList<String> picNameList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		amount = String.valueOf(this.databaseList().length);
		dbAdapter = new DbAdapter(this);
		try {
			dbAdapter.open();
			picNameList = getPicList();
			for (String picName : picNameList) {
				dbAdapter.insertPicture("/mnt/sdcard/image/" + picName, 1,
						null, null, null);
			}
			initPicLocation();
		} catch (Exception e) {
			Log.i("sql", e.getMessage());
		}

		Log.d(Tag, "before buiding:" + amount);
		amount = String.valueOf(this.databaseList().length);
		Log.d(Tag, "afer creating" + amount);

	}

	public ArrayList<String> getPicList() {
		File dir = new File("/mnt/sdcard/image/");
		String[] filelist = dir.list();
		ArrayList<String> piclist = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].endsWith(".jpg") || filelist[i].endsWith(".png")
					|| filelist[i].endsWith(".gif")
					|| filelist[i].endsWith("JPG"))
				piclist.add(filelist[i]);
		}
		return piclist;
	}

	public void initPicLocation() {

		Random r = new Random(new java.util.Date().getTime());
		Cursor cursor = dbAdapter.getAllPictures();
		final int picIdIndex = cursor.getColumnIndex("_id");
		int latitemp = 0, longitemp = 0, latitude = 0, longitude = 0;
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			int picId = cursor.getInt(picIdIndex);
			latitemp = r.nextInt(10000);
			longitemp = r.nextInt(14000);
			latitude = latitemp + 39975000;
			longitude = longitemp + 116334000;
			String sql = "update picture set latitude=" + latitude + ","
					+ "longitude=" + longitude + " where _id=" + picId;
			dbAdapter.sqlExecute(sql);
		}
	}

	public void onDestroy() {
		dbAdapter.close();
		amount = String.valueOf(databaseList().length);
		Log.d(Tag, "onDestroy..before:" + amount);
		super.onDestroy();
	}
}