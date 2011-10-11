package com.android.wheretogo.activity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wheretogo.adapter.DbAdapter;
import com.android.wheretogo.constant.Constant;
import com.android.wheretogo.threads.UploadThread;

public class ActivityCameraImageShow extends Activity implements
		OnGestureListener {

	DbAdapter dbAdapter = null;
	EditText editText = null;
	private ImageView imageView = null;
	private int picId;
	String picPath = null;
	private GestureDetector gestureScanner;
	public Handler handler = null;
	ArrayList<Integer> picIdList = new ArrayList<Integer>();
	String[] urList = { "Harvey", "Scott Lee", "jun Hu", "zhenyu Wu",
			"TengWang", "john Tao", "li Hao" };

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle("Picture");
		setContentView(R.layout.cameraimageshow);

		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		Intent intent = this.getIntent();
		picId = intent.getIntExtra("picId", 0);
		imageView = (ImageView) findViewById(R.id.CameraImage);
		imageView.setImageDrawable(this.getDrawable());

		gestureScanner = new GestureDetector(this);
		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					public boolean onDoubleTap(MotionEvent e) {
						return false;
					}

					public boolean onDoubleTapEvent(MotionEvent e) {
						return false;
					}

					public boolean onSingleTapConfirmed(MotionEvent e) {
						return false;
					}
				});
		handler = new Handler(){
			public void handleMessage(Message msg){
				switch(msg.what){
				case 1:
					Toast toast = Toast.makeText(ActivityCameraImageShow.this, "上传图片成功", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		};
	}

	public Drawable getDrawable() {

		String name = dbAdapter.getPictureNameByPicId(picId);
		Display display = this.getWindowManager().getDefaultDisplay();
		// 获取屏幕的宽和高
		int dw = display.getWidth();
		int dh = display.getHeight();
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		Bitmap picBitmap = BitmapFactory.decodeFile(name, op);

		int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
																// 大于等于他的最小整数
		int hRatio = (int) Math.ceil(op.outHeight / (float) dh); // 计算高度比例

		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false; // 注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
		picBitmap = BitmapFactory.decodeFile(name, op);

		Drawable drawable = new BitmapDrawable(picBitmap);

		return drawable;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lookup, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_edit: {
			actionClickMenuItem1();
			return true;
		}
		case R.id.menu_detail: {
			actionClickMenuItem2();
			return true;
		}
		case R.id.menu_refresh:{
			actionClickMenuItem3();
			return true;
		}
		}
		
		return false;
	}

	private void actionClickMenuItem3() {
		// lirunsheng 520
//		String uri = "http://203.91.121.3:8085/ServerW2G";
		String uri = Constant.uri;
		String picDirectory = "/mnt/sdcard/WhereToGo/";
		Context context = ActivityCameraImageShow.this;
		UploadThread uploadThread = new UploadThread(uri, picId, picDirectory, context,handler);
		uploadThread.start();
//		Toast toast = Toast.makeText(context, "上传图片成功", Toast.LENGTH_LONG);
//		toast.show();
//		while(uploadThread.isAlive()){}
//		if(!uploadThread.isAlive()){
//			new AlertDialog.Builder(ActivityCameraImageShow.this)
//			.setTitle("提示")
//			.setMessage("上传成功")
//			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//				}
//			}).show();
//		}

	}

	public void actionClickMenuItem1() {
		Intent intent = new Intent(this, ActivityEditTag.class);
		intent.putExtra("picId", picId);
		startActivity(intent);

	}

	private void actionClickMenuItem2() {
		// TODO Auto-generated method stub
		showDetail();
	}

	public void showDetail() {
		View messageView = getLayoutInflater().inflate(R.layout.showdetail,
				null, false);
		setPicdetail(messageView);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.icon);
		builder.setTitle("图片信息");
		builder.setView(messageView);
		builder.create();
		builder.show();
	}

	private void setPicdetail(View messageView) {
		// TODO Auto-generated method stub
		TextView textLati = (TextView) messageView
				.findViewById(R.id.detaillati);
		TextView textLongi = (TextView) messageView
				.findViewById(R.id.detaillongi);
		TextView textPher = (TextView) messageView
				.findViewById(R.id.detailphotographer);
		TextView textTime = (TextView) messageView
				.findViewById(R.id.detailtime);
		TextView textTag = (TextView) messageView.findViewById(R.id.detailtag);
		
		ArrayList<String> tags = null;
		Random r = new Random(new java.util.Date().getTime());
		Cursor mCursor = dbAdapter.getPicture(picId);
		
		if (mCursor.moveToFirst() != false) {
			final int timeIndex = mCursor.getColumnIndex("created");
            final int latiIndex =mCursor.getColumnIndex("latitude");
            final int longiIndex =mCursor.getColumnIndex("longitude");
			// set tag
			tags = dbAdapter.getPicTagsByPicId(picId);
			if (tags != null) {
				for (String tag : tags) {
					textTag.append(tag + "  ");
				}
			}
			// set longitude and latitude
			String lati = mCursor.getString(latiIndex);
			String longi =  mCursor.getString(longiIndex);
			if (lati != null && longi != null) {
				textLati.append(lati);
				textLongi.append(longi);
			}
			// set owner
//			textPher.append(urList[r.nextInt(urList.length)]);
			textPher.append(Constant.userName);
			// set time
			String curTime = null;
			curTime = mCursor.getString(timeIndex);
			textTime.append(curTime);
		}
		mCursor.close();
	}

	public void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}

	public boolean onTouchEvent(MotionEvent me) {
		return gestureScanner.onTouchEvent(me);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		this.openOptionsMenu();
		return false;
	}

}
