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

public class ActivityCreateOriginalDb extends Activity {
	/** Called when the activity is first created. */
	final String Tag = "SQLite";
	String amount = null;
	DbAdapter dbAdapter = null;
	ArrayList<String> picNameList = null;
	String[][] longiLati = { { "116.345578", "39.979360" },
			{ "116.343133", "39.979311" }, { "116.339722", "39.979081" },
			{ "116.342511", "39.982254" }, { "116.339874", "39.982665" },
			{ "116.339249", "39.984539" }, { "116.338436", "39.981037" },
			{ "116.337732", "39.976795" }, { "116.343091", "39.980347" },
			{ "116.347150", "39.979969" } };

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
				int index = Integer.parseInt(picName.substring(0, 1));
				dbAdapter.insertPicture("/mnt/sdcard/beihang/" + picName, 1,
						longiLati[index][0], longiLati[index][1], null);
			}
			// initPicLocation();
		} catch (Exception e) {
			Log.i("sql", e.getMessage());
		}

		Log.d(Tag, "before buiding:" + amount);
		amount = String.valueOf(this.databaseList().length);
		Log.d(Tag, "afer creating" + amount);

	}

	public ArrayList<String> getPicList() {
		
		File dir = new File("/mnt/sdcard/beihang/");
		String[] filelist = dir.list();
		ArrayList<String> piclist = new ArrayList<String>();
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].endsWith(".jpg"))
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