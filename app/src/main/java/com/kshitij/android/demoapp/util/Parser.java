package com.kshitij.android.demoapp.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kshitij.android.demoapp.model.Messages;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

/**
 * Helper that handles the parsing of data.
 */

public class Parser {
	private static final String TAG = Parser.class.getSimpleName();
	private static Parser mParser;

	public synchronized static Parser getInstance() {
		if (mParser == null) {
			mParser = new Parser();
		}
		return mParser;
	}

	/**
	 * Populates JSON data into Messages.
	 */
	public Messages parseJsonData(String data) {
		Log.d(TAG, "parseJsonData(), data = " + data);
		Messages messages = null;
		if (!Utility.isNullOrEmpty(data)) {
			Gson gson = new Gson();
			try {
				messages = gson.fromJson(data, Messages.class);
			} catch (JsonSyntaxException e) {
				Log.e(TAG, "parseJsonData(), Exception: " + e.toString());
			}
		}
		return messages;
	}
}
