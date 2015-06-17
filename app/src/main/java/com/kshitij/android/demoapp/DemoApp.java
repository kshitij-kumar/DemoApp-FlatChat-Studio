package com.kshitij.android.demoapp;

import android.app.Application;

import com.kshitij.android.demoapp.util.VolleyHelper;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

/**
 * Application class.
 * 
 */
public class DemoApp extends Application {

	private static DemoApp mInstance;

	public DemoApp() {

	}

	public static DemoApp getAppInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		mInstance = this;
		// Initialize Volley
		VolleyHelper.init(getApplicationContext());
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}
