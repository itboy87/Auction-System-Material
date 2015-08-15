package com.suh.itboy.auctionsystem.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by itboy on 8/2/2015.
 */
public class App {
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

    public static boolean writeImageToInternal(Context context, Bitmap bitmapImage, String path) {

        try {
            FileOutputStream outputStream = context.openFileOutput(path, Context.MODE_PRIVATE);
            String extension = App.getExtension(path);
            if (extension.equalsIgnoreCase("jpeg"))
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            else if (extension.equalsIgnoreCase("png"))
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            else
                return false;
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
