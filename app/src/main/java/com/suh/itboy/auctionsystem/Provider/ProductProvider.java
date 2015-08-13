package com.suh.itboy.auctionsystem.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.suh.itboy.auctionsystem.Adapters.Database.ProductDBAdapter;
import com.suh.itboy.auctionsystem.Helper.DatabaseHelper;

public class ProductProvider extends ContentProvider {
    public static final String URI_AUTHORITY = "com.suh.auctionsystem.productprovider";
    static final String URL = "content://" + URI_AUTHORITY + "/" + ProductDBAdapter.DATABASE_TABLE;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static final int PRODUCTS = 1;
    static final int PRODUCT_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        uriMatcher.addURI(URI_AUTHORITY, ProductDBAdapter.DATABASE_TABLE, PRODUCTS);
        uriMatcher.addURI(URI_AUTHORITY, ProductDBAdapter.DATABASE_TABLE + "/#", PRODUCT_ID);
    }


    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        database = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ProductDBAdapter.DATABASE_TABLE);

        return queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(ProductDBAdapter.DATABASE_TABLE, null, values);
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(ProductDBAdapter.DATABASE_TABLE, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(ProductDBAdapter.DATABASE_TABLE, values, selection, selectionArgs);
    }
}
