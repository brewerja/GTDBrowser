/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.data.requestmanager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.SparseArray;

import com.foxykeep.datadroid.requestmanager.RequestManager;
import com.gtdbrowser.data.service.GtdService;

/**
 * This class is used as a proxy to call the Service. It provides easy-to-use
 * methods to call the service and manages the Intent creation. It also assures
 * that a request will not be sent again if an exactly identical one is already
 * in progress
 * 
 * @author Foxykeep
 */
public class GtdRequestManager extends RequestManager {

	private static final int MAX_RANDOM_REQUEST_ID = 1000000;

	// Singleton management
	private static GtdRequestManager sInstance;

	public static GtdRequestManager from(final Context context) {
		if (sInstance == null)
			sInstance = new GtdRequestManager(context);
		return sInstance;
	}

	private static Random sRandom = new Random();

	private SparseArray<Intent> mRequestSparseArray;
	private Context mContext;
	private ArrayList<WeakReference<OnRequestFinishedListener>> mListenerList;
	private Handler mHandler = new Handler();
	private EvalReceiver mEvalReceiver = new EvalReceiver(mHandler);

	private GtdRequestManager(final Context context) {
		mContext = context.getApplicationContext();
		mRequestSparseArray = new SparseArray<Intent>();
		mListenerList = new ArrayList<WeakReference<OnRequestFinishedListener>>();
	}

	/**
	 * The ResultReceiver that will receive the result from the Service
	 */
	private class EvalReceiver extends ResultReceiver {
		EvalReceiver(final Handler h) {
			super(h);
		}

		@Override
		public void onReceiveResult(final int resultCode, final Bundle resultData) {
			handleResult(resultCode, resultData);
		}
	}

	/**
	 * Clients may implements this interface to be notified when a request is
	 * finished
	 */
	public static interface OnRequestFinishedListener extends EventListener {

		/**
		 * Event fired when a request is finished.
		 * 
		 * @param requestId
		 *            The request Id (to see if this is the right request)
		 * @param resultCode
		 *            The result code (0 if there was no error)
		 * @param payload
		 *            The result of the service execution.
		 */
		public void onRequestFinished(int requestId, int resultCode, Bundle payload);
	}

	/**
	 * Add a {@link OnRequestFinishedListener} to this. Clients may use it in
	 * order to listen to events fired when a request is finished.
	 * <p>
	 * <b>Warning !! </b> If it's an {@link Activity} that is used as a
	 * Listener, it must be detached when {@link Activity#onPause} is called in
	 * an {@link Activity}.
	 * </p>
	 * 
	 * @param listener
	 *            The listener to add to this.
	 */
	public void addOnRequestFinishedListener(final OnRequestFinishedListener listener) {
		WeakReference<OnRequestFinishedListener> weakRef = new WeakReference<OnRequestFinishedListener>(listener);
		synchronized (mListenerList) {
			if (!mListenerList.contains(weakRef)) {
				mListenerList.add(weakRef);
			}
		}
	}

	/**
	 * Remove a {@link OnRequestFinishedListener} to this
	 * {@link SkeletonRequestManager}.
	 * 
	 * @param listenerThe
	 *            listener to remove to this {@link SkeletonRequestManager}.
	 */
	public void removeOnRequestFinishedListener(final OnRequestFinishedListener listener) {
		synchronized (mListenerList) {
			mListenerList.remove(new WeakReference<OnRequestFinishedListener>(listener));
		}
	}

	/**
	 * Return whether a request (specified by its id) is still in progress or
	 * not
	 * 
	 * @param requestId
	 *            The request id
	 * @return whether the request is still in progress or not.
	 */
	public boolean isRequestInProgress(final int requestId) {
		return (mRequestSparseArray.indexOfKey(requestId) >= 0);
	}

	/**
	 * This method is call whenever a request is finished. Call all the
	 * available listeners to let them know about the finished request
	 * 
	 * @param resultCode
	 *            The result code of the request
	 * @param resultData
	 *            The bundle sent back by the service
	 */
	protected void handleResult(final int resultCode, final Bundle resultData) {

		// Get the request Id
		final int requestId = resultData.getInt(RECEIVER_EXTRA_REQUEST_ID);

		// Remove the request Id from the "in progress" request list
		mRequestSparseArray.remove(requestId);

		// Call the available listeners
		synchronized (mListenerList) {
			for (WeakReference<OnRequestFinishedListener> weakRef : mListenerList) {
				OnRequestFinishedListener listener = weakRef.get();
				if (listener != null) {
					listener.onRequestFinished(requestId, resultCode, resultData);
				}
			}
		}
	}

	/**
	 * Gets the list of regions and save it in the database
	 * 
	 * @return the request Id
	 */
	public int getRegionList() {

		// Check if a match to this request is already launched
		final int requestSparseArrayLength = mRequestSparseArray.size();
		for (int i = 0; i < requestSparseArrayLength; i++) {
			final Intent savedIntent = mRequestSparseArray.valueAt(i);

			if (savedIntent.getIntExtra(GtdService.INTENT_EXTRA_WORKER_TYPE, -1) != GtdService.WORKER_TYPE_REGION_LIST) {
				continue;
			}
			return mRequestSparseArray.keyAt(i);
		}

		final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);

		final Intent intent = new Intent(mContext, GtdService.class);
		intent.putExtra(GtdService.INTENT_EXTRA_WORKER_TYPE, GtdService.WORKER_TYPE_REGION_LIST);
		intent.putExtra(GtdService.INTENT_EXTRA_RECEIVER, mEvalReceiver);
		intent.putExtra(GtdService.INTENT_EXTRA_REQUEST_ID, requestId);
		mContext.startService(intent);

		mRequestSparseArray.append(requestId, intent);

		return requestId;
	}

	/**
	 * Gets the list of regions and save it in the database
	 * 
	 * @return the request Id
	 */
	public int getAttackList() {

		// Check if a match to this request is already launched
		final int requestSparseArrayLength = mRequestSparseArray.size();
		for (int i = 0; i < requestSparseArrayLength; i++) {
			final Intent savedIntent = mRequestSparseArray.valueAt(i);

			if (savedIntent.getIntExtra(GtdService.INTENT_EXTRA_WORKER_TYPE, -1) != GtdService.WORKER_TYPE_ATTACK_LIST) {
				continue;
			}
			return mRequestSparseArray.keyAt(i);
		}

		final int requestId = sRandom.nextInt(MAX_RANDOM_REQUEST_ID);

		final Intent intent = new Intent(mContext, GtdService.class);
		intent.putExtra(GtdService.INTENT_EXTRA_WORKER_TYPE, GtdService.WORKER_TYPE_ATTACK_LIST);
		intent.putExtra(GtdService.INTENT_EXTRA_RECEIVER, mEvalReceiver);
		intent.putExtra(GtdService.INTENT_EXTRA_REQUEST_ID, requestId);
		mContext.startService(intent);

		mRequestSparseArray.append(requestId, intent);

		return requestId;
	}
}
