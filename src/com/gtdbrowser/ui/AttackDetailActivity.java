package com.gtdbrowser.ui;

import com.gtdbrowser.R;
import com.gtdbrowser.data.provider.GtdContent.AttackDao;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class AttackDetailActivity extends Activity {

	String id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attack_detail);

		id = getIntent().getStringExtra("id");
		Cursor c = getContentResolver().query(AttackDao.CONTENT_URI, AttackDao.CONTENT_PROJECTION, "id = " + id, null,
				null);
		c.moveToFirst();

		String ID = c.getString(AttackDao.CONTENT_GTDID_COLUMN);
		String YEAR = c.getString(AttackDao.CONTENT_YEAR_COLUMN);
		String MONTH = c.getString(AttackDao.CONTENT_MONTH_COLUMN);
		String DAY = c.getString(AttackDao.CONTENT_DAY_COLUMN);
		String DATE = c.getString(AttackDao.CONTENT_DATE_COLUMN);
		String APPROXDATE = c.getString(AttackDao.CONTENT_APPROXDATE_COLUMN);
		String EXTENDED = c.getString(AttackDao.CONTENT_EXTENDED_COLUMN);
		String RESOLUTION = c.getString(AttackDao.CONTENT_RESOLUTION_COLUMN);
		String COUNTRY = c.getString(AttackDao.CONTENT_COUNTRY_COLUMN);
		String REGION = c.getString(AttackDao.CONTENT_REGION_COLUMN);
		String PROVSTATE = c.getString(AttackDao.CONTENT_PROVSTATE_COLUMN);
		String CITY = c.getString(AttackDao.CONTENT_CITY_COLUMN);
		String VICINITY = c.getString(AttackDao.CONTENT_VICINITY_COLUMN);
		String LOCATION = c.getString(AttackDao.CONTENT_LOCATION_COLUMN);
