package com.syiyi.splash.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lintao.song on 2015/11/2.
 */
public class CommonUtil {
    /**
     * 获取版本号
     */
    public static int getVersion(Context context) throws PackageManager.NameNotFoundException {
        int v = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        LogUtil.i("same", "当前应用版本号是：" + v + "");
        return v;
    }

    /**
     * 是否是升级
     */
    public static boolean isUpdateMode(Context context) {
        try {
            int v = getVersion(context);
            int sp_v = SharePerferencesUtil.getVersion(context);
            if (v > sp_v)
                return true;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 设置全屏
     */
    public static void showFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }
}
