package com.suh.itboy.auctionsystem.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.Database.ProfileDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.Database.UserDBAdapter;

/**
 * Created by itboy on 8/2/2015.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "auction";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = "create table "
            + UserDBAdapter.DATABASE_TABLE +" (_id integer primary key autoincrement, "
            + UserDBAdapter.COLUMN_EMAIL+ " TEXT,"
            + UserDBAdapter.COLUMN_PASS+ " TEXT,"
            + UserDBAdapter.COLUMN_CREATED+ " DATETIME DEFAULT CURRENT_TIMESTAMP," + ");";

    private static final String CREATE_TABLE_PROFILE = "create table "
            + ProfileDBAdapter.DATABASE_TABLE+" (_id integer primary key autoincrement, "
            + ProfileDBAdapter.COLUMN_NAME+" TEXT,"
            + ProfileDBAdapter.COLUMN_AVATAR+" TEXT," + ");";

    private static final String CREATE_TABLE_PRODUCT = "create table "
            + ProductDBAdapter.DATABASE_TABLE+" (_id integer primary key autoincrement, "
            + ProductDBAdapter.COLUMN_TITLE+" TEXT,"
            + ProductDBAdapter.COLUMN_DESCRIPTION+" TEXT,"
            + ProductDBAdapter.COLUMN_PRICE+" INT"
            + ProductDBAdapter.COLUMN_CREATED+" INT" + ");";

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PROFILE);
        db.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void close(){
        sInstance.close();
        sInstance = null;
    }
}