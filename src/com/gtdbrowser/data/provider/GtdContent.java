/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.data.provider;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;

import com.foxykeep.datadroid.provider.util.DatabaseUtil;
import com.gtdbrowser.data.model.Attack;
import com.gtdbrowser.data.model.Region;

/**
 * {@link GtdContent} is the superclass of the various classes of content stored
 * by {@link GtdProvider}. It adds to {@link DatabaseContent} the
 * {@link #AUTHORITY} and {@link #CONTENT_URI}
 * <p>
 * </p>
 */
public abstract class GtdContent {
	public static final Uri CONTENT_URI = Uri.parse("content://" + GtdProvider.AUTHORITY);

	public interface RegionDaoColumns {
		public static final String NAME = "name";
		public static final String ID = "id";
		public static final String NUM_ATTACKS = "num_attacks";
	}

	public interface AttackDaoColumns {
		public static final String ID = "id";
		public static final String DATE = "date";
		public static final String APPROXDATE = "approxdate";
		public static final String EXTENDED = "extended";
		public static final String RESOLUTION = "resolution";
		public static final String COUNTRY = "country";
		public static final String REGION = "region";
		public static final String PROVSTATE = "provstate";
		public static final String CITY = "city";
		public static final String VICINITY = "vicinity";
		public static final String LOCATION = "location";
		public static final String LAT = "lat";
		public static final String LON = "lon";
		public static final String SUMMARY = "summary";
		public static final String CRIT1 = "crit1";
		public static final String CRIT2 = "crit2";
		public static final String CRIT3 = "crit3";
		public static final String DOUBTTERR = "doubtterr";
		public static final String ALTERNATIVE = "alternative";
		public static final String MULTIPLE = "multiple";
		public static final String CONFLICT = "conflict";
		public static final String SUCCESS = "success";
		public static final String SUICIDE = "suicide";
		public static final String ATTACKTYPE1 = "attacktype1";
		public static final String ATTACKTYPE2 = "attacktype2";
		public static final String ATTACKTYPE3 = "attacktype3";
		public static final String TARGTYPE1 = "targtype1";
		public static final String CORP1 = "corp1";
		public static final String TARGET1 = "target1";
		public static final String NATLTY1 = "natlty1";
		public static final String TARGTYPE2 = "targtype2";
		public static final String CORP2 = "corp2";
		public static final String TARGET2 = "target2";
		public static final String NATLTY2 = "natlty2";
		public static final String TARGTYPE3 = "targtype3";
		public static final String CORP3 = "corp3";
		public static final String TARGET3 = "target3";
		public static final String NATLTY3 = "natlty3";
		public static final String GNAME = "gname";
		public static final String GSUBNAME = "gsubname";
		public static final String GNAME2 = "gname2";
		public static final String GSUBNAME2 = "gsubname2";
		public static final String GNAME3 = "gname3";
		public static final String GSUBNAME3 = "gsubname3";
		public static final String MOTIVE = "motive";
		public static final String GUNCERTAIN1 = "guncertain1";
		public static final String GUNCERTAIN2 = "guncertain2";
		public static final String GUNCERTAIN3 = "guncertain3";
		public static final String NPERPS = "nperps";
		public static final String NPERPCAP = "nperpcap";
		public static final String CLAIMED = "claimed";
		public static final String CLAIMMODE = "claimmode";
		public static final String CLAIMCONF = "claimconf";
		public static final String CLAIM2 = "claim2";
		public static final String CLAIMMODE2 = "claimmode2";
		public static final String CLAIMCONF2 = "claimconf2";
		public static final String CLAIM3 = "claim3";
		public static final String CLAIMMODE3 = "claimmode3";
		public static final String CLAIMCONF3 = "claimconf3";
		public static final String COMPCLAIM = "compclaim";
		public static final String WEAPTYPE1 = "weaptype1";
		public static final String WEAPSUBTYPE1 = "weapsubtype1";
		public static final String WEAPTYPE2 = "weaptype2";
		public static final String WEAPSUBTYPE2 = "weapsubtype2";
		public static final String WEAPTYPE3 = "weaptype3";
		public static final String WEAPSUBTYPE3 = "weapsubtype3";
		public static final String WEAPTYPE4 = "weaptype4";
		public static final String WEAPSUBTYPE4 = "weapsubtype4";
		public static final String WEAPDETAIL = "weapdetail";
		public static final String NKILL = "nkill";
		public static final String NKILLUS = "nkillus";
		public static final String NKILLTER = "nkillter";
		public static final String NWOUND = "nwound";
		public static final String NWOUNDUS = "nwoundus";
		public static final String NWOUNDTER = "nwoundter";
		public static final String PROPERTY = "property";
		public static final String PROPEXTENT = "propextent";
		public static final String PROPVALUE = "propvalue";
		public static final String PROPCOMMENT = "propcomment";
		public static final String ISHOSTKID = "ishostkid";
		public static final String NHOSTKID = "nhostkid";
		public static final String NHOSTKIDUS = "nhostkidus";
		public static final String NHOURS = "nhours";
		public static final String NDAYS = "ndays";
		public static final String DIVERT = "divert";
		public static final String KIDHIJCOUNTRY = "kidhijcountry";
		public static final String RANSOM = "ransom";
		public static final String RANSOMAMT = "ransomamt";
		public static final String RANSOMAMTUS = "ransomamtus";
		public static final String RANSOMPAID = "ransompaid";
		public static final String RANSOMPAIDUS = "ransompaidus";
		public static final String RANSOMNOTE = "ransomnote";
		public static final String HOSTKIDOUTCOME = "hostkidoutcome";
		public static final String NRELEASED = "nreleased";
		public static final String ADDNOTES = "addnotes";
		public static final String SCITE1 = "scite1";
		public static final String SCITE2 = "scite2";
		public static final String SCITE3 = "scite3";
		public static final String DBSOURCE = "dbsource";

	}

