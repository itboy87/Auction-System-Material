package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Adapters.AccountPagerAdapter;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.Validate;

public class AccountActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        viewPager = (ViewPager)findViewById(R.id.account_viewpager);
        AccountPagerAdapter accountPagerAdapter = new AccountPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(accountPagerAdapter);
    }

    public void ChangeToSignIn(View view) {
        viewPager.setCurrentItem(0, true);
    }

    public void ChangeToSignUp(View view) {
        viewPager.setCurrentItem(1, true);
    }

}
