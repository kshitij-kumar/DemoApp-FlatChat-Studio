package com.kshitij.android.demoapp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */
public class Utility {
	private static final String TAG = Utility.class.getSimpleName();

	/**
	 * Converts InputStream into String representation.
	 * 
	 * @param inputStream
	 *            The InputStream to be converted.
	 * @return String representation of input or empty string if input is null.
	 */

	public static String convertToString(InputStream inputStream)
			throws IOException {
		Log.d(TAG, "convertToString()");
		if (inputStream == null) {
			return "";
		}
		ByteArrayOutputStream content = new ByteArrayOutputStream();

		int readBytes = 0;
		byte[] sBuffer = new byte[512];
		while ((readBytes = inputStream.read(sBuffer)) != -1) {
			content.write(sBuffer, 0, readBytes);
		}

		return new String(content.toByteArray());
	}

	/**
	 * Checks if a string is null or empty.
	 * 
	 * @param string
	 *            The input string.
	 * @return True if the string is null or empty, false otherwise.
	 */

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.length() == 0;
	}
}
