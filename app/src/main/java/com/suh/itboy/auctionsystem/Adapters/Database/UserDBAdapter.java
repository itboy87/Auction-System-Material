package com.suh.itboy.auctionsystem.Adapters.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by itboy on 8/1/2015.
 */
public class UserDBAdapter {
    public static final String ROW_ID = "_id";

    public static final String DATABASE_TABLE = "user";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_CREATED = "created_at";

    
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public UserDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public UserDBAdapter open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    /**
     * @return rowId or -1 if failed
     */
    public long createUser(String email, String pass){
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_EMAIL, email);
        initialValues.put(COLUMN_PASS, pass);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * @return true if deleted, false otherwise
     */
    public boolean deleteuserUser(long rowId) {

        return this.mDb.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0;
    }

    /**
     * @return Cursor over all users
     */
    public Cursor getAlluserUsers() {

        return this.mDb.query(DATABASE_TABLE, new String[] { ROW_ID,
                COLUMN_EMAIL, COLUMN_PASS }, null, null, null, null, null);
    }

    /**
     * @return Cursor positioned to matching user, if found
     * @throws SQLException if user could not be found/retrieved
     */
    public Cursor getuserUser(long rowId) throws SQLException {

        Cursor mCursor =

                this.mDb.query(true, DATABASE_TABLE, new String[] { ROW_ID, COLUMN_EMAIL,
                        COLUMN_PASS}, ROW_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateuserUser(long rowId, String email, String pass){
        ContentValues args = new ContentValues();
        args.put(COLUMN_EMAIL, email);
        args.put(COLUMN_PASS, pass);

        return this.mDb.update(DATABASE_TABLE, args, ROW_ID + "=" + rowId, null) >0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}
