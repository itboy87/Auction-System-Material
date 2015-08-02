package com.suh.itboy.auctionsystem.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.suh.itboy.auctionsystem.Adapters.AccountPagerAdapter;
import com.suh.itboy.auctionsystem.R;

public class AccountActivity extends AppCompatActivity {
    ViewPager viewPager;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        viewPager = (ViewPager)findViewById(R.id.account_viewpager);
        AccountPagerAdapter accountPagerAdapter = new AccountPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(accountPagerAdapter);

        progressDialog = new ProgressDialog(AccountActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
    }

    public static void showProgressDialog(String msg){
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void closeProgressDialog(){
        progressDialog.dismiss();
    }

    public void ChangeToSignIn(View view) {
        viewPager.setCurrentItem(0, true);
    }

    public void ChangeToSignUp(View view) {
        viewPager.setCurrentItem(1, true);
    }

}
