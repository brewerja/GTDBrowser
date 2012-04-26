package com.gtdbrowser.ui;

import com.gtdbrowser.data.provider.GtdContent.AttackDao;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class AttackDetailActivity extends Activity {

	String id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		Cursor c = getContentResolver().query(AttackDao.CONTENT_URI, AttackDao.CONTENT_PROJECTION, "id = " + id, null,
				null);
		c.moveToFirst();
		String newString = c.getString(AttackDao.CONTENT_CITY_COLUMN);
		TextView tv = new TextView(this);
		tv.setText("GTD ID: " + id + "\n" + newString);
		setContentView(tv);
	}
}
