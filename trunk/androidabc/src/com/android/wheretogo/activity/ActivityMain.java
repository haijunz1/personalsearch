package com.android.wheretogo.activity;

import java.io.File;

import com.android.wheretogo.constant.Constant;
import com.android.wheretogo.service.GpsService;
import com.android.wheretogo.threads.DownloadThread;
import com.android.wheretogo.threads.UploadThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ActivityMain extends Activity {
	/** Called when the activity is first created. */
	SharedPreferences gps = null;
	OnClickListener listenerGo = null;
	OnClickListener listenerClear = null;
	OnClickListener listenerMypic = null;
	OnClickListener listenerUpdate = null;
	OnClickListener listenerTakephoto=null;

	Button buttonClear;
	Button buttonMypic;
	Button buttonUpdate;
	Button buttonGo;
	Button buttonTakephoto;

	File workPath=new File(Environment.getExternalStorageDirectory(),"WhereToGo");
	File downloadPath= new File(workPath,"download");
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Welcome Harvey!");
		setContentView(R.layout.main);
		startService(new Intent(this, GpsService.class));
		makeDir();
		
		//.........added by lirunsheng
		String uri = Constant.uri;
//		String uri = "http://203.91.121.3:8085/ServerW2G";
		String longitude = "116343136";
		String latitude = "39979311";
		
		//get longi and lati from sharedpref
		gps = getSharedPreferences("gps", 0);
		String longi = gps.getString("longi", null);
		String lati = gps.getString("lati", null);
		if(longi != null && lati != null){
			longitude = longi.replace(".","");
			latitude = lati.replace(".", "");
		}
		String picDirectory = "/mnt/sdcard/WhereTogo/download/";
		Context context = ActivityMain.this;
		//download pictures
		DownloadThread downloadThread = new DownloadThread(uri,
				longitude, latitude, picDirectory, context);
		downloadThread.start();
		//..........
		
		//upload pictures
//		UploadThread uploadThread = new UploadThread(uri, 10001, picDirectory, context);
//		uploadThread.start();
		//
		listenerGo = new OnClickListener() {
			public void onClick(View v) {
				Intent intent0 = new Intent(ActivityMain.this, ActivityGo.class);
				setTitle("Go");
				startActivity(intent0);
			}
		};

		listenerClear = new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(ActivityMain.this,
						ActivityClear.class);
				startActivity(intent1);
			}
		};

		listenerMypic = new OnClickListener() {
			public void onClick(View v) {
				setTitle("Galaxy");
				Intent intent2 = new Intent(ActivityMain.this,
						ActivityMypic.class);
				startActivity(intent2);

			}
		};

		listenerUpdate = new OnClickListener() {
			public void onClick(View v) {
				setTitle("Update");
				Intent intent3 = new Intent(ActivityMain.this,
						ActivityCreateOriginalDb.class);
				startActivity(intent3);

			}
		};

		listenerTakephoto = new OnClickListener() {
			public void onClick(View v) {
				setTitle("Take Photo");
				Intent intent3 = new Intent(ActivityMain.this,
						ActivityTakepic.class);
				startActivity(intent3);

			}
		};
		buttonGo = (Button) findViewById(R.id.buttonGo);
		buttonGo.setOnClickListener(listenerGo);
		
		buttonClear = (Button) findViewById(R.id.buttonClear);
		buttonClear.setText("Clear Databases");
		buttonClear.setOnClickListener(listenerClear);
		
		buttonMypic = (Button) findViewById(R.id.buttonMypic);
		buttonMypic.setOnClickListener(listenerMypic);
		
		buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
		buttonUpdate.setText("Update Database");
		buttonUpdate.setOnClickListener(listenerUpdate);
		
		buttonTakephoto=(Button)findViewById(R.id.buttonTakephoto);
		buttonTakephoto.setText("Take Photo");
		buttonTakephoto.setOnClickListener(listenerTakephoto);
		
		
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	        
	        //按下键盘上返回按钮

	        if(keyCode == KeyEvent.KEYCODE_BACK){
	 
	            new AlertDialog.Builder(this)
	                .setTitle("退出").setMessage("是否退出系统？").setIcon(R.drawable.warning)
	                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    }
	                })
	                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	                        finish();
	                    }
	                }).show();
	            
	            return true;
	        }else{        
	            return super.onKeyDown(keyCode, event);
	        }
	    }
	 
	 
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        stopService(new Intent(this, GpsService.class));
	        System.exit(0);
	        //或者下面这种方式

	        //android.os.Process.killProcess(android.os.Process.myPid()); 

	    }
        public void makeDir(){
        	if(!workPath.exists()){
        		workPath.mkdirs();
        	}
        	if(!downloadPath.exists()){
        		downloadPath.mkdirs();
        	}
        }	    
	    
	 
    
}
