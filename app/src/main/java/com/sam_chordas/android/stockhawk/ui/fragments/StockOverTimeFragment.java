package com.sam_chordas.android.stockhawk.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the {@link StockOverTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockOverTimeFragment extends Fragment {
    static Cursor cursor = null;
    static int pos = 0;
    TextView tv_eps_current_year, tv_eps_next_year, tv_eps_next_quarter, tv_day_low, tv_day_high,
    tv_year_high, tv_year_low;
    private TextView symbol;


    public StockOverTimeFragment() {
        // Required empty public constructor
    }


    public static StockOverTimeFragment newInstance(Cursor param1, int param2) {
        StockOverTimeFragment fragment = new StockOverTimeFragment();
        cursor = param1;
        pos = param2;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_stock_over_time, container, false);
        cursor.moveToPosition(pos);

        symbol = (TextView) view.findViewById(R.id.symbol);
        tv_eps_current_year = (TextView) view.findViewById(R.id.tv_eps_current_year);
        tv_eps_next_year = (TextView) view.findViewById(R.id.tv_eps_next_year);
        tv_eps_next_quarter = (TextView) view.findViewById(R.id.tv_eps_next_quarter);
        tv_day_low = (TextView) view.findViewById(R.id.tv_day_low);
        tv_day_high = (TextView) view.findViewById(R.id.tv_day_high);
        tv_year_high = (TextView) view.findViewById(R.id.tv_year_high);
        tv_year_low = (TextView) view.findViewById(R.id.tv_year_low);

        symbol.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.SYMBOL)));
        tv_eps_current_year.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.EPSESTIMATECURRENTYEAR)));
        tv_eps_next_year.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTYEAR)));
        tv_eps_next_quarter.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.EPSESTIMATENEXTQUARTER)));
        tv_day_low.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.DAYLOW)));
        tv_day_high.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.DAYHIGH)));
        tv_year_low.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.YEARLOW)));
        tv_year_high.setText(cursor.getString(cursor.getColumnIndex(QuoteColumns.YEARHIGH)));

        return view;
    }



}
