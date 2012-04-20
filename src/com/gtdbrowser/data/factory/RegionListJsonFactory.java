package com.gtdbrowser.data.factory;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gtdbrowser.data.model.Region;

public class RegionListJsonFactory {

	public static ArrayList<Region> parseResult(final String wsResponse) throws JSONException {
		final ArrayList<Region> regionList = new ArrayList<Region>();

		final JSONObject parser = new JSONObject(wsResponse);
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
