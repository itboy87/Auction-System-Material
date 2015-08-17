package com.suh.itboy.auctionsystem.Fragments.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.suh.itboy.auctionsystem.Activities.DashboardActivity;
import com.suh.itboy.auctionsystem.Activities.ProductEditorActivity;
import com.suh.itboy.auctionsystem.Provider.ProductProvider;
import com.suh.itboy.auctionsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

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


        //productsGrid.setAdapter(new ProductGridAdapter(getActivity()));
        productsGrid.setAdapter(((DashboardActivity) getActivity()).cursorAdapter);

        productsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                App.ShowMsg(view, "item selected at " + position + " id: " + id, null, null);
                Intent intent = new Intent(getActivity(), ProductEditorActivity.class);
                Uri uri = Uri.parse(ProductProvider.CONTENT_URI + "/" + String.valueOf(id));
                intent.putExtra(ProductProvider.PRODUCT_EDIT_TYPE, uri);

                getActivity().startActivityForResult(intent, DashboardActivity.PRODUCT_REQUEST_CODE);
            }
        });


        return view;
    }
}
