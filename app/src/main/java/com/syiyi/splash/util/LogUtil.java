package com.syiyi.splash.util;

import android.util.Log;

import com.syiyi.splash.common.Config;

/**
 * Created by lintao.song on 2015/11/2.
 */
public class LogUtil {
    public static final String LOG_NAME="same";
    public static void i(String title, String context) {
        if (Config.IS_DEBUG)
            Log.i(title, context);
    }
    public static void i(String context) {
        if (Config.IS_DEBUG)
            Log.i(LOG_NAME, context);
    }
}
