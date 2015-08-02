package com.suh.itboy.auctionsystem.Adapters.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.suh.itboy.auctionsystem.Helper.DatabaseHelper;
import com.suh.itboy.auctionsystem.Interfaces.BaseColumns;

public class ProfileDBAdapter implements BaseColumns {

    public static final String DATABASE_TABLE = "profile";

    public static final String COLUMN_NAME = "full_name";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_GENDER = "gender";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public ProfileDBAdapter(Context ctx) {
        mDbHelper = DatabaseHelper.getInstance(ctx);
    }

    public ProfileDBAdapter open() throws SQLException {
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }


    public void close() {
        this.mDbHelper.close();
    }

    /**
     * Create a new profile. If the profile is successfully created return the new
     * rowId for that profile, otherwise return a -1 to indicate failure.
     * 
     * @param name
     * @param avatar
     * @param gender
     * @return rowId or -1 if failed
     */
    public long createProfile(String name, String avatar, String gender){
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME, name);
        initialValues.put(COLUMN_AVATAR, avatar);
        initialValues.put(COLUMN_GENDER, gender);
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the profile with the given rowId
     * 
     * @param rowId
     * @return true if deleted, false otherwise
     */
    public boolean deleteProfile(long rowId) {

        return this.mDb.delete(DATABASE_TABLE, ROW_ID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all profiles in the database
     * 
     * @return Cursor over all profiles
     */
    public Cursor getAllProfiles() {

        return this.mDb.query(DATABASE_TABLE, new String[] { ROW_ID,
                COLUMN_NAME, COLUMN_AVATAR, COLUMN_GENDER }, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the profile that matches the given rowId
     * @param rowId
     * @return Cursor positioned to matching profile, if found
     * @throws SQLException if profile could not be found/retrieved
     */
    public Cursor getProfile(long rowId) throws SQLException {

        Cursor mCursor =

        this.mDb.query(true, DATABASE_TABLE, new String[] { ROW_ID, COLUMN_NAME,
                COLUMN_AVATAR, COLUMN_GENDER}, ROW_ID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Update the profile.
     * 
     * @param rowId
     * @param name
     * @param avatar
     * @param gender
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateProfile(long rowId, String name, String avatar, String gender){
        ContentValues args = new ContentValues();
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_AVATAR, avatar);
        args.put(COLUMN_GENDER, gender);

        return this.mDb.update(DATABASE_TABLE, args, ROW_ID + "=" + rowId, null) >0; 
    }

}