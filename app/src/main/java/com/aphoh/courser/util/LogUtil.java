package com.aphoh.courser.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.aphoh.courser.BuildConfig;

/**
 * Created by Will on 1/26/15.
 */
public class LogUtil {
    String LOG_TAG;
    boolean release = BuildConfig.VERSION_NAME == "release";

    public LogUtil(String LOG_TAG) {
        this.LOG_TAG = LOG_TAG;
    }

    public void d(String log) {
        if (!release) android.util.Log.d(LOG_TAG, log);
    }

    public void v(String log) {
        boolean logVersion = true;
        if (logVersion) Log.v(LOG_TAG, log);
    }

    public void wtf(String log) {
        android.util.Log.wtf(LOG_TAG, log);
    }

    public void e(String log) {
        android.util.Log.e(LOG_TAG, log);
    }


    public void toast(Context context, @StringRes int string) {
        toast(context, context.getString(string));
    }

    public void toast(Context context, String msg) {
        if (msg.length() > 20) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public String getLogTag() {
        return LOG_TAG;
    }

    public void setLogTap(String LOG_TAG) {
        this.LOG_TAG = LOG_TAG;
    }

}