	public static final class RegionDao extends GtdContent implements RegionDaoColumns, BaseColumns {
		public static final String TABLE_NAME = "region";
		public static final Uri CONTENT_URI = Uri.parse(GtdContent.CONTENT_URI + "/" + TABLE_NAME);
		public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/com.gtdbrowser.data.provider.region";
		public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/com.gtdbrowser.data.provider.region";

		public static final String MIN_ID_SELECTION = ID + " >= ?";
		public static final String NAME_ORDER_BY = NUM_ATTACKS + " DESC";

		public static final int CONTENT_ID_COLUMN = 0;
		public static final int CONTENT_NAME_COLUMN = 1;
		public static final int CONTENT_REGION_ID_COLUMN = 2;
		public static final int CONTENT_NUM_ATTACKS_COLUMN = 3;
		public static final String[] CONTENT_PROJECTION = new String[] { _ID, NAME, ID, NUM_ATTACKS };

		static void createTable(final SQLiteDatabase db) {
			final String s = " (" + _ID + " integer primary key autoincrement, " + NAME + " text, " + ID + " integer, "
					+ NUM_ATTACKS + " integer " + ");";

			db.execSQL("create table " + TABLE_NAME + s);

			db.execSQL(DatabaseUtil.getCreateIndexString(TABLE_NAME, NAME));
		}

		static void upgradeTable(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
			try {
				db.execSQL("drop table " + TABLE_NAME);
			} catch (final SQLException e) {
			}
			createTable(db);
		}

		public static String getBulkInsertString() {
			final StringBuffer sqlRequest = new StringBuffer("INSERT INTO ");
			sqlRequest.append(TABLE_NAME);
			sqlRequest.append(" ( ");
			sqlRequest.append(NAME);
			sqlRequest.append(", ");
			sqlRequest.append(ID);
			sqlRequest.append(", ");
			sqlRequest.append(NUM_ATTACKS);
			sqlRequest.append(" ) ");
			sqlRequest.append(" VALUES (?, ?, ?)");
			return sqlRequest.toString();
		}

		public static void bindValuesInBulkInsert(final SQLiteStatement stmt, final ContentValues values) {
			int i = 1;
			String value = values.getAsString(NAME);
			stmt.bindString(i++, value != null ? value : "");
			stmt.bindLong(i++, values.getAsInteger(ID));
			stmt.bindLong(i++, values.getAsInteger(NUM_ATTACKS));
		}

		public static ContentValues getContentValues(final Region region) {
			ContentValues values = new ContentValues();
			values.put(NAME, region.name);
			values.put(ID, region.id);
			values.put(NUM_ATTACKS, region.num_attacks);
			return values;
		}
	}

	public static final class AttackDao extends GtdContent implements AttackDaoColumns, BaseColumns {
		public static final String TABLE_NAME = "attacks";
		public static final Uri CONTENT_URI = Uri.parse(GtdContent.CONTENT_URI + "/" + TABLE_NAME);
		public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/com.gtdbrowser.data.provider.attacks";
		public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/com.gtdbrowser.data.provider.attacks";

		public static final String MIN_ID_SELECTION = ID + " >= ?";
		public static final String ID_ORDER_BY = ID + " ASC";

