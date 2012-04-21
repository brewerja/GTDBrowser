package com.gtdbrowser.data.provider;

import java.util.HashMap;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;

import com.foxykeep.datadroid.provider.util.DatabaseUtil;
import com.gtdbrowser.config.WSConfig;
import com.gtdbrowser.data.model.FilteredListModel;

public class FilteredListDao extends GtdContent implements BaseColumns {

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String NUM_ATTACKS = "num_attacks";

	public static final Uri CONTENT_URI_BASE = Uri.parse(GtdContent.CONTENT_URI + "/");
	public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/com.gtdbrowser.data.provider.region";
	public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/com.gtdbrowser.data.provider.region";

	public static final String MIN_ID_SELECTION = ID + " >= ?";
	public static final String NUM_ATTACKS_ORDER_BY = NUM_ATTACKS + " DESC";

	public static final int CONTENT_ID_COLUMN = 0;
	public static final int CONTENT_NAME_COLUMN = 1;
	public static final int CONTENT_OBJECT_ID_COLUMN = 2;
	public static final int CONTENT_NUM_ATTACKS_COLUMN = 3;
	public static final String[] CONTENT_PROJECTION = new String[] { _ID, NAME, ID, NUM_ATTACKS };

	public static void createTable(final SQLiteDatabase db, String table_name) {
		final String s = " (" + _ID + " integer primary key autoincrement, " + NAME + " text, " + ID + " integer, "
				+ NUM_ATTACKS + " integer " + ");";

		db.execSQL("create table " + table_name + s);

		db.execSQL(DatabaseUtil.getCreateIndexString(table_name, NAME));
	}

	public static void upgradeTable(final SQLiteDatabase db, String table_name, final int oldVersion,
			final int newVersion) {
		try {
			db.execSQL("drop table " + table_name);
		} catch (final SQLException e) {
		}
		createTable(db, table_name);
	}

	public static String getBulkInsertString(String table_name) {
		final StringBuffer sqlRequest = new StringBuffer("INSERT INTO ");
		sqlRequest.append(table_name);
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
		String value = values.getAsString(NAME);
		stmt.bindString(CONTENT_NAME_COLUMN, value != null ? value : "");
		stmt.bindLong(CONTENT_OBJECT_ID_COLUMN, values.getAsInteger(ID));
		stmt.bindLong(CONTENT_NUM_ATTACKS_COLUMN, values.getAsInteger(NUM_ATTACKS));
	}

	public static ContentValues getContentValues(final FilteredListModel model) {
		ContentValues values = new ContentValues();
		values.put(NAME, model.name);
		values.put(ID, model.id);
		values.put(NUM_ATTACKS, model.num_attacks);
		return values;
	}

	public static HashMap<String, String> map;
	static {
		map = new HashMap<String, String>();
		map.put("regions", "region");
		map.put("countries", "country");
		map.put("attacktypes", "attacktype");
	}

	public static String getTableNameFromUri(String ws_uri) {
		// TODO: Little bit of a hack.
		String tableName = map.get(ws_uri.split(WSConfig.SERVER_URI + WSConfig.API_PATH)[1].split("/")[1]);
		return tableName;
	}

}