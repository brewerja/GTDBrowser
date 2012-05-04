/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.gtdbrowser.R;
import com.gtdbrowser.config.WSConfig;
import com.gtdbrowser.data.provider.FilteredListDao;

public class HomeActivity extends Activity implements OnClickListener {

	private Button mButtonRegionList;
	private Button mButtonAttackList;
	private Button mButtonCountryList;
	private Button mButtonAttackTypeList;
	private View mButtonTargetTypeList;
	private View mButtonWeaponTypeList;
	private View mButtonYearList;
	private View mButtonDbSourceList;
	private String[] tables = { "region", "country", "attacktype", "targettype", "weapontype", "year", "dbsource" };

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);
		bindViews();
	}

	@Override
	protected void onResume() {
		super.onResume();

		String[] projection = { FilteredListDao.ID };

		for (String table : tables) {
			Cursor c = getContentResolver().query(Uri.parse(FilteredListDao.CONTENT_URI + "/" + table), projection,
					"checked = 1", null, FilteredListDao.ID);
			if (c == null) {
				Log.i("COUNT", "NULL CURSOR");
			} else if (c.getCount() < 1) {
				if (getButton(table) != null)
					getButton(table).setTextColor(Color.BLACK);
			} else {
				if (getButton(table) != null)
					getButton(table).setTextColor(Color.BLUE);
			}
		}

	}

	private Button getButton(String name) {
		if (name == "region") {
			return (Button) findViewById(R.id.b_region_list);
		} else if (name == "country") {
			return (Button) findViewById(R.id.b_country_list);
		} else if (name == "attacktype") {
			return (Button) findViewById(R.id.b_attacktype_list);
		} else if (name == "targettype") {
			return (Button) findViewById(R.id.b_targettype_list);
		} else if (name == "weapontype") {
			return (Button) findViewById(R.id.b_weapontype_list);
		} else if (name == "dbsource") {
			return (Button) findViewById(R.id.b_dbsource_list);
		} else if (name == "year") {
			return (Button) findViewById(R.id.b_year_list);
		}
		return null;
	}

	private void bindViews() {
		mButtonRegionList = (Button) findViewById(R.id.b_region_list);
		mButtonRegionList.setOnClickListener(this);

		mButtonCountryList = (Button) findViewById(R.id.b_country_list);
		mButtonCountryList.setOnClickListener(this);

		mButtonAttackTypeList = (Button) findViewById(R.id.b_attacktype_list);
		mButtonAttackTypeList.setOnClickListener(this);

		mButtonTargetTypeList = (Button) findViewById(R.id.b_targettype_list);
		mButtonTargetTypeList.setOnClickListener(this);

		mButtonWeaponTypeList = (Button) findViewById(R.id.b_weapontype_list);
		mButtonWeaponTypeList.setOnClickListener(this);

		mButtonDbSourceList = (Button) findViewById(R.id.b_dbsource_list);
		mButtonDbSourceList.setOnClickListener(this);

		mButtonYearList = (Button) findViewById(R.id.b_year_list);
		mButtonYearList.setOnClickListener(this);

		mButtonAttackList = (Button) findViewById(R.id.b_attack_list);
		mButtonAttackList.setOnClickListener(this);
	}

	@Override
	public void onClick(final View view) {
		Intent intent = null;
		if (view == mButtonRegionList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "region");
			intent.putExtra("filterDefaultUri", WSConfig.WS_REGION_LIST_URL);
		}
		if (view == mButtonCountryList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "country");
			intent.putExtra("filterDefaultUri", WSConfig.WS_COUNTRY_LIST_URL);
		}
		if (view == mButtonAttackTypeList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "attacktype");
			intent.putExtra("filterDefaultUri", WSConfig.WS_ATTACKTYPE_LIST_URL);
		}
		if (view == mButtonTargetTypeList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "targettype");
			intent.putExtra("filterDefaultUri", WSConfig.WS_TARGETTYPE_LIST_URL);
		}
		if (view == mButtonWeaponTypeList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "weapontype");
			intent.putExtra("filterDefaultUri", WSConfig.WS_WEAPONTYPE_LIST_URL);
		}
		if (view == mButtonYearList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "year");
			intent.putExtra("filterDefaultUri", WSConfig.WS_YEAR_LIST_URL);
		}
		if (view == mButtonDbSourceList) {
			intent = new Intent(this, FilteredListActivity.class);
			intent.putExtra("filterType", "dbsource");
			intent.putExtra("filterDefaultUri", WSConfig.WS_DBSOURCE_LIST_URL);
		}
		if (view == mButtonAttackList) {
			String[] projection = { FilteredListDao.ID };
			StringBuilder filters = new StringBuilder("");

			for (String table : tables) {
				Cursor c = getContentResolver().query(Uri.parse(FilteredListDao.CONTENT_URI + "/" + table), projection,
						"checked = 1", null, FilteredListDao.ID);
				if (c == null) {
					Log.i("COUNT", "NULL CURSOR");
				} else if (c.getCount() < 1) {
					Log.i("COUNT", new Integer(c.getCount()).toString());
				} else {
					while (c.moveToNext()) {
						String id = new Integer(c.getInt(0)).toString();
						if (table.equals("weapontype")) {
							for (int i = 1; i < 5; i++)
								filters.append("&" + "weaptype" + i + "=" + id);
						} else if (table.equals("attacktype")) {
							for (int i = 1; i < 4; i++)
								filters.append("&" + table + i + "=" + id);
						} else if (table.equals("targettype")) {
							for (int i = 1; i < 4; i++)
								filters.append("&" + "targtype" + i + "=" + id);
						} else
							filters.append("&" + table + "=" + id);
						Log.i(table, new Integer(c.getInt(0)).toString());
					}
					Log.i("COUNT", new Integer(c.getCount()).toString());

				}
			}
			intent = new Intent(this, AttackListActivity.class);
			intent.putExtra("filterType", "attacks");
			intent.putExtra("filterDefaultUri", WSConfig.WS_ATTACK_LIST_URL + filters.toString());
		}

		if (intent != null) {
			startActivity(intent);
		}
	}
}