		public static final int CONTENT_GTDID_COLUMN = 1;
		public static final int CONTENT_DATE_COLUMN = 2;
		public static final int CONTENT_APPROXDATE_COLUMN = 3;
		public static final int CONTENT_EXTENDED_COLUMN = 4;
		public static final int CONTENT_RESOLUTION_COLUMN = 5;
		public static final int CONTENT_COUNTRY_COLUMN = 6;
		public static final int CONTENT_REGION_COLUMN = 7;
		public static final int CONTENT_PROVSTATE_COLUMN = 8;
		public static final int CONTENT_CITY_COLUMN = 9;
		public static final int CONTENT_VICINITY_COLUMN = 10;
		public static final int CONTENT_LOCATION_COLUMN = 11;
		public static final int CONTENT_LAT_COLUMN = 12;
		public static final int CONTENT_LON_COLUMN = 13;
		public static final int CONTENT_SUMMARY_COLUMN = 14;
		public static final int CONTENT_CRIT1_COLUMN = 15;
		public static final int CONTENT_CRIT2_COLUMN = 16;
		public static final int CONTENT_CRIT3_COLUMN = 17;
		public static final int CONTENT_DOUBTTERR_COLUMN = 18;
		public static final int CONTENT_ALTERNATIVE_COLUMN = 19;
		public static final int CONTENT_MULTIPLE_COLUMN = 20;
		public static final int CONTENT_CONFLICT_COLUMN = 21;
		public static final int CONTENT_SUCCESS_COLUMN = 22;
		public static final int CONTENT_SUICIDE_COLUMN = 23;
		public static final int CONTENT_ATTACKTYPE1_COLUMN = 24;
		public static final int CONTENT_ATTACKTYPE2_COLUMN = 25;
		public static final int CONTENT_ATTACKTYPE3_COLUMN = 26;
		public static final int CONTENT_TARGTYPE1_COLUMN = 27;
		public static final int CONTENT_CORP1_COLUMN = 28;
		public static final int CONTENT_TARGET1_COLUMN = 29;
		public static final int CONTENT_NATLTY1_COLUMN = 30;
		public static final int CONTENT_TARGTYPE2_COLUMN = 31;
		public static final int CONTENT_CORP2_COLUMN = 32;
		public static final int CONTENT_TARGET2_COLUMN = 33;
		public static final int CONTENT_NATLTY2_COLUMN = 34;
		public static final int CONTENT_TARGTYPE3_COLUMN = 35;
		public static final int CONTENT_CORP3_COLUMN = 36;
		public static final int CONTENT_TARGET3_COLUMN = 37;
		public static final int CONTENT_NATLTY3_COLUMN = 38;
		public static final int CONTENT_GNAME_COLUMN = 39;
		public static final int CONTENT_GSUBNAME_COLUMN = 40;
		public static final int CONTENT_GNAME2_COLUMN = 41;
		public static final int CONTENT_GSUBNAME2_COLUMN = 42;
		public static final int CONTENT_GNAME3_COLUMN = 43;
		public static final int CONTENT_GSUBNAME3_COLUMN = 44;
		public static final int CONTENT_MOTIVE_COLUMN = 45;
		public static final int CONTENT_GUNCERTAIN1_COLUMN = 46;
		public static final int CONTENT_GUNCERTAIN2_COLUMN = 47;
		public static final int CONTENT_GUNCERTAIN3_COLUMN = 48;
		public static final int CONTENT_NPERPS_COLUMN = 49;
		public static final int CONTENT_NPERPCAP_COLUMN = 50;
		public static final int CONTENT_CLAIMED_COLUMN = 51;
		public static final int CONTENT_CLAIMMODE_COLUMN = 52;
		public static final int CONTENT_CLAIMCONF_COLUMN = 53;
		public static final int CONTENT_CLAIM2_COLUMN = 54;
		public static final int CONTENT_CLAIMMODE2_COLUMN = 55;
		public static final int CONTENT_CLAIMCONF2_COLUMN = 56;
		public static final int CONTENT_CLAIM3_COLUMN = 57;
		public static final int CONTENT_CLAIMMODE3_COLUMN = 58;
		public static final int CONTENT_CLAIMCONF3_COLUMN = 59;
		public static final int CONTENT_COMPCLAIM_COLUMN = 60;
		public static final int CONTENT_WEAPTYPE1_COLUMN = 61;
		public static final int CONTENT_WEAPSUBTYPE1_COLUMN = 62;
		public static final int CONTENT_WEAPTYPE2_COLUMN = 63;
		public static final int CONTENT_WEAPSUBTYPE2_COLUMN = 64;
		public static final int CONTENT_WEAPTYPE3_COLUMN = 65;
		public static final int CONTENT_WEAPSUBTYPE3_COLUMN = 66;
		public static final int CONTENT_WEAPTYPE4_COLUMN = 67;
		public static final int CONTENT_WEAPSUBTYPE4_COLUMN = 68;
		public static final int CONTENT_WEAPDETAIL_COLUMN = 69;
		public static final int CONTENT_NKILL_COLUMN = 70;
		public static final int CONTENT_NKILLUS_COLUMN = 71;
		public static final int CONTENT_NKILLTER_COLUMN = 72;
		public static final int CONTENT_NWOUND_COLUMN = 73;
		public static final int CONTENT_NWOUNDUS_COLUMN = 74;
		public static final int CONTENT_NWOUNDTER_COLUMN = 75;
		public static final int CONTENT_PROPERTY_COLUMN = 76;
		public static final int CONTENT_PROPEXTENT_COLUMN = 77;
		public static final int CONTENT_PROPVALUE_COLUMN = 78;
		public static final int CONTENT_PROPCOMMENT_COLUMN = 79;
		public static final int CONTENT_ISHOSTKID_COLUMN = 80;
		public static final int CONTENT_NHOSTKID_COLUMN = 81;
		public static final int CONTENT_NHOSTKIDUS_COLUMN = 82;
		public static final int CONTENT_NHOURS_COLUMN = 83;
		public static final int CONTENT_NDAYS_COLUMN = 84;
		public static final int CONTENT_DIVERT_COLUMN = 85;
		public static final int CONTENT_KIDHIJCOUNTRY_COLUMN = 86;
		public static final int CONTENT_RANSOM_COLUMN = 87;
		public static final int CONTENT_RANSOMAMT_COLUMN = 88;
		public static final int CONTENT_RANSOMAMTUS_COLUMN = 89;
		public static final int CONTENT_RANSOMPAID_COLUMN = 90;
		public static final int CONTENT_RANSOMPAIDUS_COLUMN = 91;
		public static final int CONTENT_RANSOMNOTE_COLUMN = 92;
		public static final int CONTENT_HOSTKIDOUTCOME_COLUMN = 93;
		public static final int CONTENT_NRELEASED_COLUMN = 94;
		public static final int CONTENT_ADDNOTES_COLUMN = 95;
		public static final int CONTENT_SCITE1_COLUMN = 96;
		public static final int CONTENT_SCITE2_COLUMN = 97;
		public static final int CONTENT_SCITE3_COLUMN = 98;
		public static final int CONTENT_DBSOURCE_COLUMN = 99;
		public static final String[] CONTENT_PROJECTION = new String[] { _ID, ID, DATE, APPROXDATE, EXTENDED,
				RESOLUTION, COUNTRY, REGION, PROVSTATE, CITY, VICINITY, LOCATION, LAT, LON, SUMMARY, CRIT1, CRIT2,
				CRIT3, DOUBTTERR, ALTERNATIVE, MULTIPLE, CONFLICT, SUCCESS, SUICIDE, ATTACKTYPE1, ATTACKTYPE2,
				ATTACKTYPE3, TARGTYPE1, CORP1, TARGET1, NATLTY1, TARGTYPE2, CORP2, TARGET2, NATLTY2, TARGTYPE3, CORP3,
				TARGET3, NATLTY3, GNAME, GSUBNAME, GNAME2, GSUBNAME2, GNAME3, GSUBNAME3, MOTIVE, GUNCERTAIN1,
				GUNCERTAIN2, GUNCERTAIN3, NPERPS, NPERPCAP, CLAIMED, CLAIMMODE, CLAIMCONF, CLAIM2, CLAIMMODE2,
				CLAIMCONF2, CLAIM3, CLAIMMODE3, CLAIMCONF3, COMPCLAIM, WEAPTYPE1, WEAPSUBTYPE1, WEAPTYPE2,
				WEAPSUBTYPE2, WEAPTYPE3, WEAPSUBTYPE3, WEAPTYPE4, WEAPSUBTYPE4, WEAPDETAIL, NKILL, NKILLUS, NKILLTER,
				NWOUND, NWOUNDUS, NWOUNDTER, PROPERTY, PROPEXTENT, PROPVALUE, PROPCOMMENT, ISHOSTKID, NHOSTKID,
				NHOSTKIDUS, NHOURS, NDAYS, DIVERT, KIDHIJCOUNTRY, RANSOM, RANSOMAMT, RANSOMAMTUS, RANSOMPAID,
				RANSOMPAIDUS, RANSOMNOTE, HOSTKIDOUTCOME, NRELEASED, ADDNOTES, SCITE1, SCITE2, SCITE3, DBSOURCE };
		public static final String[] CONTENT_PROJECTION2 = new String[] { _ID, ID, DATE, APPROXDATE, EXTENDED,
				RESOLUTION, COUNTRY, REGION, PROVSTATE, CITY, VICINITY, LOCATION, LAT, LON, SUMMARY, CRIT1, CRIT2,
				CRIT3 };

