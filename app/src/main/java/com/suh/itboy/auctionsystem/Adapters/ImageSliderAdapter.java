package com.suh.itboy.auctionsystem.Adapters;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suh.itboy.auctionsystem.Fragments.Dashboard.ImageSliderFragment;
import com.suh.itboy.auctionsystem.R;

/**
 * Created by itboy on 8/5/2015.
 */
public class ImageSliderAdapter extends FragmentPagerAdapter {
    public static String KEY_SLIDE = "slide";
    private int[] images = new int[]{
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3,
            R.drawable.slider4
    };

    public ImageSliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(KEY_SLIDE,images[position]);
        ImageSliderFragment imageSliderFragment = new ImageSliderFragment();
        imageSliderFragment.setArguments(args);
        return imageSliderFragment;
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
