package com.suh.itboy.auctionsystem.Models.Database;

import android.database.Cursor;

import com.suh.itboy.auctionsystem.Adapters.Database.ProfileDBAdapter;

/**
 * Created by itboy on 8/2/2015.
 */
public class ProfileModel extends Model {

    private String name;
    private String avatar;
    private String gender;

    public ProfileModel() {
    }

    public ProfileModel(long id,String name, String avatar, String gender) {
        this.setRowId(id);
        this.setName(name);
        this.setAvatar(avatar);
        this.setGender(gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean mapFromCursor(Cursor c){
        if (c != null){
            return false;
        }
        if (c.moveToFirst()){
            this.setName(c.getString(c.getColumnIndex(ProfileDBAdapter.COLUMN_NAME)));
            this.setAvatar(c.getString(c.getColumnIndex(ProfileDBAdapter.COLUMN_AVATAR)));
            this.setGender(c.getString(c.getColumnIndex(ProfileDBAdapter.COLUMN_GENDER)));
            this.setRowId(c.getInt(c.getColumnIndex(ProfileDBAdapter.ROW_ID)));

            return true;
        }

        return false;
    }
}
