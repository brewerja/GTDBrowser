package com.gtdbrowser.worker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.foxykeep.datadroid.exception.RestClientException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.NetworkConnectionResult;
import com.gtdbrowser.config.WSConfig;
import com.gtdbrowser.data.model.FilteredListModel;
import com.gtdbrowser.data.provider.FilteredListDao;

public class FilteredListWorker {

	private static String next_uri;

	public static Bundle start(final Context context, final String uri) throws IllegalStateException, IOException,
			URISyntaxException, RestClientException, ParserConfigurationException, SAXException, JSONException {

		NetworkConnectionResult wsResult = NetworkConnection.retrieveResponseFromService(uri,
				NetworkConnection.METHOD_GET);

		ArrayList<FilteredListModel> filteredList = null;
		filteredList = parseResult(wsResult.wsResponse);

		// Adds the objects into the database
		final int filteredListSize = filteredList.size();
		if (filteredList != null && filteredListSize > 0) {
			ContentValues[] valuesArray = new ContentValues[filteredListSize];
			for (int i = 0; i < filteredListSize; i++) {
				valuesArray[i] = FilteredListDao.getContentValues(filteredList.get(i));
			}
			String tableName = FilteredListDao.getTableNameFromUri(uri);
			context.getContentResolver().bulkInsert(Uri.parse(FilteredListDao.CONTENT_URI + "/" + tableName),
					valuesArray);
		}

		Bundle returnBundle = new Bundle();
		returnBundle.putString("nextURI", next_uri);
		return returnBundle;
	}

	public static ArrayList<FilteredListModel> parseResult(final String wsResponse) throws JSONException {
		final ArrayList<FilteredListModel> filteredList = new ArrayList<FilteredListModel>();

		final JSONObject parser = new JSONObject(wsResponse);

		final JSONObject jsonMetaArray = parser.getJSONObject("meta");
		if (jsonMetaArray.get("next") != JSONObject.NULL)
			next_uri = WSConfig.SERVER_URI + jsonMetaArray.getString("next");
		else
			next_uri = null;

		final JSONArray jsonArray = parser.getJSONArray("objects");
		final int size = jsonArray.length();
		for (int i = 0; i < size; i++) {
			final JSONObject jsonObject = jsonArray.getJSONObject(i);
			final FilteredListModel object = new FilteredListModel();

			object.name = jsonObject.getString("name");
			object.id = jsonObject.getInt("id");
			object.num_attacks = jsonObject.getInt("num_attacks");
			//object.filter = jsonObject.getString("filter");

			filteredList.add(object);
		}

		return filteredList;
	}
}
