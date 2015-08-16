package com.suh.itboy.auctionsystem.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by itboy on 8/2/2015.
 */
public class App {
    private static final String TAG = "Auction.Debug:APP";
//    private static ProgressDialog progressDialog;

    public static void ShowMsg(View view, String value, String actionString, View.OnClickListener clickListener) {
        Snackbar.make(view, value, Snackbar.LENGTH_LONG).setAction(actionString, clickListener)
                .show();
        //Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
    }

    public static String getExtension(String path) {
        int index = path.lastIndexOf(".");
        if (index > 0) {
            return path.substring(index + 1);
        } else {
            return "";
        }
    }

    public static boolean writeImageToInternal(Context context, Bitmap b, String picName) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            String extension = App.getExtension(picName);
            if (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("jpg"))
                b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            else if (extension.equalsIgnoreCase("png"))
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            else {
                fos.close();
                Log.d(TAG, "Unknown Extension: " + extension);
                return false;
            }

            fos.close();
        } catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

/*    public static void showProgressDialog(Context context, String title, String message){
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        progressDialog.show();
    }

    public static void closeProgressDialog(){
        if (progressDialog != null)
            progressDialog.dismiss();
    }*/
}
