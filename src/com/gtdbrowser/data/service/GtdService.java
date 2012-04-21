/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.data.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.service.WorkerService;
import com.gtdbrowser.data.requestmanager.GtdRequestManager;
import com.gtdbrowser.worker.AttackListWorker;
import com.gtdbrowser.worker.FilteredListWorker;

/**
 * This class is called by the {@link GtdRequestManager} through the
 * {@link Intent} system. Get the parameters stored in the {@link Intent} and
 * call the right Worker.
 * 
 * @author Foxykeep
 */
public class GtdService extends WorkerService {

	private static final String LOG_TAG = GtdService.class.getSimpleName();

	// Max number of parallel threads used
	private static final int MAX_THREADS = 3;

	// Worker types
	public static final int WORKER_TYPE_FILTERED_LIST = 0;
	public static final int WORKER_TYPE_ATTACK_LIST = 1;

	public static String INTENT_EXTRA_URI = "com.gtdbrowser.extras.uri";

	public GtdService() {
		super(MAX_THREADS);
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		final int workerType = intent.getIntExtra(INTENT_EXTRA_WORKER_TYPE, -1);
		
		try {
			switch (workerType) {
			case WORKER_TYPE_FILTERED_LIST:
				String uri = intent.getStringExtra(INTENT_EXTRA_URI);
				Bundle resultBundle = FilteredListWorker.start(this, uri);
				sendSuccess(intent, resultBundle);
				break;
			case WORKER_TYPE_ATTACK_LIST:
				AttackListWorker.start(this, 1);
				sendSuccess(intent, null);
				break;
			}
		} catch (final IllegalStateException e) {
			Log.e(LOG_TAG, "IllegalStateException", e);
			sendConnexionFailure(intent, null);
		} catch (final IOException e) {
			Log.e(LOG_TAG, "IOException", e);
			sendConnexionFailure(intent, null);
		} catch (final URISyntaxException e) {
			Log.e(LOG_TAG, "URISyntaxException", e);
			sendConnexionFailure(intent, null);
		} catch (final RestClientException e) {
			Log.e(LOG_TAG, "RestClientException", e);
			sendConnexionFailure(intent, null);
		} catch (final ParserConfigurationException e) {
			Log.e(LOG_TAG, "ParserConfigurationException", e);
			sendDataFailure(intent, null);
		} catch (final SAXException e) {
			Log.e(LOG_TAG, "SAXException", e);
			sendDataFailure(intent, null);
		} catch (final JSONException e) {
			Log.e(LOG_TAG, "JSONException", e);
			sendDataFailure(intent, null);
		}
		// This block (which should be the last one in your implementation)
		// will catch all the RuntimeException and send you back an error
		// that you can manage. If you remove this catch, the
		// RuntimeException will still crash the PoCService but you will not be
		// informed (as it is in 'background') so you should never remove this
		// catch
		catch (final RuntimeException e) {
			Log.e(LOG_TAG, "RuntimeException", e);
			sendDataFailure(intent, null);
		}
	}
}
