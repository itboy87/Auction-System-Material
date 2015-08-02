package com.suh.itboy.auctionsystem.Utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by itboy on 8/2/2015.
 */
public class App {
    public static void ShowMsg(View view,String value){
        Snackbar.make(view, value, Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
    }
}
