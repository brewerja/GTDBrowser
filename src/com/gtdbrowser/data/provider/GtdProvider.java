/*
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 *
 * Licensed under the Beerware License :
 * 
 *   As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think
 *   this stuff is worth it, you can buy me a beer in return
 */
package com.gtdbrowser.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.gtdbrowser.config.LogConfig;
import com.gtdbrowser.data.provider.GtdContent.AttackDao;
import com.gtdbrowser.data.provider.FilteredListDao;

public class GtdProvider extends ContentProvider {

	private static final String LOG_TAG = GtdProvider.class.getSimpleName();

	protected static final String DATABASE_NAME = "DataManagerPoC.db";

	// Any changes to the database format *must* include update-in-place code.
	// Original version: 1
	public static final int DATABASE_VERSION = 1;

	public static final String AUTHORITY = "com.gtdbrowser.data.provider.PoCProvider";

	public static final Uri INTEGRITY_CHECK_URI = Uri.parse("content://" + AUTHORITY + "/integrityCheck");

	private static final int ATTACK_BASE = 0;
	private static final int ATTACK = ATTACK_BASE;
	private static final int ATTACK_ID = ATTACK_BASE + 1;

	private static final int REGION_BASE = 0x1000;
	private static final int REGION = REGION_BASE;

	private static final int COUNTRY_BASE = 0x2000;
	private static final int COUNTRY = COUNTRY_BASE;

	private static final int ATTACKTYPE_BASE = 0x3000;
	private static final int ATTACKTYPE = ATTACKTYPE_BASE;

	private static final int BASE_SHIFT = 12; // DO NOT TOUCH !
	// 12 bits to the base type: 0, 0x1000, 0x2000, etc.

	private static final String[] TABLE_NAMES = { AttackDao.TABLE_NAME, "region", "country", "attacktype" };

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		final UriMatcher matcher = sURIMatcher;

