package com.suh.itboy.auctionsystem.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.suh.itboy.auctionsystem.Adapters.ViewPagerAdapter;
import com.suh.itboy.auctionsystem.Fragments.Account.LoginFragment;
import com.suh.itboy.auctionsystem.Fragments.Account.RegisterFragment;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.App;

import java.util.Timer;
import java.util.TimerTask;

public class AccountActivity extends AppCompatActivity {
    ViewPager viewPager;
    private static View rootView;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        viewPager = (ViewPager)findViewById(R.id.account_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(),"Login");
        viewPagerAdapter.addFragment(new RegisterFragment(),"Register");
        viewPager.setAdapter(viewPagerAdapter);

        rootView = findViewById(R.id.activity_account);

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

    public static void closeProgressDialog(long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                AccountActivity.closeProgressDialog();
            }
        };
        new Timer().schedule(timerTask, delay);
    }

    public void ChangeToSignIn(View view) {
        viewPager.setCurrentItem(0, true);
    }

    public void ChangeToSignUp(View view) {
        viewPager.setCurrentItem(1, true);
    }

    /**
     * Wrap for App.ShowMsg
     */
    public static void showMsg(String value){
        App.ShowMsg(rootView, value);
    }

    public static void showMsg(final String value,long delay){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                App.ShowMsg(rootView, value);
            }
        };
        new Timer().schedule(timerTask, delay);
    }

}
