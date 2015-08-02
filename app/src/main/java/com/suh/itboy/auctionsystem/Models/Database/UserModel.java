package com.suh.itboy.auctionsystem.Models.Database;

import android.database.Cursor;

import com.suh.itboy.auctionsystem.Adapters.Database.UserDBAdapter;
import com.suh.itboy.auctionsystem.Helper.DatabaseHelper;

/**
 * Created by itboy on 8/2/2015.
 */
public class UserModel extends Model {
    private String email;
    private String pass;

    public UserModel() {

    }

    public UserModel(long id, String email, String pass) {
        this.setRowId(id);
        this.setEmail(email);
        this.setPass(pass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean mapFromCursor(Cursor c){
        if (c != null){
            return false;
        }
        if (c.moveToFirst()){
            this.setEmail(c.getString(c.getColumnIndex(UserDBAdapter.COLUMN_EMAIL)));
            this.setPass(c.getString(c.getColumnIndex(UserDBAdapter.COLUMN_PASS)));
            this.setRowId(c.getInt(c.getColumnIndex(UserDBAdapter.ROW_ID)));

            return true;
        }

        return false;
    }


}