		// All regions
		matcher.addURI(AUTHORITY, "region", REGION);
		// All countries
		matcher.addURI(AUTHORITY, "country", COUNTRY);
		// All attack types
		matcher.addURI(AUTHORITY, "attacktype", ATTACKTYPE);
		// All attacks
		matcher.addURI(AUTHORITY, AttackDao.TABLE_NAME, ATTACK);
		// A specific attack
		matcher.addURI(AUTHORITY, AttackDao.TABLE_NAME + "/#", ATTACK_ID);

	}

	private SQLiteDatabase mDatabase;

	public synchronized SQLiteDatabase getDatabase(final Context context) {
		// Always return the cached database, if we've got one
		if (mDatabase != null) {
			return mDatabase;
		}

		final DatabaseHelper helper = new DatabaseHelper(context, DATABASE_NAME);
		mDatabase = helper.getWritableDatabase();
		if (mDatabase != null) {
			mDatabase.setLockingEnabled(true);
		}

		return mDatabase;
	}

	static SQLiteDatabase getReadableDatabase(final Context context) {
		final DatabaseHelper helper = new GtdProvider().new DatabaseHelper(context, DATABASE_NAME);
		return helper.getReadableDatabase();
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(final Context context, final String name) {
			super(context, name, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(final SQLiteDatabase db) {
			Log.d(LOG_TAG, "Creating databases");

			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createRegionTable start");
			FilteredListDao.createTable(db, "region");
			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createRegionTable end");

			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createCountryTable start");
			FilteredListDao.createTable(db, "country");
			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createCountryTable end");

			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createAttackTypeTable start");
			FilteredListDao.createTable(db, "attacktype");
			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createAttackTypeTable end");

			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createAttackTable start");
			AttackDao.createTable(db);
			if (LogConfig.DDP_DEBUG_LOGS_ENABLED)
				Log.d(LOG_TAG, "PoCProvider | createAttackTable end");
		}

		@Override
		public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		}

		@Override
		public void onOpen(final SQLiteDatabase db) {
		}
	}

	@Override
	public int delete(final Uri uri, final String selection, final String[] selectionArgs) {

		final int match = sURIMatcher.match(uri);
		final Context context = getContext();

		// Pick the correct database for this operation
		final SQLiteDatabase db = getDatabase(context);
		final int table = match >> BASE_SHIFT;
		String id = "0";

		if (LogConfig.DDP_DEBUG_LOGS_ENABLED) {
			Log.d(LOG_TAG, "delete: uri=" + uri + ", match is " + match);
		}

		int result = -1;

		switch (match) {
		case ATTACK_ID:
			id = uri.getPathSegments().get(1);
			result = db.delete(TABLE_NAMES[table], whereWithId(id, selection), selectionArgs);
			break;
		case REGION:
		case COUNTRY:
		case ATTACKTYPE:
		case ATTACK:
			result = db.delete(TABLE_NAMES[table], selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public String getType(final Uri uri) {
		final int match = sURIMatcher.match(uri);
		switch (match) {
		case REGION:
		case COUNTRY:
		case ATTACKTYPE:
			return FilteredListDao.TYPE_DIR_TYPE;
		case ATTACK_ID:
			return AttackDao.TYPE_ELEM_TYPE;
		case ATTACK:
			return AttackDao.TYPE_DIR_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(final Uri uri, final ContentValues values) {

		final int match = sURIMatcher.match(uri);
		final Context context = getContext();

		// Pick the correct database for this operation
		final SQLiteDatabase db = getDatabase(context);
		final int table = match >> BASE_SHIFT;
		long id;

		if (LogConfig.DDP_DEBUG_LOGS_ENABLED) {
			Log.d(LOG_TAG, "insert: uri=" + uri + ", match is " + match);
		}

		Uri resultUri = null;

		switch (match) {
		case REGION:
		case COUNTRY:
		case ATTACKTYPE:
		case ATTACK:
			id = db.insert(TABLE_NAMES[table], "foo", values);
			resultUri = ContentUris.withAppendedId(uri, id);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Notify with the base uri, not the new uri (nobody is watching a new
		// record)
		getContext().getContentResolver().notifyChange(uri, null);
		return resultUri;
	}

	@Override
	public int bulkInsert(final Uri uri, final ContentValues[] values) {

		final int match = sURIMatcher.match(uri);
		final Context context = getContext();

		// Pick the correct database for this operation
		final SQLiteDatabase db = getDatabase(context);

		if (LogConfig.DDP_DEBUG_LOGS_ENABLED) {
			Log.d(LOG_TAG, "bulkInsert: uri=" + uri + ", match is " + match);
		}

		int numberInserted = 0;
		SQLiteStatement insertStmt;

		db.beginTransaction();
		try {
			switch (match) {
			case REGION:
				// TODO: CAN WE ELIMINATE THE "region" string for the REGION
				// number?
				insertStmt = db.compileStatement(FilteredListDao.getBulkInsertString("region"));
				for (final ContentValues value : values) {
					FilteredListDao.bindValuesInBulkInsert(insertStmt, value);
					insertStmt.execute();
					insertStmt.clearBindings();
				}
				insertStmt.close();
				db.setTransactionSuccessful();
				numberInserted = values.length;
				break;
			case COUNTRY:
				insertStmt = db.compileStatement(FilteredListDao.getBulkInsertString("country"));
				for (final ContentValues value : values) {
					FilteredListDao.bindValuesInBulkInsert(insertStmt, value);
					insertStmt.execute();
					insertStmt.clearBindings();
				}
				insertStmt.close();
				db.setTransactionSuccessful();
				numberInserted = values.length;
				break;
			case ATTACKTYPE:
				insertStmt = db.compileStatement(FilteredListDao.getBulkInsertString("attacktype"));
				for (final ContentValues value : values) {
					FilteredListDao.bindValuesInBulkInsert(insertStmt, value);
					insertStmt.execute();
					insertStmt.clearBindings();
				}
				insertStmt.close();
				db.setTransactionSuccessful();
				numberInserted = values.length;
				break;
			case ATTACK:
				insertStmt = db.compileStatement(AttackDao.getBulkInsertString());
				for (final ContentValues value : values) {
					AttackDao.bindValuesInBulkInsert(insertStmt, value);
					insertStmt.execute();
					insertStmt.clearBindings();
				}
				insertStmt.close();
				db.setTransactionSuccessful();
				numberInserted = values.length;
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
			}

		} finally {
			db.endTransaction();
		}

		// Notify with the base uri, not the new uri (nobody is watching a new
		// record)
		context.getContentResolver().notifyChange(uri, null);
		return numberInserted;
	}

	@Override
	public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs,
			final String sortOrder) {

		Cursor c = null;
		final Uri notificationUri = GtdContent.CONTENT_URI;
		final int match = sURIMatcher.match(uri);
		final Context context = getContext();
		// Pick the correct database for this operation
		final SQLiteDatabase db = getDatabase(context);
		final int table = match >> BASE_SHIFT;
		String id;

		if (LogConfig.DDP_DEBUG_LOGS_ENABLED) {
			Log.d(LOG_TAG, "query: uri=" + uri + ", match is " + match);
		}

		switch (match) {
		case ATTACK_ID:
			id = uri.getPathSegments().get(1);
			c = db.query(TABLE_NAMES[table], projection, whereWithId(id, selection), selectionArgs, null, null,
					sortOrder);
			break;
		case REGION:
		case COUNTRY:
		case ATTACKTYPE:
		case ATTACK:
			c = db.query(TABLE_NAMES[table], projection, selection, selectionArgs, null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if ((c != null) && !isTemporary()) {
			c.setNotificationUri(getContext().getContentResolver(), notificationUri);
		}
		return c;
	}

	private String whereWithId(final String id, final String selection) {
		final StringBuilder sb = new StringBuilder(256);
		sb.append(BaseColumns._ID);
		sb.append(" = ");
		sb.append(id);
		if (selection != null) {
			sb.append(" AND (");
			sb.append(selection);
			sb.append(')');
		}
		return sb.toString();
	}

	@Override
	public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
		final int match = sURIMatcher.match(uri);
		final Context context = getContext();
		// Pick the correct database for this operation
		final SQLiteDatabase db = getDatabase(context);
		final int table = match >> BASE_SHIFT;
		int result;

		if (LogConfig.DDP_DEBUG_LOGS_ENABLED) {
			Log.d(LOG_TAG, "update: uri=" + uri + ", match is " + match);
		}

		switch (match) {
		case ATTACK:
		case REGION:
		case COUNTRY:
		case ATTACKTYPE:
			result = db.update(TABLE_NAMES[table], values, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public boolean onCreate() {
		return true;
	}
}
