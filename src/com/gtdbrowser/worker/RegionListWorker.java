package com.gtdbrowser.worker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.NetworkConnectionResult;
import com.gtdbrowser.config.WSConfig;
import com.gtdbrowser.data.model.Region;
import com.gtdbrowser.data.provider.GtdContent.RegionDao;

public class RegionListWorker {

	private static boolean hasNext;
	
	public static Bundle start(final Context context, final int offset) throws IllegalStateException, IOException,
			URISyntaxException, RestClientException, ParserConfigurationException, SAXException, JSONException {

		NetworkConnectionResult wsResult = NetworkConnection.retrieveResponseFromService(
				WSConfig.WS_REGION_LIST_URL + "&offset=" + offset, NetworkConnection.METHOD_GET);

		ArrayList<Region> regionList = null;
		regionList = parseResult(wsResult.wsResponse);

		// Clear the table
		// context.getContentResolver().delete(RegionDao.CONTENT_URI, "1", null);
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
		
		Bundle returnBundle = new Bundle();
		returnBundle.putBoolean("hasNext", hasNext);
		return returnBundle;
	}
	
	public static ArrayList<Region> parseResult(final String wsResponse) throws JSONException {
		final ArrayList<Region> regionList = new ArrayList<Region>();

		final JSONObject parser = new JSONObject(wsResponse);
		
		final JSONObject jsonMetaArray = parser.getJSONObject("meta");
		if (jsonMetaArray.get("next") != JSONObject.NULL) {
			hasNext = true;
		} else {
			hasNext = false;
		}
		
		final JSONArray jsonRegionArray = parser.getJSONArray("objects");
		final int size = jsonRegionArray.length();
		for (int i = 0; i < size; i++) {
			final JSONObject jsonRegion = jsonRegionArray.getJSONObject(i);
			final Region region = new Region();

			region.name = jsonRegion.getString("name");
			region.id = jsonRegion.getInt("id");
			region.num_attacks = jsonRegion.getInt("num_attacks");
			region.filter = jsonRegion.getString("filter");

			regionList.add(region);
		}

		return regionList;
	}
}
