package com.suh.itboy.auctionsystem.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ViewPager viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        viewPagerAdapter.addFragment(new HomeFragment(), getString(R.string.tab_1), R.drawable.ic_action_home, R.color.tab_1);
        viewPagerAdapter.addFragment(new MySalesFragment(), getString(R.string.tab_2), R.drawable.ic_action_shopping_cart, R.color.tab_2);
        viewPagerAdapter.addFragment(new PurchasesFragment(), getString(R.string.tab_3), R.drawable.ic_action_shopping_basket, R.color.tab_3);
        viewPagerAdapter.addFragment(new WishListFragment(), getString(R.string.tab_4), R.drawable.ic_action_card_giftcard, R.color.tab_4);
        viewPagerAdapter.addFragment(new MessagesFragment(), getString(R.string.tab_5), R.drawable.ic_action_email, R.color.tab_5);

        viewPager.setAdapter(viewPagerAdapter);
        this.initToolBar();
        this.initTabLayout(viewPager, viewPagerAdapter);
        this.setupDrawerLayout();
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
            actionBar.setHomeButtonEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
    }

    private void initTabLayout(ViewPager viewPager, ViewPagerAdapter viewPagerAdapter) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        int length = tabLayout.getTabCount();
        for (int i = 0; i < length; i++) {
            tabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabView(i));
        }
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

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
