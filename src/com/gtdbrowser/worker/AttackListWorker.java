package com.gtdbrowser.worker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.NetworkConnectionResult;
import com.gtdbrowser.config.WSConfig;
import com.gtdbrowser.data.factory.AttackListJsonFactory;
import com.gtdbrowser.data.model.Attack;
import com.gtdbrowser.data.provider.GtdContent.AttackDao;

public class AttackListWorker {

	public static void start(final Context context, final int returnFormat) throws IllegalStateException, IOException,
			URISyntaxException, RestClientException, ParserConfigurationException, SAXException, JSONException {

		NetworkConnectionResult wsResult = NetworkConnection.retrieveResponseFromService(
				WSConfig.WS_ATTACK_LIST_URL_JSON, NetworkConnection.METHOD_GET);

		ArrayList<Attack> AttackList = null;
		AttackList = AttackListJsonFactory.parseResult(wsResult.wsResponse);

		// Clear the table
		context.getContentResolver().delete(AttackDao.CONTENT_URI, "1", null);
		// "1" means all rows, was null

		// Adds the Attacks in the database
		final int AttackListSize = AttackList.size();
		if (AttackList != null && AttackListSize > 0) {
			ContentValues[] valuesArray = new ContentValues[AttackListSize];
			for (int i = 0; i < AttackListSize; i++) {
				valuesArray[i] = AttackDao.getContentValues(AttackList.get(i));
			}
			context.getContentResolver().bulkInsert(AttackDao.CONTENT_URI, valuesArray);
		}
	}
}
