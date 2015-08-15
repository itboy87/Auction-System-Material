package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
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
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.ProductCursorAdapter;
import com.suh.itboy.auctionsystem.Adapters.ViewPagerAdapter;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.HomeFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.MessagesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.MySalesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.PurchasesFragment;
import com.suh.itboy.auctionsystem.Fragments.Dashboard.WishListFragment;
import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.Provider.ProductProvider;
import com.suh.itboy.auctionsystem.R;

public class DashboardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PRODUCT_REQUEST_CODE = 200;
    public CursorAdapter cursorAdapter;
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

        //Uri productUri = insertProduct("Product 1", "Product 1 Description", 4000);

        //Log.d("DashboardActivity", "Product Inserted At " + productUri.getLastPathSegment());

        cursorAdapter = new ProductCursorAdapter(DashboardActivity.this, null, 0);

        getLoaderManager().initLoader(0, null, this);
    }

    private Uri insertProduct(String name, String description, long price) {
        ContentValues values = new ContentValues();
        values.put(ProductDBAdapter.COLUMN_TITLE, name);
        values.put(ProductDBAdapter.COLUMN_DESCRIPTION, description);
        values.put(ProductDBAdapter.COLUMN_PRICE, price);

        return getContentResolver().insert(ProductProvider.CONTENT_URI, values);
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
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setHomeButtonEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_menu);
    }

    private void initTabLayout(ViewPager viewPager, ViewPagerAdapter viewPagerAdapter) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        int length = tabLayout.getTabCount();
        for (int i = 0; i < length; i++) {
            tabLayout.getTabAt(i).setCustomView(viewPagerAdapter.getTabView(tabLayout, i));
        }
        /*tabLayout.addTab(tabLayout.newTab().setText("Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Bid Products"));
        tabLayout.addTab(tabLayout.newTab().setText("New Products"));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_create_sample:
                createSampleData();
                break;

            case R.id.action_delete_products:
                deleteAllProducts();
                break;

            case R.id.action_logout:
                UserSessionHelper.getInstance(this).logOutUser();
                ActivityManager.startAccountActivity(this);
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createSampleData() {
        insertProduct("Product 1", "Product 1 Description", 4000);
        insertProduct("Product 2", "Product 2 Description", 6000);
        insertProduct("Product 3", "Product 3 Description", 2000);
        insertProduct("Product 4", "Product 4 Description", 9000);
        insertProduct("Product 5", "Product 5 Description", 6000);
        insertProduct("Product 6", "Product 6 Description", 2000);

        restartLoader();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, DashboardActivity.this);
    }

    private void deleteAllProducts() {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    getContentResolver().delete(ProductProvider.CONTENT_URI, null, null);
                    restartLoader();
//                    App.ShowMsg(null, getString(R.string.all_delete_toast), null, null);
                    Toast.makeText(DashboardActivity.this, getString(R.string.all_delete_toast), Toast.LENGTH_SHORT).show();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.confirm_delete_all_products))
                .setPositiveButton(getString(R.string.yes), clickListener)
                .setNegativeButton(getString(R.string.No), clickListener)
                .show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(DashboardActivity.this, ProductProvider.CONTENT_URI, null, null, null,
                ProductDBAdapter.COLUMN_CREATED + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(DashboardActivity.this, "RESULT OK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DashboardActivity.this, "RESULT NOT OK", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddProduct(View view) {
        Intent productIntent = new Intent(DashboardActivity.this, AddProductActivity.class);
        startActivityForResult(productIntent, PRODUCT_REQUEST_CODE);
    }
}
