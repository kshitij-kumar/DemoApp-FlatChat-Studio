package com.kshitij.android.demoapp.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.kshitij.android.demoapp.DemoApp;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

/**
 * A ContentProvider implementation, used by ListAdapter
 * 
 */
public class MessageProvider extends ContentProvider {
	private static final String AUTHORITY = "com.kshitij.android.demoapp.db.MessageProvider";
	private static final String BASE_PATH = "messages";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		return null;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String order) {
		// Retrieve messages from DB
		DatabaseHelper helper = DatabaseHelper.getInstance(DemoApp
				.getAppInstance().getApplicationContext());
		String sqlSelectMessagesQuery = "Select * from "
				+ DatabaseHelper.TABLE_MESSAGES;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sqlSelectMessagesQuery, null);

		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

}
