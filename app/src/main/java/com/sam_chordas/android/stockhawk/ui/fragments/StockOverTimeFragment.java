package com.sam_chordas.android.stockhawk.ui.fragments;



import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the {@link StockOverTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockOverTimeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    static String uri = null;
    static int pos = 0;
    TextView tv_eps_current_year, tv_eps_next_year, tv_eps_next_quarter, tv_day_low, tv_day_high,
    tv_year_high, tv_year_low;
    private TextView symbol;
    private Cursor mCursor;
    private static final int CURSOR_LOADER_ID = 0;
    private com.db.chart.view.LineChartView linechart;

    public StockOverTimeFragment() {
        // Required empty public constructor
    }


    public static StockOverTimeFragment newInstance(int param2) {
        StockOverTimeFragment fragment = new StockOverTimeFragment();
        pos = param2;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_line_graph, container, false);

         linechart = (com.db.chart.view.LineChartView) view.findViewById(R.id.linechart);

        /*symbol = (TextView) view.findViewById(R.id.symbol);
        tv_eps_current_year = (TextView) view.findViewById(R.id.tv_eps_current_year);
        tv_eps_next_year = (TextView) view.findViewById(R.id.tv_eps_next_year);
        tv_eps_next_quarter = (TextView) view.findViewById(R.id.tv_eps_next_quarter);
        tv_day_low = (TextView) view.findViewById(R.id.tv_day_low);
        tv_day_high = (TextView) view.findViewById(R.id.tv_day_high);
        tv_year_high = (TextView) view.findViewById(R.id.tv_year_high);
        tv_year_low = (TextView) view.findViewById(R.id.tv_year_low);
*/


        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        // This narrows the return to only the stocks that are most current.
        return new CursorLoader(getActivity(), QuoteProvider.Quotes.CONTENT_URI,
                new String[]{ QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP, QuoteColumns.EPSESTIMATECURRENTYEAR,
                        QuoteColumns.EPSESTIMATENEXTYEAR, QuoteColumns.EPSESTIMATENEXTQUARTER, QuoteColumns.DAYLOW, QuoteColumns.DAYHIGH,
                        QuoteColumns.YEARLOW, QuoteColumns.YEARHIGH},
                QuoteColumns.ISCURRENT + " = ?",
                new String[]{"1"},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){

        mCursor = data;
        mCursor.moveToPosition(pos);

        //symbol.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL)));
        /*tv_eps_current_year.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATECURRENTYEAR)));
        tv_eps_next_year.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTYEAR)));
        tv_eps_next_quarter.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTQUARTER)));
        tv_day_low.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYLOW)));
        tv_day_high.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYHIGH)));
        tv_year_low.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEARLOW)));
        tv_year_high.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEARHIGH)));*/




     final String[] mLabels= {getString(R.string.eps_estimate_current_year), getString(R.string.eps_estimate_next_year), getString(R.string.eps_estimate_next_quarter), getString(R.string.day_low), getString(R.string.day_high),getString(R.string.year_low),getString(R.string.year_high)};
       final float[] mValues = {Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATECURRENTYEAR))), Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTYEAR))), Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTQUARTER))),
               Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYLOW))),Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYHIGH))),Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEARLOW))),Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEARHIGH))),};
        LineSet dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#ffffff"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffffff"))
                .setThickness(1)
                .setDashed(new float[]{1f,1f})
                .beginAt(0);
        linechart.addData(dataset);

        linechart.show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){

    }



}
