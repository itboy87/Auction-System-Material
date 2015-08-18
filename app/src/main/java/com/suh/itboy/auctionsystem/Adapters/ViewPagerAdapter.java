package com.suh.itboy.auctionsystem.Adapters;


import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suh.itboy.auctionsystem.Fragments.Account.LoginFragment;
import com.suh.itboy.auctionsystem.Fragments.Account.RegisterFragment;
import com.suh.itboy.auctionsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itboy on 7/31/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    public static boolean SET_EMAIL = false;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();
    private final List<Integer> mIconList = new ArrayList<>();
    private List<Integer> mColorList = new ArrayList<>();

    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, @DrawableRes int icon, @ColorRes int color) {
        this.mFragmentList.add(fragment);
        this.mTitleList.add(title);
        this.mIconList.add(icon);
        this.mColorList.add(color);
    }

    public void addFragment(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mTitleList.add(title);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        //if set email then get email from opposite page set in page
        if (SET_EMAIL) {
            String email = null;
            switch (position) {
                case 0:
                    email = ((RegisterFragment) mFragmentList.get(1)).getEmail();
                    ((LoginFragment) mFragmentList.get(position)).setEmail(email);
                    break;
                case 1:
                    email = ((LoginFragment) mFragmentList.get(0)).getEmail();
                    ((RegisterFragment) mFragmentList.get(position)).setEmail(email);
                    break;
            }
        }

        SET_EMAIL = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public View getTabView(TabLayout tabLayout, int position) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.tab_layout, tabLayout, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);


        icon.setImageResource(this.mIconList.get(position));
        title.setText(this.getPageTitle(position));
        /*ViewGroup layout = (ViewGroup) view.findViewById(R.id.layout);
        layout.setBackgroundResource(this.mColorList.get(position));*/
        return view;
    }

}
