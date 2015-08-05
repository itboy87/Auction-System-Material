package com.suh.itboy.auctionsystem.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.suh.itboy.auctionsystem.Adapters.ViewPagerAdapter;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.HomeFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.MessagesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.MySalesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.PurchasesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.WishListFragment;
import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ViewPager viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment(), "Home");
        viewPagerAdapter.addFragment(new MySalesFragment(), "My Sales");
        viewPagerAdapter.addFragment(new PurchasesFragment(), "Purchases");
        viewPagerAdapter.addFragment(new WishListFragment(), "WishList");
        viewPagerAdapter.addFragment(new MessagesFragment(), "Messages");

        viewPager.setAdapter(viewPagerAdapter);
        this.initToolBar();
        this.initTabLayout(viewPager);
        
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }
    }

    private void initTabLayout(ViewPager viewPager){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Bid Products"));
        tabLayout.addTab(tabLayout.newTab().setText("New Products"));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            UserSessionHelper.getInstance(this).logOutUser();
            ActivityManager.startAccountActivity(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
