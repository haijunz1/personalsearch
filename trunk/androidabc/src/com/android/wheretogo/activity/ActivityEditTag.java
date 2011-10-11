package com.android.wheretogo.activity;

import java.util.ArrayList;
import com.android.wheretogo.adapter.DbAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityEditTag extends Activity {

	ArrayList<String> tagList;
	OnClickListener listenerButtonAdd = null;
	Button buttonAdd = null;
	DbAdapter dbAdapter = new DbAdapter(this);
	int picId;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittag);
		this.setTitle("TagList");
		picId = this.getIntent().getIntExtra("picId", 0);
		dbAdapter.open();
		ListView listView = (ListView) findViewById(R.id.listView);

		tagList = dbAdapter.getPicTagsByPicId(picId);
		if (tagList == null) {
			tagList = new ArrayList<String>();
		}

		final DeletableAdapter adapter = new DeletableAdapter(this, tagList);
		listView.setAdapter((ListAdapter) adapter);
		buttonAdd = (Button) findViewById(R.id.ButtonAdd);
		final EditText editText = (EditText) findViewById(R.id.editTextTag);
		editText.setSingleLine();
		listenerButtonAdd = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tag = editText.getText().toString();
				if (tag.length() > 0) {
					tagList.add(tag);
					adapter.notifyDataSetChanged();
					insertTag(tag);
				} else {
					Toast.makeText(ActivityEditTag.this, "输入不能为空，请重新输入",
							Toast.LENGTH_SHORT);
				}

				editText.setText("");
			}
		};
		buttonAdd.setOnClickListener(listenerButtonAdd);
	}

	public class DeletableAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<String> tagList;

		public DeletableAdapter(Context context, ArrayList<String> taglist) {
			this.context = context;
			this.tagList = taglist;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tagList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tagList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int index = position;
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.listitem, null);
			}
			final TextView textView = (TextView) view
					.findViewById(R.id.ItemText);
			textView.setText(tagList.get(position));
			final ImageView imageView = (ImageView) view
					.findViewById(R.id.ItemImage);
			imageView.setBackgroundResource(android.R.drawable.ic_delete);
			imageView.setTag(position);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String tag = tagList.get(index);
					int tagId = dbAdapter.getTagIdByTag(tag);
					tagList.remove(index);
					if (dbAdapter.isTagExist(tag)) {
						if (dbAdapter.isPic_TagExist(picId, tagId))
							dbAdapter.deletePicTag(tagId);
						if (!dbAdapter.isTagRelatExist(tagId)) {
							dbAdapter.deleteTag(tagId);
						}
						notifyDataSetChanged();
					}
				}
			});
			return view;
		}
	}

	private void insertTag(String tag) {
		// TODO Auto-generated method stub
		int tagId = 0;
		if (dbAdapter.isTagExist(tag)) {
			tagId = dbAdapter.getTagIdByTag(tag);
			if (!dbAdapter.isPic_TagExist(picId, tagId)) {
				dbAdapter.insertPicTagById(picId, tagId);
			}
		} else {
			dbAdapter.insertTag(tag);
			tagId=dbAdapter.insertTag(tag);
			dbAdapter.insertPicTagById(picId, tagId);
		}
	}

	@Override
	public void onDestroy() {
		dbAdapter.close();
		super.onDestroy();
	}
}