		static void createTable(final SQLiteDatabase db) {
			final String s = " (" + _ID + " integer primary key autoincrement, " + ID + " text, " + DATE + " text, "
					+ APPROXDATE + " text, " + EXTENDED + " text, " + RESOLUTION + " text, " + COUNTRY + " text, "
					+ REGION + " text, " + PROVSTATE + " text, " + CITY + " text, " + VICINITY + " text, " + LOCATION
					+ " text, " + LAT + " text, " + LON + " text, " + SUMMARY + " text, " + CRIT1 + " text, " + CRIT2
					+ " text, " + CRIT3 + " text, " + DOUBTTERR + " text, " + ALTERNATIVE + " text, " + MULTIPLE
					+ " text, " + CONFLICT + " text, " + SUCCESS + " text, " + SUICIDE + " text, " + ATTACKTYPE1
					+ " text, " + ATTACKTYPE2 + " text, " + ATTACKTYPE3 + " text, " + TARGTYPE1 + " text, " + CORP1
					+ " text, " + TARGET1 + " text, " + NATLTY1 + " text, " + TARGTYPE2 + " text, " + CORP2 + " text, "
					+ TARGET2 + " text, " + NATLTY2 + " text, " + TARGTYPE3 + " text, " + CORP3 + " text, " + TARGET3
					+ " text, " + NATLTY3 + " text, " + GNAME + " text, " + GSUBNAME + " text, " + GNAME2 + " text, "
					+ GSUBNAME2 + " text, " + GNAME3 + " text, " + GSUBNAME3 + " text, " + MOTIVE + " text, "
					+ GUNCERTAIN1 + " text, " + GUNCERTAIN2 + " text, " + GUNCERTAIN3 + " text, " + NPERPS
					+ " integer, " + NPERPCAP + " integer, " + CLAIMED + " text, " + CLAIMMODE + " text, " + CLAIMCONF
					+ " text, " + CLAIM2 + " text, " + CLAIMMODE2 + " text, " + CLAIMCONF2 + " text, " + CLAIM3
					+ " text, " + CLAIMMODE3 + " text, " + CLAIMCONF3 + " text, " + COMPCLAIM + " text, " + WEAPTYPE1
					+ " text, " + WEAPSUBTYPE1 + " text, " + WEAPTYPE2 + " text, " + WEAPSUBTYPE2 + " text, "
					+ WEAPTYPE3 + " text, " + WEAPSUBTYPE3 + " text, " + WEAPTYPE4 + " text, " + WEAPSUBTYPE4
					+ " text, " + WEAPDETAIL + " text, " + NKILL + " double, " + NKILLUS + " double, " + NKILLTER
					+ " double, " + NWOUND + " double, " + NWOUNDUS + " double, " + NWOUNDTER + " double, " + PROPERTY
					+ " text, " + PROPEXTENT + " text, " + PROPVALUE + " double, " + PROPCOMMENT + " text, "
					+ ISHOSTKID + " text, " + NHOSTKID + " double, " + NHOSTKIDUS + " double, " + NHOURS + " double, "
					+ NDAYS + " integer, " + DIVERT + " text, " + KIDHIJCOUNTRY + " text, " + RANSOM + " text, "
					+ RANSOMAMT + " double, " + RANSOMAMTUS + " double, " + RANSOMPAID + " double, " + RANSOMPAIDUS
					+ " double, " + RANSOMNOTE + " text, " + HOSTKIDOUTCOME + " text, " + NRELEASED + " double, "
					+ ADDNOTES + " text, " + SCITE1 + " text, " + SCITE2 + " text, " + SCITE3 + " text, " + DBSOURCE
					+ " text " + ");";

			db.execSQL("create table " + TABLE_NAME + s);

			db.execSQL(DatabaseUtil.getCreateIndexString(TABLE_NAME, ID));
		}

