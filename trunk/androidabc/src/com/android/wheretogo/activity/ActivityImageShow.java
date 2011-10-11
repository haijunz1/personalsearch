package com.android.wheretogo.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.android.wheretogo.adapter.DbAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;
import android.content.Intent;
import android.database.Cursor;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ActivityImageShow extends Activity implements OnGestureListener,
		ViewFactory {

	DbAdapter dbAdapter = null;
	EditText editText = null;
	private ImageSwitcher is;
	private GestureDetector gestureScanner;
	int picId, picOrder;

	ArrayList<Integer> picIdList = new ArrayList<Integer>();
	String[] urList = { "Harvey", "Scott Lee", "jun Hu", "zhenyu Wu",
			"TengWang", "john Tao", "li Hao" };

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle("Pictures");
		setResult(0);
		try {
			setContentView(R.layout.imageshow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		picOrder = this.getIntent().getIntExtra("picOrder", 0);
		picIdList = getIntent().getExtras().getIntegerArrayList("picIdList");
		picId = picIdList.get(picOrder);
		gestureScanner = new GestureDetector(this);
		gestureScanner
				.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
					public boolean onDoubleTap(MotionEvent e) {
						// 双击时产生一次
						Log.v("test", "onDoubleTap");
						return false;
					}

					public boolean onDoubleTapEvent(MotionEvent e) {
						// 双击时产生两次
						Log.v("test", "onDoubleTapEvent");
						return false;
					}

					public boolean onSingleTapConfirmed(MotionEvent e) {
						// 短快的点击算一次单击
						Log.v("test", "onSingleTapConfirmed");
						return false;
					}
				});
		is = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		is.setFactory(this);
		Animation inAnm = AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in);
		Animation outAnm = AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out);
		inAnm.setInterpolator(this, android.R.anim.accelerate_interpolator);
		outAnm.setInterpolator(this, android.R.anim.decelerate_interpolator);
		is.setInAnimation(inAnm);
		is.setOutAnimation(outAnm);

		is.setImageDrawable(this.getDrawable());

	}

	public boolean onTouchEvent(MotionEvent me) {
		return gestureScanner.onTouchEvent(me);
	}

	// public void setImageView() {
	// String name = dbAdapter.getPictureNameByPicId(picId);
	// image = (ImageView) findViewById(R.id.imageView2);
	// Bitmap bm = BitmapFactory.decodeFile(name);
	// image.setImageBitmap(bm);
	// }

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
		case R.id.menu_delete: {
			actionClickMenuItem3();
			return true;
		}

		}
		return false;
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
	private void actionClickMenuItem3() {
		// TODO Auto-generated method stub
	    new AlertDialog.Builder(this)
        .setTitle("删除").setMessage("是否删除改图片？").setIcon(R.drawable.warning)
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        })
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
              dbAdapter.deletePicture(picId);
              dbAdapter.deletePicTag(picId);
              Intent data= new Intent();
              data.putExtra("picId", picId);
              ActivityImageShow.this.setResult(1, data);
              ActivityImageShow.this.finish();
            }
        }).show();
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
			final int ownerIndex = mCursor.getColumnIndex("ownerId");
			final int timeIndex = mCursor.getColumnIndex("created");
			final int latiIndex = mCursor.getColumnIndex("latitude");
			final int longiIndex = mCursor.getColumnIndex("longitude");
			// set tag
			tags = dbAdapter.getPicTagsByPicId(picId);
			if (tags != null) {
				for (String tag : tags) {
					textTag.append(tag + "  ");
				}
			}
			// set longitude and latitude
			String lati = mCursor.getString(latiIndex);
			String longi = mCursor.getString(longiIndex);
			if (lati != null && longi != null) {
				textLati.append(lati);
				textLongi.append(longi);
			}
			// set owner
//			textPher.append(urList[r.nextInt(urList.length)]);
			int ownerId = mCursor.getInt(ownerIndex);
			String owner = dbAdapter.getOwnerName(ownerId);
			textPher.append(owner);
			// set time
			String curTime = null;
			curTime = mCursor.getString(timeIndex);
			if(curTime!=null)
				textTime.append(curTime);
		}
		mCursor.close();
	}

	public void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		// 参数解释：
		// e1：第1个ACTION_DOWN MotionEvent
		// e2：最后一个ACTION_MOVE MotionEvent
		// velocityX：X轴上的移动速度，像素/秒
		// velocityY：Y轴上的移动速度，像素/秒
		// 触发条件 ：
		// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
		if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 30) {
			int nextOrder = 0;
			if (picOrder == picIdList.size() - 1) {
				nextOrder = 0;
			} else {
				nextOrder = picOrder + 1;
			}
			picOrder = nextOrder;
			picId = this.picIdList.get(picOrder);
			is.setImageDrawable(this.getDrawable());
			// Fling left
		} else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 30) {
			int nextOrder = 0;
			if (picOrder == 0) {
				nextOrder = picIdList.size() - 1;
			} else {
				nextOrder = picOrder - 1;
			}
			picOrder = nextOrder;
			picId = this.picIdList.get(picOrder);
			is.setImageDrawable(this.getDrawable());
			// Fling right
		}
		return true;
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

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		// i.setBackgroundColor(0xFF000000);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}
}
