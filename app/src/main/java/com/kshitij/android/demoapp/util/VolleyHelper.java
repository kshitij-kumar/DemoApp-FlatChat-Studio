package com.kshitij.android.demoapp.util;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

/**
 * Helper class around Volley library.
 */

public class VolleyHelper {

	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	public static void init(Context context) {
		initRequestQueue(context);
		initImageLoader(context);
	}

	private static void initRequestQueue(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	private static void initImageLoader(Context context) {
		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		mImageLoader = new ImageLoader(mRequestQueue,
				new BitmapCache(cacheSize));
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException(
					"getRequestQueue(), RequestQueue not initialized");
		}
	}

	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException(
					"getImageLoader(), ImageLoader not initialized");
		}
	}

}
