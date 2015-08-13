package com.suh.itboy.auctionsystem.Fragments.Dashboard;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Provider.ProductProvider;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.App;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private CursorAdapter cursorAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewPager homeViewPager;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
/*        ViewPager viewPager = (ViewPager) view.findViewById(R.id.slider_viewpager);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getChildFragmentManager());
        viewPager.setAdapter(imageSliderAdapter);
        homeViewPager = (ViewPager) getActivity().findViewById(R.id.home_viewpager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                homeViewPager.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

        GridView productsGrid = (GridView) view.findViewById(R.id.products);

//        Cursor cursor = getActivity().getContentResolver().query(ProductProvider.CONTENT_URI, null, null, null, ProductDBAdapter.COLUMN_CREATED + " DESC");

        String[] from = {ProductDBAdapter.COLUMN_TITLE};
        int[] to = {R.id.title};
        cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.product_grid, null, from, to, 1);

        //productsGrid.setAdapter(new ProductGridAdapter(getActivity()));
        productsGrid.setAdapter(cursorAdapter);

        productsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App.ShowMsg(view, "item selected at " + position, null, null);
            }
        });

        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(getActivity(), ProductProvider.CONTENT_URI, null, null, null,
                        ProductDBAdapter.COLUMN_CREATED + " DESC");
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                cursorAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                cursorAdapter.swapCursor(null);
            }
        });
        return view;
    }
}