		static void upgradeTable(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
			try {
				db.execSQL("drop table " + TABLE_NAME);
			} catch (final SQLException e) {
			}
			createTable(db);
		}

		public static String getBulkInsertString() {
			final StringBuffer sqlRequest = new StringBuffer("INSERT INTO ");
			sqlRequest.append(TABLE_NAME);
			sqlRequest.append(" ( ");
			sqlRequest.append(ID);
			sqlRequest.append(", ");
			sqlRequest.append(DATE);
			sqlRequest.append(", ");
			sqlRequest.append(APPROXDATE);
			sqlRequest.append(", ");
			sqlRequest.append(EXTENDED);
			sqlRequest.append(", ");
			sqlRequest.append(RESOLUTION);
			sqlRequest.append(", ");
			sqlRequest.append(COUNTRY);
			sqlRequest.append(", ");
			sqlRequest.append(REGION);
			sqlRequest.append(", ");
			sqlRequest.append(PROVSTATE);
			sqlRequest.append(", ");
			sqlRequest.append(CITY);
			sqlRequest.append(", ");
			sqlRequest.append(VICINITY);
			sqlRequest.append(", ");
			sqlRequest.append(LOCATION);
			sqlRequest.append(", ");
			sqlRequest.append(LAT);
			sqlRequest.append(", ");
			sqlRequest.append(LON);
			sqlRequest.append(", ");
			sqlRequest.append(SUMMARY);
			sqlRequest.append(", ");
			sqlRequest.append(CRIT1);
			sqlRequest.append(", ");
			sqlRequest.append(CRIT2);
			sqlRequest.append(", ");
			sqlRequest.append(CRIT3);
			sqlRequest.append(", ");
			sqlRequest.append(DOUBTTERR);
			sqlRequest.append(", ");
			sqlRequest.append(ALTERNATIVE);
			sqlRequest.append(", ");
			sqlRequest.append(MULTIPLE);
			sqlRequest.append(", ");
			sqlRequest.append(CONFLICT);
			sqlRequest.append(", ");
			sqlRequest.append(SUCCESS);
			sqlRequest.append(", ");
			sqlRequest.append(SUICIDE);
			sqlRequest.append(", ");
			sqlRequest.append(ATTACKTYPE1);
			sqlRequest.append(", ");
			sqlRequest.append(ATTACKTYPE2);
			sqlRequest.append(", ");
			sqlRequest.append(ATTACKTYPE3);
			sqlRequest.append(", ");
			sqlRequest.append(TARGTYPE1);
			sqlRequest.append(", ");
			sqlRequest.append(CORP1);
			sqlRequest.append(", ");
			sqlRequest.append(TARGET1);
			sqlRequest.append(", ");
			sqlRequest.append(NATLTY1);
			sqlRequest.append(", ");
			sqlRequest.append(TARGTYPE2);
			sqlRequest.append(", ");
			sqlRequest.append(CORP2);
			sqlRequest.append(", ");
			sqlRequest.append(TARGET2);
			sqlRequest.append(", ");
			sqlRequest.append(NATLTY2);
			sqlRequest.append(", ");
			sqlRequest.append(TARGTYPE3);
			sqlRequest.append(", ");
			sqlRequest.append(CORP3);
			sqlRequest.append(", ");
			sqlRequest.append(TARGET3);
			sqlRequest.append(", ");
			sqlRequest.append(NATLTY3);
			sqlRequest.append(", ");
			sqlRequest.append(GNAME);
			sqlRequest.append(", ");
			sqlRequest.append(GSUBNAME);
			sqlRequest.append(", ");
			sqlRequest.append(GNAME2);
			sqlRequest.append(", ");
			sqlRequest.append(GSUBNAME2);
			sqlRequest.append(", ");
			sqlRequest.append(GNAME3);
			sqlRequest.append(", ");
			sqlRequest.append(GSUBNAME3);
			sqlRequest.append(", ");
			sqlRequest.append(MOTIVE);
			sqlRequest.append(", ");
			sqlRequest.append(GUNCERTAIN1);
			sqlRequest.append(", ");
			sqlRequest.append(GUNCERTAIN2);
			sqlRequest.append(", ");
			sqlRequest.append(GUNCERTAIN3);
			sqlRequest.append(", ");
			sqlRequest.append(NPERPS);
			sqlRequest.append(", ");
			sqlRequest.append(NPERPCAP);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMED);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMMODE);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMCONF);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIM2);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMMODE2);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMCONF2);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIM3);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMMODE3);
			sqlRequest.append(", ");
			sqlRequest.append(CLAIMCONF3);
			sqlRequest.append(", ");
			sqlRequest.append(COMPCLAIM);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPTYPE1);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPSUBTYPE1);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPTYPE2);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPSUBTYPE2);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPTYPE3);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPSUBTYPE3);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPTYPE4);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPSUBTYPE4);
			sqlRequest.append(", ");
			sqlRequest.append(WEAPDETAIL);
			sqlRequest.append(", ");
			sqlRequest.append(NKILL);
			sqlRequest.append(", ");
			sqlRequest.append(NKILLUS);
			sqlRequest.append(", ");
			sqlRequest.append(NKILLTER);
			sqlRequest.append(", ");
			sqlRequest.append(NWOUND);
			sqlRequest.append(", ");
			sqlRequest.append(NWOUNDUS);
			sqlRequest.append(", ");
			sqlRequest.append(NWOUNDTER);
			sqlRequest.append(", ");
			sqlRequest.append(PROPERTY);
			sqlRequest.append(", ");
			sqlRequest.append(PROPEXTENT);
			sqlRequest.append(", ");
			sqlRequest.append(PROPVALUE);
			sqlRequest.append(", ");
			sqlRequest.append(PROPCOMMENT);
			sqlRequest.append(", ");
			sqlRequest.append(ISHOSTKID);
			sqlRequest.append(", ");
			sqlRequest.append(NHOSTKID);
			sqlRequest.append(", ");
			sqlRequest.append(NHOSTKIDUS);
			sqlRequest.append(", ");
			sqlRequest.append(NHOURS);
			sqlRequest.append(", ");
			sqlRequest.append(NDAYS);
			sqlRequest.append(", ");
			sqlRequest.append(DIVERT);
			sqlRequest.append(", ");
			sqlRequest.append(KIDHIJCOUNTRY);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOM);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOMAMT);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOMAMTUS);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOMPAID);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOMPAIDUS);
			sqlRequest.append(", ");
			sqlRequest.append(RANSOMNOTE);
			sqlRequest.append(", ");
			sqlRequest.append(HOSTKIDOUTCOME);
			sqlRequest.append(", ");
			sqlRequest.append(NRELEASED);
			sqlRequest.append(", ");
			sqlRequest.append(ADDNOTES);
			sqlRequest.append(", ");
			sqlRequest.append(SCITE1);
			sqlRequest.append(", ");
			sqlRequest.append(SCITE2);
			sqlRequest.append(", ");
			sqlRequest.append(SCITE3);
			sqlRequest.append(", ");
			sqlRequest.append(DBSOURCE);
			sqlRequest.append(" ) ");
			sqlRequest
					.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			return sqlRequest.toString();
		}

		public static void bindValuesInBulkInsert(final SQLiteStatement stmt, final ContentValues values) {
			int i = 1;
			String value = values.getAsString(ID);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(DATE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(APPROXDATE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(EXTENDED);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(RESOLUTION);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(COUNTRY);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(REGION);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(PROVSTATE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CITY);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(VICINITY);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(LOCATION);
			stmt.bindString(i++, value != null ? value : "");
			// stmt.bindDouble(i++, values.getAsDouble(LAT));
			// stmt.bindDouble(i++, values.getAsDouble(LON));
			value = values.getAsString(SUMMARY);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CRIT1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CRIT2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CRIT3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(DOUBTTERR);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(ALTERNATIVE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(MULTIPLE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CONFLICT);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(SUCCESS);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(SUICIDE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(ATTACKTYPE1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(ATTACKTYPE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(ATTACKTYPE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGTYPE1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CORP1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGET1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(NATLTY1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGTYPE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CORP2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGET2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(NATLTY2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGTYPE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CORP3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(TARGET3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GNAME);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GSUBNAME);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GNAME2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GSUBNAME2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GNAME3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GSUBNAME3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(MOTIVE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GUNCERTAIN1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GUNCERTAIN2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(GUNCERTAIN3);
			stmt.bindString(i++, value != null ? value : "");
			Integer valInt = values.getAsInteger(NPERPS);
			if (valInt != null)
				stmt.bindLong(i++, valInt);
			else
				stmt.bindNull(i++);
			valInt = values.getAsInteger(NPERPCAP);
			if (valInt != null)
				stmt.bindLong(i++, valInt);
			else
				stmt.bindNull(i++);
			value = values.getAsString(CLAIMED);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMMODE);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMCONF);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIM2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMMODE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMCONF2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIM3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMMODE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(CLAIMCONF3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(COMPCLAIM);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPTYPE1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPSUBTYPE1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPTYPE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPSUBTYPE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPTYPE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPSUBTYPE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPTYPE4);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPSUBTYPE4);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(WEAPDETAIL);
			stmt.bindString(i++, value != null ? value : "");
			Double valDouble = values.getAsDouble(NKILL);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NKILLUS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NKILLTER);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NWOUND);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NWOUNDUS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NWOUNDTER);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			value = values.getAsString(PROPERTY);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(PROPEXTENT);
			stmt.bindString(i++, value != null ? value : "");
			valDouble = values.getAsDouble(PROPVALUE);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			value = values.getAsString(PROPCOMMENT);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(ISHOSTKID);
			stmt.bindString(i++, value != null ? value : "");
			valDouble = values.getAsDouble(NHOSTKID);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NHOSTKIDUS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(NHOURS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valInt = values.getAsInteger(NDAYS);
			if (valInt != null)
				stmt.bindLong(i++, valInt);
			else
				stmt.bindNull(i++);
			stmt.bindString(i++, values.getAsString(DIVERT));
			stmt.bindString(i++, values.getAsString(KIDHIJCOUNTRY));
			stmt.bindString(i++, values.getAsString(RANSOM));
			valDouble = values.getAsDouble(RANSOMAMT);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(RANSOMAMTUS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(RANSOMPAID);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			valDouble = values.getAsDouble(RANSOMPAIDUS);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			stmt.bindString(i++, values.getAsString(RANSOMNOTE));
			value = values.getAsString(HOSTKIDOUTCOME);
			stmt.bindString(i++, value != null ? value : "");
			valDouble = values.getAsDouble(NRELEASED);
			if (valDouble != null)
				stmt.bindDouble(i++, valDouble);
			else
				stmt.bindNull(i++);
			value = values.getAsString(ADDNOTES);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(SCITE1);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(SCITE2);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(SCITE3);
			stmt.bindString(i++, value != null ? value : "");
			value = values.getAsString(DBSOURCE);
			stmt.bindString(i++, value != null ? value : "");
		}

		public static ContentValues getContentValues(final Attack attack) {
			ContentValues values = new ContentValues();
			values.put(ID, attack.id);
			values.put(DATE, attack.date);
			values.put(APPROXDATE, attack.approxdate);
			values.put(EXTENDED, attack.extended);
			values.put(RESOLUTION, attack.resolution);
			values.put(COUNTRY, attack.country);
			values.put(REGION, attack.region);
			values.put(PROVSTATE, attack.provstate);
			values.put(CITY, attack.city);
			values.put(VICINITY, attack.vicinity);
			values.put(LOCATION, attack.location);
			// values.put(LAT, attack.lat);
			// values.put(LON, attack.lon);
			values.put(SUMMARY, attack.summary);
			values.put(CRIT1, attack.crit1);
			values.put(CRIT2, attack.crit2);
			values.put(CRIT3, attack.crit3);
			values.put(DOUBTTERR, attack.doubtterr);
			values.put(ALTERNATIVE, attack.alternative);
			values.put(MULTIPLE, attack.multiple);
			values.put(CONFLICT, attack.conflict);
			values.put(SUCCESS, attack.success);
			values.put(SUICIDE, attack.suicide);
			values.put(ATTACKTYPE1, attack.attacktype1);
			values.put(ATTACKTYPE2, attack.attacktype2);
			values.put(ATTACKTYPE3, attack.attacktype3);
			values.put(TARGTYPE1, attack.targtype1);
			values.put(CORP1, attack.corp1);
			values.put(TARGET1, attack.target1);
			values.put(NATLTY1, attack.natlty1);
			values.put(TARGTYPE2, attack.targtype2);
			values.put(CORP2, attack.corp2);
			values.put(TARGET2, attack.target2);
			values.put(NATLTY2, attack.natlty2);
			values.put(TARGTYPE3, attack.targtype3);
			values.put(CORP3, attack.corp3);
			values.put(TARGET3, attack.target3);
			values.put(NATLTY3, attack.natlty3);
			values.put(GNAME, attack.gname);
			values.put(GSUBNAME, attack.gsubname);
			values.put(GNAME2, attack.gname2);
			values.put(GSUBNAME2, attack.gsubname2);
			values.put(GNAME3, attack.gname3);
			values.put(GSUBNAME3, attack.gsubname3);
			values.put(MOTIVE, attack.motive);
			values.put(GUNCERTAIN1, attack.guncertain1);
			values.put(GUNCERTAIN2, attack.guncertain2);
			values.put(GUNCERTAIN3, attack.guncertain3);
			values.put(NPERPS, attack.nperps);
			values.put(NPERPCAP, attack.nperpcap);
			values.put(CLAIMED, attack.claimed);
			values.put(CLAIMMODE, attack.claimmode);
			values.put(CLAIMCONF, attack.claimconf);
			values.put(CLAIM2, attack.claim2);
			values.put(CLAIMMODE2, attack.claimmode2);
			values.put(CLAIMCONF2, attack.claimconf2);
			values.put(CLAIM3, attack.claim3);
			values.put(CLAIMMODE3, attack.claimmode3);
			values.put(CLAIMCONF3, attack.claimconf3);
			values.put(COMPCLAIM, attack.compclaim);
			values.put(WEAPTYPE1, attack.weaptype1);
			values.put(WEAPSUBTYPE1, attack.weapsubtype1);
			values.put(WEAPTYPE2, attack.weaptype2);
			values.put(WEAPSUBTYPE2, attack.weapsubtype2);
			values.put(WEAPTYPE3, attack.weaptype3);
			values.put(WEAPSUBTYPE3, attack.weapsubtype3);
			values.put(WEAPTYPE4, attack.weaptype4);
			values.put(WEAPSUBTYPE4, attack.weapsubtype4);
			values.put(WEAPDETAIL, attack.weapdetail);
			values.put(NKILL, attack.nkill);
			values.put(NKILLUS, attack.nkillus);
			values.put(NKILLTER, attack.nkillter);
			values.put(NWOUND, attack.nwound);
			values.put(NWOUNDUS, attack.nwoundus);
			values.put(NWOUNDTER, attack.nwoundter);
			values.put(PROPERTY, attack.property);
			values.put(PROPEXTENT, attack.propextent);
			values.put(PROPVALUE, attack.propvalue);
			values.put(PROPCOMMENT, attack.propcomment);
			values.put(ISHOSTKID, attack.ishostkid);
			values.put(NHOSTKID, attack.nhostkid);
			values.put(NHOSTKIDUS, attack.nhostkidus);
			values.put(NHOURS, attack.nhours);
			values.put(NDAYS, attack.ndays);
			values.put(DIVERT, attack.divert);
			values.put(KIDHIJCOUNTRY, attack.kidhijcountry);
			values.put(RANSOM, attack.ransom);
			values.put(RANSOMAMT, attack.ransomamt);
			values.put(RANSOMAMTUS, attack.ransomamtus);
			values.put(RANSOMPAID, attack.ransompaid);
			values.put(RANSOMPAIDUS, attack.ransompaidus);
			values.put(RANSOMNOTE, attack.ransomnote);
			values.put(HOSTKIDOUTCOME, attack.hostkidoutcome);
			values.put(NRELEASED, attack.nreleased);
			values.put(ADDNOTES, attack.addnotes);
			values.put(SCITE1, attack.scite1);
			values.put(SCITE2, attack.scite2);
			values.put(SCITE3, attack.scite3);
			values.put(DBSOURCE, attack.dbsource);
			return values;
		}
	}
}
