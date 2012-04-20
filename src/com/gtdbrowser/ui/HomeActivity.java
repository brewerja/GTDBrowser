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

public class HomeActivity extends Activity implements OnClickListener {

	private Button mButtonRegionList;
	private Button mButtonAttackList;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);
		bindViews();
	}

	private void bindViews() {
		mButtonRegionList = (Button) findViewById(R.id.b_region_list);
		mButtonRegionList.setOnClickListener(this);

		mButtonAttackList = (Button) findViewById(R.id.b_attack_list);
		mButtonAttackList.setOnClickListener(this);
	}

	@Override
	public void onClick(final View view) {
		Intent intent = null;
		if (view == mButtonRegionList) {
			intent = new Intent(this, RegionListActivity.class);
		}
		if (view == mButtonAttackList) {
			intent = new Intent(this, AttackListActivity.class);
		}

		if (intent != null) {
			startActivity(intent);
		}
	}
}
