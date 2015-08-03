package com.suh.itboy.auctionsystem.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by itboy on 8/2/2015.
 */
public class ActivityManager {
    public static void startAccountActivity(Activity parentActivity){
        startActivity(parentActivity,AccountActivity.class);
        parentActivity.finish();
    }

    public static void startHomeActivity(Activity parentActivity){
        startActivity(parentActivity, DashboardActivity.class);
        parentActivity.finish();
    }

    private static void startActivity(Context context, Class<? extends Activity> activityClass){
        Intent intent = new Intent(context,activityClass );
        context.startActivity(intent);
    }
}
