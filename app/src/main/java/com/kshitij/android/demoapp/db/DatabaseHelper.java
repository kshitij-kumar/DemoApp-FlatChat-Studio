package com.kshitij.android.demoapp.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kshitij.android.demoapp.model.Chat;
import com.kshitij.android.demoapp.model.Messages;

/**
 * Created by kshitij.kumar on 16-06-2015.
 */

/**
 * An implementation of SQLiteOpenHelper
 * 
 */

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = DatabaseHelper.class.getSimpleName();
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "dempappdb";
	public static final String TABLE_MESSAGES = "MESSAGES";
	public static final String COL_ID = "_id";
	public static final String COL_MESSAGE_ID = "MESSAGE_ID";
	public static final String COL_MESSAGE_TYPE = "MESSAGE_TYPE";
	public static final String COL_MESSAGE_DATA = "MESSAGE_DATA";
	public static final String COL_MESSAGE_TIMESTAMP = "MESSAGE_TIMESTAMP";

	private static DatabaseHelper instance;

	public static synchronized DatabaseHelper getInstance(Context context) {

		if (instance == null) {
			instance = new DatabaseHelper(context);
		}

		return instance;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DatabaseHelper(Context context, String databaseName,
			CursorFactory object, int databaseVersion) {
		super(context, databaseName, object, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String queryCreateMessagesTable = "Create virtual table "
				+ TABLE_MESSAGES + " USING fts3(" + COL_ID
				+ " INTEGER PRIMARY KEY, " + COL_MESSAGE_ID + " TEXT, "
				+ COL_MESSAGE_TYPE + " TEXT, " + COL_MESSAGE_DATA + " TEXT, "
				+ COL_MESSAGE_TIMESTAMP + " TEXT)";

		db.execSQL(queryCreateMessagesTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String queryDropMessagesTable = "Drop table if exists "
				+ TABLE_MESSAGES;
		db.execSQL(queryDropMessagesTable);
		onCreate(db);
	}

	/**
	 * Inserts Messages in DB
	 * 
	 */
	public void saveMessagesInDB(Messages messages) {
		if (messages == null || messages.getChats() == null
				|| messages.getChats().size() == 0) {
			Log.d(TAG, "saveMessagesInDB(), Messages feed is null or empty.");
			return;
		} else {
			SQLiteDatabase db = getWritableDatabase();
			List<Chat> chats = messages.getChats();
			for (Chat chat : chats) {
				ContentValues values = getContentValues(chat);
				db.insert(TABLE_MESSAGES, null, values);
			}
		}
	}

	/**
	 * Prepares ContentValues to be stored in DB
	 * 
	 */
	private ContentValues getContentValues(Chat chat) {
		ContentValues values = new ContentValues();
		values.put(COL_MESSAGE_ID, chat.getMsg_id());
		values.put(COL_MESSAGE_TYPE, chat.getMsg_type());
		values.put(COL_MESSAGE_DATA, chat.getMsg_data());
		values.put(COL_MESSAGE_TIMESTAMP, chat.getTimestamp());

		return values;
	}

}