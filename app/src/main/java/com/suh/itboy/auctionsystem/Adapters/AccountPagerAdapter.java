package com.suh.itboy.auctionsystem.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.suh.itboy.auctionsystem.Fragments.LoginFragment;
import com.suh.itboy.auctionsystem.Fragments.RegisterFragment;

/**
 * Created by itboy on 7/31/2015.
 */
public class AccountPagerAdapter extends FragmentPagerAdapter {
    public static String TAG = "AccountPagerAdapter";
    public AccountPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "position: "+position);
        if (position == 0){
            return new LoginFragment();
        }
        else{
            return new RegisterFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
