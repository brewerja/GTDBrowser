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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.gtdbrowser.R;
import com.gtdbrowser.config.WSConfig;

public class HomeActivity extends Activity implements OnClickListener {

	private Button mButtonRegionList;
	private Button mButtonAttackList;
	private Button mButtonCountryList;
	private Button mButtonAttackTypeList;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);
		bindViews();
	}

	private void bindViews() {
		mButtonRegionList = (Button) findViewById(R.id.b_region_list);
		mButtonRegionList.setOnClickListener(this);
		
		mButtonCountryList = (Button) findViewById(R.id.b_country_list);
		mButtonCountryList.setOnClickListener(this);

		mButtonAttackTypeList = (Button) findViewById(R.id.b_attacktype_list);
		mButtonAttackTypeList.setOnClickListener(this);
		
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
		if (view == mButtonAttackList) {
			intent = new Intent(this, AttackListActivity.class);
		}

		if (intent != null) {
			startActivity(intent);
		}
	}
}
