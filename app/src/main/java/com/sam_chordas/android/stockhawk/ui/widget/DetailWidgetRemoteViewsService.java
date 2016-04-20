package com.sam_chordas.android.stockhawk.ui.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.lang.annotation.Target;
import java.util.concurrent.ExecutionException;

/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = DetailWidgetRemoteViewsService.class.getSimpleName();



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission

                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                        new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                                QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP, QuoteColumns.EPSESTIMATECURRENTYEAR,
                                QuoteColumns.EPSESTIMATENEXTYEAR, QuoteColumns.EPSESTIMATENEXTQUARTER, QuoteColumns.DAYLOW, QuoteColumns.DAYHIGH,
                                QuoteColumns.YEARLOW, QuoteColumns.YEARHIGH},
                        QuoteColumns.ISCURRENT + " = ?",
                        new String[]{"1"},
                        null);
                Binder.restoreCallingIdentity(identityToken);

                Log.e("count",""+ data.getCount());

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {

                Log.e("count",""+ data.getCount());
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.list_item_quote);

                Log.e("data", data.getString(data.getColumnIndex(QuoteColumns.SYMBOL)));
                views.setTextViewText(R.id.stock_symbol, data.getString(data.getColumnIndex(QuoteColumns.SYMBOL)));
                views.setTextViewText(R.id.bid_price, data.getString(data.getColumnIndex(QuoteColumns.BIDPRICE)));

                if (data.getInt(data.getColumnIndex("is_up")) == 1){


                        views.setInt(R.id.change, "setBackgroundResource",R.drawable.percent_change_pill_green);
                    }
             else{
                    views.setInt(R.id.change, "setBackgroundResource",R.drawable.percent_change_pill_red);

                }

                views.setTextViewText(R.id.change,data.getString(data.getColumnIndex("percent_change")));

                Intent fillInIntent = new Intent();
                fillInIntent.putExtra("pos", position);
                // Make it possible to distinguish the individual on-click
                // action of a given item
                views.setOnClickFillInIntent(R.id.main_layout, fillInIntent);

                return views;
            }



            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.list_item_quote);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(data.getColumnIndex(QuoteColumns._ID));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