//		String LAT = c.getString(AttackDao.CONTENT_LAT_COLUMN);
//		String LON = c.getString(AttackDao.CONTENT_LON_COLUMN);
		String SUMMARY = c.getString(AttackDao.CONTENT_SUMMARY_COLUMN);
		String CRIT1 = c.getString(AttackDao.CONTENT_CRIT1_COLUMN);
		String CRIT2 = c.getString(AttackDao.CONTENT_CRIT2_COLUMN);
		String CRIT3 = c.getString(AttackDao.CONTENT_CRIT3_COLUMN);
		String DOUBTTERR = c.getString(AttackDao.CONTENT_DOUBTTERR_COLUMN);
		String ALTERNATIVE = c.getString(AttackDao.CONTENT_ALTERNATIVE_COLUMN);
		String MULTIPLE = c.getString(AttackDao.CONTENT_MULTIPLE_COLUMN);
		String CONFLICT = c.getString(AttackDao.CONTENT_CONFLICT_COLUMN);
		String SUCCESS = c.getString(AttackDao.CONTENT_SUCCESS_COLUMN);
		String SUICIDE = c.getString(AttackDao.CONTENT_SUICIDE_COLUMN);
		String ATTACKTYPE1 = c.getString(AttackDao.CONTENT_ATTACKTYPE1_COLUMN);
		String ATTACKTYPE2 = c.getString(AttackDao.CONTENT_ATTACKTYPE2_COLUMN);
		String ATTACKTYPE3 = c.getString(AttackDao.CONTENT_ATTACKTYPE3_COLUMN);
		String TARGTYPE1 = c.getString(AttackDao.CONTENT_TARGTYPE1_COLUMN);
		String CORP1 = c.getString(AttackDao.CONTENT_CORP1_COLUMN);
		String TARGET1 = c.getString(AttackDao.CONTENT_TARGET1_COLUMN);
		String NATLTY1 = c.getString(AttackDao.CONTENT_NATLTY1_COLUMN);
		String TARGTYPE2 = c.getString(AttackDao.CONTENT_TARGTYPE2_COLUMN);
		String CORP2 = c.getString(AttackDao.CONTENT_CORP2_COLUMN);
		String TARGET2 = c.getString(AttackDao.CONTENT_TARGET2_COLUMN);
		String NATLTY2 = c.getString(AttackDao.CONTENT_NATLTY2_COLUMN);
		String TARGTYPE3 = c.getString(AttackDao.CONTENT_TARGTYPE3_COLUMN);
		String CORP3 = c.getString(AttackDao.CONTENT_CORP3_COLUMN);
		String TARGET3 = c.getString(AttackDao.CONTENT_TARGET3_COLUMN);
		String NATLTY3 = c.getString(AttackDao.CONTENT_NATLTY3_COLUMN);
		String GNAME = c.getString(AttackDao.CONTENT_GNAME_COLUMN);
		String GSUBNAME = c.getString(AttackDao.CONTENT_GSUBNAME_COLUMN);
		String GNAME2 = c.getString(AttackDao.CONTENT_GNAME2_COLUMN);
		String GSUBNAME2 = c.getString(AttackDao.CONTENT_GSUBNAME2_COLUMN);
		String GNAME3 = c.getString(AttackDao.CONTENT_GNAME3_COLUMN);
		String GSUBNAME3 = c.getString(AttackDao.CONTENT_GSUBNAME3_COLUMN);
		String MOTIVE = c.getString(AttackDao.CONTENT_MOTIVE_COLUMN);
		String GUNCERTAIN1 = c.getString(AttackDao.CONTENT_GUNCERTAIN1_COLUMN);
		String GUNCERTAIN2 = c.getString(AttackDao.CONTENT_GUNCERTAIN2_COLUMN);
		String GUNCERTAIN3 = c.getString(AttackDao.CONTENT_GUNCERTAIN3_COLUMN);
		String NPERPS = c.getString(AttackDao.CONTENT_NPERPS_COLUMN);
		String NPERPCAP = c.getString(AttackDao.CONTENT_NPERPCAP_COLUMN);
		String CLAIMED = c.getString(AttackDao.CONTENT_CLAIMED_COLUMN);
		String CLAIMMODE = c.getString(AttackDao.CONTENT_CLAIMMODE_COLUMN);
		String CLAIMCONF = c.getString(AttackDao.CONTENT_CLAIMCONF_COLUMN);
		String CLAIM2 = c.getString(AttackDao.CONTENT_CLAIM2_COLUMN);
		String CLAIMMODE2 = c.getString(AttackDao.CONTENT_CLAIMMODE2_COLUMN);
		String CLAIMCONF2 = c.getString(AttackDao.CONTENT_CLAIMCONF2_COLUMN);
		String CLAIM3 = c.getString(AttackDao.CONTENT_CLAIM3_COLUMN);
		String CLAIMMODE3 = c.getString(AttackDao.CONTENT_CLAIMMODE3_COLUMN);
		String CLAIMCONF3 = c.getString(AttackDao.CONTENT_CLAIMCONF3_COLUMN);
		String COMPCLAIM = c.getString(AttackDao.CONTENT_COMPCLAIM_COLUMN);
		String WEAPTYPE1 = c.getString(AttackDao.CONTENT_WEAPTYPE1_COLUMN);
		String WEAPSUBTYPE1 = c.getString(AttackDao.CONTENT_WEAPSUBTYPE1_COLUMN);
		String WEAPTYPE2 = c.getString(AttackDao.CONTENT_WEAPTYPE2_COLUMN);
		String WEAPSUBTYPE2 = c.getString(AttackDao.CONTENT_WEAPSUBTYPE2_COLUMN);
		String WEAPTYPE3 = c.getString(AttackDao.CONTENT_WEAPTYPE3_COLUMN);
		String WEAPSUBTYPE3 = c.getString(AttackDao.CONTENT_WEAPSUBTYPE3_COLUMN);
		String WEAPTYPE4 = c.getString(AttackDao.CONTENT_WEAPTYPE4_COLUMN);
		String WEAPSUBTYPE4 = c.getString(AttackDao.CONTENT_WEAPSUBTYPE4_COLUMN);
		String WEAPDETAIL = c.getString(AttackDao.CONTENT_WEAPDETAIL_COLUMN);
		String NKILL = c.getString(AttackDao.CONTENT_NKILL_COLUMN);
		String NKILLUS = c.getString(AttackDao.CONTENT_NKILLUS_COLUMN);
		String NKILLTER = c.getString(AttackDao.CONTENT_NKILLTER_COLUMN);
		String NWOUND = c.getString(AttackDao.CONTENT_NWOUND_COLUMN);
		String NWOUNDUS = c.getString(AttackDao.CONTENT_NWOUNDUS_COLUMN);
		String NWOUNDTER = c.getString(AttackDao.CONTENT_NWOUNDTER_COLUMN);
		String PROPERTY = c.getString(AttackDao.CONTENT_PROPERTY_COLUMN);
		String PROPEXTENT = c.getString(AttackDao.CONTENT_PROPEXTENT_COLUMN);
		String PROPVALUE = c.getString(AttackDao.CONTENT_PROPVALUE_COLUMN);
		String PROPCOMMENT = c.getString(AttackDao.CONTENT_PROPCOMMENT_COLUMN);
		String ISHOSTKID = c.getString(AttackDao.CONTENT_ISHOSTKID_COLUMN);
		String NHOSTKID = c.getString(AttackDao.CONTENT_NHOSTKID_COLUMN);
		String NHOSTKIDUS = c.getString(AttackDao.CONTENT_NHOSTKIDUS_COLUMN);
		String NHOURS = c.getString(AttackDao.CONTENT_NHOURS_COLUMN);
		String NDAYS = c.getString(AttackDao.CONTENT_NDAYS_COLUMN);
		String DIVERT = c.getString(AttackDao.CONTENT_DIVERT_COLUMN);
		String KIDHIJCOUNTRY = c.getString(AttackDao.CONTENT_KIDHIJCOUNTRY_COLUMN);
		String RANSOM = c.getString(AttackDao.CONTENT_RANSOM_COLUMN);
		String RANSOMAMT = c.getString(AttackDao.CONTENT_RANSOMAMT_COLUMN);
		String RANSOMAMTUS = c.getString(AttackDao.CONTENT_RANSOMAMTUS_COLUMN);
		String RANSOMPAID = c.getString(AttackDao.CONTENT_RANSOMPAID_COLUMN);
		String RANSOMPAIDUS = c.getString(AttackDao.CONTENT_RANSOMPAIDUS_COLUMN);
		String RANSOMNOTE = c.getString(AttackDao.CONTENT_RANSOMNOTE_COLUMN);
		String HOSTKIDOUTCOME = c.getString(AttackDao.CONTENT_HOSTKIDOUTCOME_COLUMN);
		String NRELEASED = c.getString(AttackDao.CONTENT_NRELEASED_COLUMN);
		String ADDNOTES = c.getString(AttackDao.CONTENT_ADDNOTES_COLUMN);
		String SCITE1 = c.getString(AttackDao.CONTENT_SCITE1_COLUMN);
		String SCITE2 = c.getString(AttackDao.CONTENT_SCITE2_COLUMN);
		String SCITE3 = c.getString(AttackDao.CONTENT_SCITE3_COLUMN);
		String DBSOURCE = c.getString(AttackDao.CONTENT_DBSOURCE_COLUMN);

		// PULLED OUT LAT, LON
		String fullText = "ID: " + ID + "\n" + "YEAR: " + YEAR + "\n" + "MONTH: " + MONTH + "\n" + "DAY: " + DAY + "\n"
				+ "DATE: " + DATE + "\n" + "APPROXDATE: " + APPROXDATE + "\n" + "EXTENDED: " + EXTENDED + "\n"
				+ "RESOLUTION: " + RESOLUTION + "\n" + "COUNTRY: " + COUNTRY + "\n" + "REGION: " + REGION + "\n"
				+ "PROVSTATE: " + PROVSTATE + "\n" + "CITY: " + CITY + "\n" + "VICINITY: " + VICINITY + "\n"
				+ "LOCATION: " + LOCATION + "\n" +  "SUMMARY: " + SUMMARY
				+ "\n" + "CRIT1: " + CRIT1 + "\n" + "CRIT2: " + CRIT2 + "\n" + "CRIT3: " + CRIT3 + "\n" + "DOUBTTERR: "
				+ DOUBTTERR + "\n" + "ALTERNATIVE: " + ALTERNATIVE + "\n" + "MULTIPLE: " + MULTIPLE + "\n"
				+ "CONFLICT: " + CONFLICT + "\n" + "SUCCESS: " + SUCCESS + "\n" + "SUICIDE: " + SUICIDE + "\n"
				+ "ATTACKTYPE1: " + ATTACKTYPE1 + "\n" + "ATTACKTYPE2: " + ATTACKTYPE2 + "\n" + "ATTACKTYPE3: "
				+ ATTACKTYPE3 + "\n" + "TARGTYPE1: " + TARGTYPE1 + "\n" + "CORP1: " + CORP1 + "\n" + "TARGET1: "
				+ TARGET1 + "\n" + "NATLTY1: " + NATLTY1 + "\n" + "TARGTYPE2: " + TARGTYPE2 + "\n" + "CORP2: " + CORP2
				+ "\n" + "TARGET2: " + TARGET2 + "\n" + "NATLTY2: " + NATLTY2 + "\n" + "TARGTYPE3: " + TARGTYPE3 + "\n"
				+ "CORP3: " + CORP3 + "\n" + "TARGET3: " + TARGET3 + "\n" + "NATLTY3: " + NATLTY3 + "\n" + "GNAME: "
				+ GNAME + "\n" + "GSUBNAME: " + GSUBNAME + "\n" + "GNAME2: " + GNAME2 + "\n" + "GSUBNAME2: "
				+ GSUBNAME2 + "\n" + "GNAME3: " + GNAME3 + "\n" + "GSUBNAME3: " + GSUBNAME3 + "\n" + "MOTIVE: "
				+ MOTIVE + "\n" + "GUNCERTAIN1: " + GUNCERTAIN1 + "\n" + "GUNCERTAIN2: " + GUNCERTAIN2 + "\n"
				+ "GUNCERTAIN3: " + GUNCERTAIN3 + "\n" + "NPERPS: " + NPERPS + "\n" + "NPERPCAP: " + NPERPCAP + "\n"
				+ "CLAIMED: " + CLAIMED + "\n" + "CLAIMMODE: " + CLAIMMODE + "\n" + "CLAIMCONF: " + CLAIMCONF + "\n"
				+ "CLAIM2: " + CLAIM2 + "\n" + "CLAIMMODE2: " + CLAIMMODE2 + "\n" + "CLAIMCONF2: " + CLAIMCONF2 + "\n"
				+ "CLAIM3: " + CLAIM3 + "\n" + "CLAIMMODE3: " + CLAIMMODE3 + "\n" + "CLAIMCONF3: " + CLAIMCONF3 + "\n"
				+ "COMPCLAIM: " + COMPCLAIM + "\n" + "WEAPTYPE1: " + WEAPTYPE1 + "\n" + "WEAPSUBTYPE1: " + WEAPSUBTYPE1
				+ "\n" + "WEAPTYPE2: " + WEAPTYPE2 + "\n" + "WEAPSUBTYPE2: " + WEAPSUBTYPE2 + "\n" + "WEAPTYPE3: "
				+ WEAPTYPE3 + "\n" + "WEAPSUBTYPE3: " + WEAPSUBTYPE3 + "\n" + "WEAPTYPE4: " + WEAPTYPE4 + "\n"
				+ "WEAPSUBTYPE4: " + WEAPSUBTYPE4 + "\n" + "WEAPDETAIL: " + WEAPDETAIL + "\n" + "NKILL: " + NKILL
				+ "\n" + "NKILLUS: " + NKILLUS + "\n" + "NKILLTER: " + NKILLTER + "\n" + "NWOUND: " + NWOUND + "\n"
				+ "NWOUNDUS: " + NWOUNDUS + "\n" + "NWOUNDTER: " + NWOUNDTER + "\n" + "PROPERTY: " + PROPERTY + "\n"
				+ "PROPEXTENT: " + PROPEXTENT + "\n" + "PROPVALUE: " + PROPVALUE + "\n" + "PROPCOMMENT: " + PROPCOMMENT
				+ "\n" + "ISHOSTKID: " + ISHOSTKID + "\n" + "NHOSTKID: " + NHOSTKID + "\n" + "NHOSTKIDUS: "
				+ NHOSTKIDUS + "\n" + "NHOURS: " + NHOURS + "\n" + "NDAYS: " + NDAYS + "\n" + "DIVERT: " + DIVERT
				+ "\n" + "KIDHIJCOUNTRY: " + KIDHIJCOUNTRY + "\n" + "RANSOM: " + RANSOM + "\n" + "RANSOMAMT: "
				+ RANSOMAMT + "\n" + "RANSOMAMTUS: " + RANSOMAMTUS + "\n" + "RANSOMPAID: " + RANSOMPAID + "\n"
				+ "RANSOMPAIDUS: " + RANSOMPAIDUS + "\n" + "RANSOMNOTE: " + RANSOMNOTE + "\n" + "HOSTKIDOUTCOME: "
				+ HOSTKIDOUTCOME + "\n" + "NRELEASED: " + NRELEASED + "\n" + "ADDNOTES: " + ADDNOTES + "\n"
				+ "SCITE1: " + SCITE1 + "\n" + "SCITE2: " + SCITE2 + "\n" + "SCITE3: " + SCITE3 + "\n" + "DBSOURCE: "
				+ DBSOURCE + "\n";

		TextView tv = (TextView) findViewById(R.id.txt1);
		tv.setText(fullText);

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
