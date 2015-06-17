package com.kshitij.android.demoapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kshitij.android.demoapp.R;
import com.kshitij.android.demoapp.db.DatabaseHelper;
import com.kshitij.android.demoapp.util.VolleyHelper;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

public class ListAdapter extends CursorAdapter {

	private LayoutInflater mInflater;

	public ListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		// CursorAdapter? Still use View-Holder pattern to optimize
		// findViewById() calls
		if (viewHolder == null) {
			viewHolder = new ViewHolder();
			viewHolder.mText = (TextView) convertView.findViewById(R.id.text);
			viewHolder.mImage = (NetworkImageView) convertView
					.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		}

		String messageType = cursor.getString(cursor
				.getColumnIndex(DatabaseHelper.COL_MESSAGE_TYPE));

		// Fill list item with data
		if (messageType.equalsIgnoreCase("0")) {
			viewHolder.mText.setVisibility(View.VISIBLE);
			viewHolder.mImage.setVisibility(View.GONE);
			viewHolder.mText.setText(cursor.getString(cursor
					.getColumnIndex(DatabaseHelper.COL_MESSAGE_DATA)));
		} else {
			viewHolder.mText.setVisibility(View.GONE);
			viewHolder.mImage.setVisibility(View.VISIBLE);
			viewHolder.mImage.setImageUrl(cursor.getString(cursor
					.getColumnIndex(DatabaseHelper.COL_MESSAGE_DATA)),
					VolleyHelper.getImageLoader());
		}

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		View rowView = mInflater.inflate(R.layout.list_item, parent, false);

		return rowView;
	}

	static class ViewHolder {
		TextView mText;
		NetworkImageView mImage;
	}
}