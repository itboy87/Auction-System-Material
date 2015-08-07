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
    private static final String DATABASE_NAME = "auction";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_USER = "create table "
            + UserDBAdapter.DATABASE_TABLE + " (_id integer primary key autoincrement, "
            + UserDBAdapter.COLUMN_EMAIL + " TEXT collate nocase,"
            + UserDBAdapter.COLUMN_PASS + " TEXT,"
            + UserDBAdapter.COLUMN_CREATED + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ");";
    private static final String CREATE_TABLE_PROFILE = "create table "
            + ProfileDBAdapter.DATABASE_TABLE + " (_id integer primary key autoincrement, "
            + ProfileDBAdapter.COLUMN_USER_ID + " integer REFERENCES "
            + UserDBAdapter.DATABASE_TABLE + ","
            + ProfileDBAdapter.COLUMN_NAME + " TEXT collate nocase,"
            + ProfileDBAdapter.COLUMN_AVATAR + " TEXT collate nocase,"
            + ProfileDBAdapter.COLUMN_GENDER + " TEXT collate nocase" + ");";
    private static final String CREATE_TABLE_PRODUCT = "create table "
            + ProductDBAdapter.DATABASE_TABLE + " (_id integer primary key autoincrement, "
            + ProductDBAdapter.COLUMN_TITLE + " TEXT collate nocase,"
            + ProductDBAdapter.COLUMN_DESCRIPTION + " TEXT collate nocase,"
            + ProductDBAdapter.COLUMN_PRICE + " INT,"
            + ProductDBAdapter.COLUMN_CREATED + " INT" + ");";
    private static SQLiteDatabase sInstance;

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteDatabase getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }
        return sInstance;
    }

    //Wrap for getInstance can be used either
    public static SQLiteDatabase initialize(Context context) {
        return getInstance(context);
    }

    public static void beginTransaction() {
        //TODO: exception handling for twice transaction error
        sInstance.beginTransaction();
    }

    public static void endTransaction() {
        sInstance.endTransaction();
    }

    public static void setTransactionSuccessful() {
        sInstance.setTransactionSuccessful();
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

    public void close() {
        if (null != sInstance) {
            sInstance.close();
            sInstance = null;
        }
    }
}