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
import com.gtdbrowser.data.factory.RegionListJsonFactory;
import com.gtdbrowser.data.model.Region;
import com.gtdbrowser.data.provider.GtdContent.RegionDao;

public class RegionListWorker {

	public static void start(final Context context, final int returnFormat) throws IllegalStateException, IOException,
			URISyntaxException, RestClientException, ParserConfigurationException, SAXException, JSONException {

		NetworkConnectionResult wsResult = NetworkConnection.retrieveResponseFromService(
				WSConfig.WS_REGION_LIST_URL_JSON, NetworkConnection.METHOD_GET);

		ArrayList<Region> regionList = null;
		regionList = RegionListJsonFactory.parseResult(wsResult.wsResponse);

		// Clear the table
		context.getContentResolver().delete(RegionDao.CONTENT_URI, "1", null);
		// "1" means all rows, was null

		// Adds the regions in the database
		final int regionListSize = regionList.size();
		if (regionList != null && regionListSize > 0) {
			ContentValues[] valuesArray = new ContentValues[regionListSize];
			for (int i = 0; i < regionListSize; i++) {
				valuesArray[i] = RegionDao.getContentValues(regionList.get(i));
			}
			context.getContentResolver().bulkInsert(RegionDao.CONTENT_URI, valuesArray);
		}
	}
}
