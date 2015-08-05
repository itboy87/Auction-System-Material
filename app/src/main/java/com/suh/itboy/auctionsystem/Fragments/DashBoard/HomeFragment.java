package com.suh.itboy.auctionsystem.Fragments.Dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.suh.itboy.auctionsystem.Adapters.ImageSliderAdapter;
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
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.slider_viewpager);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(imageSliderAdapter);
        homeViewPager = (ViewPager)getActivity().findViewById(R.id.home_viewpager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                homeViewPager.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        return view;
    }


}
