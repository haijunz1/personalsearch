package com.android.wheretogo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.wheretogo.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewArrayAdapter extends ArrayAdapter<String> {
	private Context context;
	private ArrayList<String> arrayList;
	private boolean bl = false;

	public NewArrayAdapter(Context context,ArrayList<String> arrayList){
		super(context,R.layout.listitemlogin,arrayList);
		this.context = context;
		this.arrayList = arrayList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int index = position;
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listitemlogin, null);
		}
		final TextView textView = (TextView) view.findViewById(R.id.ItemText);
		textView.setText(arrayList.get(index));
		final ImageView imageView = (ImageView) view.findViewById(R.id.ItemImage);
		imageView.setBackgroundResource(android.R.drawable.ic_delete);
		imageView.setTag(position);
		imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				arrayList.remove(index);
				notifyDataSetChanged();
			}
		});
		return view;
	}

}
