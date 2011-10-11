package com.android.wheretogo.activity;

import com.android.wheretogo.adapter.DbAdapter;
import android.app.Activity;
import android.os.Bundle;

public class ActivityClear extends Activity {
	DbAdapter dbAdapter = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		dbAdapter.clearAll();
	}
}