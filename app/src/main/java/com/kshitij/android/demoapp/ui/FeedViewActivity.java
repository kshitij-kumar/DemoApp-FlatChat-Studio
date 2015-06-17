package com.kshitij.android.demoapp.ui;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.kshitij.android.demoapp.R;
import com.kshitij.android.demoapp.adapter.ListAdapter;
import com.kshitij.android.demoapp.db.DatabaseHelper;
import com.kshitij.android.demoapp.db.MessageProvider;
import com.kshitij.android.demoapp.model.Messages;
import com.kshitij.android.demoapp.net.Network;
import com.kshitij.android.demoapp.util.Parser;

/**
 * Created by kshitij.kumar on 17-06-2015.
 */

public class FeedViewActivity extends AppCompatActivity implements
		LoaderCallbacks<Cursor> {
	public static final String TAG = FeedViewActivity.class.getSimpleName();
	private static final String URL = "http://pastebin.com/raw.php?i=aqziuquq";
	private static final int MAX_RETRY_COUNT = 2;
	private ListView mList;
	private ListAdapter mAdapter;
	private ProgressDialog mProgressDialog;
	private DataDownloaderTask mDataDownloaderTask;
	private int mRetryCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_view);
		mList = (ListView) findViewById(R.id.list);
		mAdapter = new ListAdapter(getApplicationContext(), null, false);
		mList.setAdapter(mAdapter);
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG, "onBackPressed()");
		// Cancel background task
		if (mDataDownloaderTask != null) {
			mDataDownloaderTask.cancel(true);
		}
		super.onBackPressed();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(TAG, "onSaveInstanceState()");
		// Activity is destroying, cancel background task
		if (mDataDownloaderTask != null) {
			mDataDownloaderTask.cancel(true);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG, "onCreateLoader()");
		CursorLoader loader = new CursorLoader(this,
				MessageProvider.CONTENT_URI, new String[] {}, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		Log.d(TAG, "onLoadFinished()");
		if (cursor == null || cursor.getCount() <= 0) { // No data in DB
			// Download data if network is available, retry if failed until
			// MAX_RETRY_COUNT. Abort when MAX_RETRY_COUNT reached.
			if (Network.isNetworkAvailable(this)
					&& mRetryCount < MAX_RETRY_COUNT) {
				mDataDownloaderTask = new DataDownloaderTask();
				mDataDownloaderTask.execute(URL);
				++mRetryCount;
			} else {
				showErrorMessage(getString(R.string.network_error_message));
			}
		} else {
			mAdapter.swapCursor(cursor);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);
	}

	private void dissmissLoadingProgress() {
		Log.d(TAG, "dissmissLoadingProgress()");
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	private void showLoadingProgress() {
		Log.d(TAG, "showLoadingProgress()");
		dissmissLoadingProgress();
		mProgressDialog = ProgressDialog.show(this, "", "Loading...", true,
				false);
	}

	private void showErrorMessage(String message) {
		Log.d(TAG, "showErrorMessage()");
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (mDataDownloaderTask != null) {
							mDataDownloaderTask.cancel(true);
						}
						finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * An AsyncTask to download and parse data off the UI thread
	 */
	private class DataDownloaderTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			Log.d(TAG, "DataDownloaderTask, onPreExecute()");
			showLoadingProgress();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... urls) {
			Log.d(TAG, "DataDownloaderTask, doInBackground()");
			String data;
			// Download data from server
			try {
				data = Network.getResponseAsString(urls[0]);
			} catch (IOException e) {
				Log.e(TAG, "DataDownloaderTask, doInBackground(), Exception: "
						+ e.toString());
				return getString(R.string.network_error_message);
			}

			Messages messages;
			// Parse data and save in DB
			if (data != null) {
				messages = Parser.getInstance().parseJsonData(data);
				DatabaseHelper.getInstance(getApplicationContext())
						.saveMessagesInDB(messages);
				return getString(R.string.success_message);
			} else {
				return getString(R.string.error_message);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d(TAG, "DataDownloaderTask, onPostExecute()");
			dissmissLoadingProgress();
			if (result.equalsIgnoreCase(getString(R.string.success_message))) {
				// Download successfull, restart loader
				getSupportLoaderManager().restartLoader(0, null,
						FeedViewActivity.this);
			}
		}
	}

}
