package com.suh.itboy.auctionsystem.Adapters;


import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suh.itboy.auctionsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itboy on 7/31/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
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

    public View getTabView(int position) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.tab_layout, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        ViewGroup layout = (ViewGroup) view.findViewById(R.id.layout);

        layout.setBackgroundResource(this.mColorList.get(position));
        icon.setImageResource(this.mIconList.get(position));
        title.setText(this.getPageTitle(position));

        return view;
    }
}
