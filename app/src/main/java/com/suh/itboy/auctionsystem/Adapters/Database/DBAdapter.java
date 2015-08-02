package com.suh.itboy.auctionsystem.Adapters.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    public static final String DATABASE_NAME = "auction";

    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = "create table "
            + UserDBAdapter.DATABASE_TABLE +" (_id integer primary key autoincrement, "
            + UserDBAdapter.COLUMN_EMAIL+ " TEXT,"
            + UserDBAdapter.COLUMN_PASS+ " TEXT,"
            + UserDBAdapter.COLUMN_CREATED+ " DATETIME DEFAULT CURRENT_TIMESTAMP," + ");";

    private static final String CREATE_TABLE_PROFILE = "create table "
            + ProfileDBAdapter.DATABASE_TABLE+" (_id integer primary key autoincrement, "
            +ProfileDBAdapter.COLUMN_NAME+" TEXT,"
            +ProfileDBAdapter.COLUMN_AVATAR+" TEXT," + ");";

        private static final String CREATE_TABLE_PRODUCT = "create table "
                + ProductDBAdapter.DATABASE_TABLE+" (_id integer primary key autoincrement, "
                + ProductDBAdapter.COLUMN_TITLE+" TEXT,"
                + ProductDBAdapter.COLUMN_DESCRIPTION+" TEXT,"
                + ProductDBAdapter.COLUMN_PRICE+" INT"
                + ProductDBAdapter.COLUMN_CREATED+" INT" + ");";   //$NON-NLS-2$


    private final Context context; 
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    /**
     * Constructor
     * @param ctx
     */
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_PROFILE);
            db.execSQL(CREATE_TABLE_PRODUCT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion)
        {               
            // Adding any table mods to this guy here
        }
    } 

   /**
     * open the db
     * @return this
     * @throws SQLException
     * return type: DBAdapter
     */
    public DBAdapter open() throws SQLException
    {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    /**
     * close the db 
     * return type: void
     */
    public void close() 
    {
        this.DBHelper.close();
    }
}