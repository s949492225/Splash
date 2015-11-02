package com.syiyi.splash.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.syiyi.splash.common.Config;

/**
 * Created by lintao.song on 2015/11/2.
 * 偏好设置工具
 */
public class SharePerferencesUtil {
    public static final String SP_NAME = "SAME";
    public static final String APP_VERSION = "SAME";

    /**
     * 保存版本号
     */
    public static void saveVersion(Context context) {
        SharedPreferences sp = getSharedPreferences(context, SP_NAME);
        int sp_v = getVersion(context);
        try {
            int app_v = CommonUtil.getVersion(context);
            if (app_v > sp_v)
                sp.edit().putInt(APP_VERSION, app_v).commit();
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取版本号
     */
    public static int getVersion(Context context) {
        SharedPreferences sp = getSharedPreferences(context, SP_NAME);
        int sp_v=sp.getInt(APP_VERSION, 0);
        if (Config.IS_DEBUG)
            LogUtil.i("偏好设置中的版本号："+sp_v+"");
        return sp_v;
    }

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}
