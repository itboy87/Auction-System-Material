package com.suh.itboy.auctionsystem.Adapters.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.suh.itboy.auctionsystem.Helper.DatabaseHelper;
import com.suh.itboy.auctionsystem.Interfaces.BaseColumns;

public class ProductDBAdapter implements BaseColumns {
    public static final String TAG = ProductDBAdapter.class.getName();

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CREATED = "created_at";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "picture";

    public static final String DATABASE_TABLE = "product";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public ProductDBAdapter(Context ctx) {
        this.mDb = DatabaseHelper.getInstance(ctx);
    }

    public long createProduct(String title, String description, String price) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_TITLE, title);
        initialValues.put(COLUMN_DESCRIPTION, description);
        initialValues.put(COLUMN_PRICE, price);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the product with the given rowId
     *
     * @param rowId
     * @return true if deleted, false otherwise
     */
    public boolean deleteProduct(long rowId) {

        return this.mDb.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all products in the database
     *
     * @return Cursor over all products
     */
    public Cursor getAllProducts() {

        return this.mDb.query(DATABASE_TABLE, new String[]{ROW_ID,
                COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_PRICE}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the product that matches the given rowId
     *
     * @param rowId
     * @return Cursor positioned to matching product, if found
     * @throws SQLException if product could not be found/retrieved
     */
    public Cursor getProduct(long rowId) throws SQLException {

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, new String[]{ROW_ID, COLUMN_TITLE,
                        COLUMN_DESCRIPTION, COLUMN_PRICE}, ROW_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the product.
     *
     * @param rowId
     * @param title
     * @param description
     * @param price
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateProduct(long rowId, String title, String description, String price) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_TITLE, title);
        args.put(COLUMN_DESCRIPTION, description);
        args.put(COLUMN_PRICE, price);

        return this.mDb.update(DATABASE_TABLE, args, ROW_ID + "=" + rowId, null) > 0;
    }

}