package com.gtdbrowser.ui;

import com.gtdbrowser.R;
import com.gtdbrowser.data.provider.GtdContent.AttackDao;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class AttackDetailActivity extends Activity {

	String id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attack_detail);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		Cursor c = getContentResolver().query(AttackDao.CONTENT_URI, AttackDao.CONTENT_PROJECTION, "id = " + id, null,
				null);
		c.moveToFirst();
		String newString = c.getString(AttackDao.CONTENT_CITY_COLUMN);
		TextView tv = (TextView) findViewById(R.id.txt1);
		tv.setText("GTD ID: " + id + "\n" + newString);

		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Tab 1");

		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Tab 2");
		spec2.setContent(R.id.tab2);

		TabSpec spec3 = tabHost.newTabSpec("Tab 3");
		spec3.setIndicator("Tab 3");
		spec3.setContent(R.id.tab3);

		TabSpec spec4 = tabHost.newTabSpec("Tab 4");
		spec4.setIndicator("Tab 4");
		spec4.setContent(R.id.tab4);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		tabHost.addTab(spec4);
	}
}
