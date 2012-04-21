package com.gtdbrowser.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.gtdbrowser.R;
import com.gtdbrowser.config.DialogConfig;
import com.gtdbrowser.data.provider.GtdContent;
import com.gtdbrowser.data.provider.FilteredListDao;
import com.gtdbrowser.data.requestmanager.GtdRequestManager;
import com.gtdbrowser.data.requestmanager.GtdRequestManager.OnRequestFinishedListener;
import com.gtdbrowser.data.service.GtdService;
import com.gtdbrowser.util.NotifyingAsyncQueryHandler;
import com.gtdbrowser.util.NotifyingAsyncQueryHandler.AsyncQueryListener;

public class FilteredListActivity extends ListActivity implements OnRequestFinishedListener, AsyncQueryListener,
		OnClickListener, OnScrollListener {

	public static final String PREFS_NAME = "FilteredListActivityPrefs";
	public static final String END_PAGINATION = "end";

	private static final String SAVED_STATE_REQUEST_ID = "savedStateRequestId";
	private static final String SAVED_STATE_ERROR_TITLE = "savedStateErrorTitle";
	private static final String SAVED_STATE_ERROR_MESSAGE = "savedStateErrorMessage";
	private static final String SAVED_STATE_URI = "savedStateUri";

	@SuppressWarnings("unused")
	private Spinner mFilterSpinner;
	private Button mButtonLoad;
	private Button mButtonClearDb;

	private GtdRequestManager mRequestManager;
	private int mRequestId = -1;
	private int priorFirst = -1;
	private String ws_uri;

	private NotifyingAsyncQueryHandler mQueryHandler;

	private LayoutInflater mInflater;

	private String mErrorDialogTitle;
	private String mErrorDialogMessage;

	private String filterType;
	private String filterDefaultUri;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		// Unpack the intent to get what the filtering will be.
		Intent intent = getIntent();
		filterType = intent.getStringExtra("filterType");
		filterDefaultUri = intent.getStringExtra("filterDefaultUri");

		// Retrieve the current web service URI for this filter type.
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		ws_uri = settings.getString(filterType + "_uri", filterDefaultUri);

		setContentView(R.layout.filtered_list);
		bindViews();

		if (savedInstanceState != null) {
			mRequestId = savedInstanceState.getInt(SAVED_STATE_REQUEST_ID, -1);
			mErrorDialogTitle = savedInstanceState.getString(SAVED_STATE_ERROR_TITLE);
			mErrorDialogMessage = savedInstanceState.getString(SAVED_STATE_ERROR_MESSAGE);
			ws_uri = savedInstanceState.getString(SAVED_STATE_URI);
		}

		// Get the request manager (for web service queries) and query handler
		// (for database queries), then fire off a database query to populate
		// the view.
		mRequestManager = GtdRequestManager.from(this);
		mInflater = getLayoutInflater();
		mQueryHandler = new NotifyingAsyncQueryHandler(getContentResolver(), this);
		mQueryHandler.startQuery(Uri.parse(GtdContent.CONTENT_URI + "/" + filterType),
				FilteredListDao.CONTENT_PROJECTION, FilteredListDao.NUM_ATTACKS_ORDER_BY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mRequestId != -1) {
			if (mRequestManager.isRequestInProgress(mRequestId)) {
				mRequestManager.addOnRequestFinishedListener(this);
				setProgressBarIndeterminateVisibility(true);
			} else {
				mRequestId = -1;

				// Get the number of rows in the database
				int number = ((FilteredListAdapter) getListAdapter()).getCursor().getCount();

				if (number < 1) {
					// In this case, we don't have a way to know if the request
					// was correctly executed with 0 result or if an error
					// occurred. Here I choose to display an error but it's up
					// to you
					showDialog(DialogConfig.DIALOG_CONNEXION_ERROR);
				}
				// Nothing to do if it works as the cursor is automatically
				// updated
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mRequestId != -1) {
			mRequestManager.removeOnRequestFinishedListener(this);
		}
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		outState.putInt(SAVED_STATE_REQUEST_ID, mRequestId);
		outState.putString(SAVED_STATE_ERROR_TITLE, mErrorDialogTitle);
		outState.putString(SAVED_STATE_ERROR_MESSAGE, mErrorDialogMessage);
		outState.putString(SAVED_STATE_URI, ws_uri);
		super.onSaveInstanceState(outState);
	}

	private void bindViews() {
		mFilterSpinner = (Spinner) findViewById(R.id.sp_filter);

		mButtonLoad = (Button) findViewById(R.id.b_load);
		mButtonLoad.setOnClickListener(this);

		mButtonClearDb = (Button) findViewById(R.id.b_clear_db);
		mButtonClearDb.setOnClickListener(this);

		getListView().setOnScrollListener(this);
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		Builder b;
		switch (id) {
		case DialogConfig.DIALOG_ERROR:
			b = new Builder(this);
			b.setTitle(mErrorDialogTitle);
			b.setMessage(mErrorDialogMessage);
			b.setCancelable(true);
			b.setNeutralButton(android.R.string.ok, null);
			return b.create();
		case DialogConfig.DIALOG_CONNEXION_ERROR:
			b = new Builder(this);
			b.setCancelable(true);
			b.setNeutralButton(getString(android.R.string.ok), null);
			b.setPositiveButton(getString(R.string.dialog_button_retry), new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int which) {
					callRegionListWS();
				}
			});
			b.setTitle(R.string.dialog_error_connexion_error_title);
			b.setMessage(R.string.dialog_error_connexion_error_message);
			return b.create();
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected void onPrepareDialog(final int id, final Dialog dialog) {
		switch (id) {
		case DialogConfig.DIALOG_ERROR:
			dialog.setTitle(mErrorDialogTitle);
			((AlertDialog) dialog).setMessage(mErrorDialogMessage);
			break;
		default:
			super.onPrepareDialog(id, dialog);
			break;
		}
	}

	private void callRegionListWS() {
		if (!ws_uri.equals(END_PAGINATION)) {
			setProgressBarIndeterminateVisibility(true);
			mRequestManager.addOnRequestFinishedListener(this);
			mRequestId = mRequestManager.getObjectList(ws_uri);
		}
	}

	@Override
	public void onClick(final View view) {
		if (view == mButtonLoad) {
			callRegionListWS();
		} else if (view == mButtonClearDb) {
			priorFirst = -1;
			ws_uri = filterDefaultUri;
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(filterType + "_uri", ws_uri);
			editor.commit();
			mQueryHandler.startDelete(Uri.parse(FilteredListDao.CONTENT_URI + "/" + filterType));
		}
	}

	@Override
	public void onRequestFinished(final int requestId, final int resultCode, final Bundle payload) {
		if (requestId == mRequestId) {
			setProgressBarIndeterminateVisibility(false);
			mRequestId = -1;
			mRequestManager.removeOnRequestFinishedListener(this);
			if (resultCode == GtdService.ERROR_CODE) {
				if (payload != null) {
					final int errorType = payload.getInt(GtdRequestManager.RECEIVER_EXTRA_ERROR_TYPE, -1);
					if (errorType == GtdRequestManager.RECEIVER_EXTRA_VALUE_ERROR_TYPE_DATA) {
						mErrorDialogTitle = getString(R.string.dialog_error_data_error_title);
						mErrorDialogMessage = getString(R.string.dialog_error_data_error_message);
						showDialog(DialogConfig.DIALOG_ERROR);
					} else {
						showDialog(DialogConfig.DIALOG_CONNEXION_ERROR);
					}
				} else {
					showDialog(DialogConfig.DIALOG_CONNEXION_ERROR);
				}
			}
			// Update the web service URI if there are more pages, otherwise
			// call it the end of pagination on this filter type.
			if (payload.getString("nextURI") != null)
				ws_uri = payload.getString("nextURI");
			else
				ws_uri = END_PAGINATION;
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(filterType + "_uri", ws_uri);
			editor.commit();
			// Nothing else to do if it works as the cursor is automatically
			// updated
		}
	}

	@Override
	public void onQueryComplete(final int token, final Object cookie, final Cursor cursor) {
		FilteredListAdapter adapter = (FilteredListAdapter) getListAdapter();
		if (adapter == null) {
			adapter = new FilteredListAdapter(this, cursor);
			setListAdapter(adapter);
		} else {
			adapter.changeCursor(cursor);
		}
	}

	class ViewHolder {
		private TextView mTextViewName;
		private TextView mTextViewId;
		private TextView mTextViewNumAttacks;

		public ViewHolder(final View view) {
			mTextViewName = (TextView) view.findViewById(R.id.tv_name);
			mTextViewId = (TextView) view.findViewById(R.id.tv_id);
			mTextViewNumAttacks = (TextView) view.findViewById(R.id.tv_num_attacks);
		}

		public void populateView(final Cursor c) {
			mTextViewName.setText(String.valueOf(c.getString(FilteredListDao.CONTENT_NAME_COLUMN)));
			mTextViewId.setText(String.valueOf(c.getInt(FilteredListDao.CONTENT_OBJECT_ID_COLUMN)));
			mTextViewNumAttacks.setText(String.valueOf(c.getInt(FilteredListDao.CONTENT_NUM_ATTACKS_COLUMN)));
		}
	}

	class FilteredListAdapter extends CursorAdapter {

		public FilteredListAdapter(final Context context, final Cursor c) {
			super(context, c);
		}

		@Override
		public void bindView(final View view, final Context context, final Cursor cursor) {
			((ViewHolder) view.getTag()).populateView(cursor);
		}

		@Override
		public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
			View view = mInflater.inflate(R.layout.filtered_list_item, null);
			view.setTag(new ViewHolder(view));
			return view;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// If scrolled to within 5 of the current list end, go ahead and try to
		// get more pages of data.
		if (visibleItemCount < totalItemCount && (firstVisibleItem + visibleItemCount >= totalItemCount - 5)) {
			if (firstVisibleItem != priorFirst) {
				priorFirst = firstVisibleItem;
				callRegionListWS();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
}
