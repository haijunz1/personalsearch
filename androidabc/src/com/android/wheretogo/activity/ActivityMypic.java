package com.android.wheretogo.activity;

import java.io.File;
import java.util.ArrayList;
import com.android.wheretogo.adapter.DbAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.GridView;

import android.widget.ImageView;
import android.util.Log;

public class ActivityMypic extends Activity {
	DbAdapter dbAdapter = new DbAdapter(this);
	ArrayList<Integer> picIdList = null;
	Cursor cursor = null;
	String Tag = "ActivityMypic";
	int requestCode=1;
	GridView gridview=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);
		setTitle("GridView");
		gridview = (GridView) findViewById(R.id.grid_view);
		dbAdapter.open();
		if (getIntent().getIntExtra("type", 0)==0)
			readPicInfor();
		if(getIntent().getIntExtra("type",0)==1){
			if(getIntent().getIntegerArrayListExtra("picIdList")!=null)
				picIdList=getIntent().getIntegerArrayListExtra("picIdList");
		}
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(ActivityMypic.this,
						ActivityImageShow.class);
				intent1.putExtra("picOrder", position);
				intent1.putExtra("picIdList", picIdList);
				startActivityForResult(intent1, requestCode);
			}
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == 1) {
				// 拍照Activity保存图像数据的key是data，返回的数据类型是Bitmap对象
                  int picId=data.getIntExtra("picId", 0);
                  picIdList.remove(new Integer(picId));
                  gridview.setAdapter(new ImageAdapter(this));
			// 在ImageView组件中显示拍摄的照片
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void readPicInfor() {
		picIdList = new ArrayList<Integer>();
		try {
			
			cursor = dbAdapter.getAllPictures();
			final int picIdIndex = cursor.getColumnIndex("_id");
			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				int picId = cursor.getInt(picIdIndex);
				picIdList.add(picId);

			}
			cursor.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.d(Tag, e.getMessage());
		}finally{
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imageView = null;

			String name = dbAdapter.getPictureNameByPicId(picIdList
					.get(position));
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; // just get the bounds without
			// allocating memory.
			Bitmap bm = BitmapFactory.decodeFile(name, opts);
			int scalesize = 1;
			int be = opts.outHeight / 80;
			if (be > 0) {
				scalesize = be;
			}
			opts.inSampleSize = scalesize;
			opts.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(name, opts);
			Log.v("size", "the scalesize is :" + scalesize);

			// int usedMegs = (int) (Debug.getNativeHeapAllocatedSize() /
			// 1048576L);
			// String usedMegsString = String.format(" - Memory Used: %d MB",
			// usedMegs);
			// Log.v("Memory", "the position is :" + position +
			// " the memory is :"
			// + usedMegsString);
			try {
				if (convertView == null) {

					imageView = new ImageView(mContext);
					imageView.setImageBitmap(bm);
					imageView.setLayoutParams(new GridView.LayoutParams(80, 80));
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					imageView.setPadding(5,5,5,5);
					Log.v(Tag, "the new position is :" + position
							+ "the view is " + imageView);
				}
				// if it's not recycled, initialize some attributes
				else {
					imageView = (ImageView) convertView;
					imageView.setImageBitmap(bm);
					Log.v(Tag, "the recycle position is :" + position
							+ "the view is" + convertView);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v(Tag, e.getMessage());
			}
			return imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return picIdList.size();
		}

	}

	public void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}

}