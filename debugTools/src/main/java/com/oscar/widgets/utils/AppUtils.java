package com.oscar.widgets.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class AppUtils {

    public static final int LG_SCREEN_LONGER = 3840;
    public static final int LG_SCREEN_SHORTER = 2160;

    public static int dp2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 朗国板卡获取屏幕方向
    public static int getScreenOrientationLg() {
        String orientation = getProp("persist.sys.screenorientation", "landscape");
        switch (orientation) {
            case "portrait":
                return 90;
            case "upsideDown":
                return 270;
            case "landscape":
            default:
                return 0;
        }
    }

    public static int getScreenWidthLg() {
        switch (getScreenOrientationLg()) {
            case 90:
            case 270:
                return LG_SCREEN_SHORTER;
            case 0:
            default:
                return LG_SCREEN_LONGER;

        }
    }

    public static int getScreenHeightLg() {
        switch (getScreenOrientationLg()) {
            case 90:
            case 270:
                return LG_SCREEN_LONGER;
            case 0:
            default:
                return LG_SCREEN_SHORTER;

        }
    }


    public static void getScreenSize(Context context,Point size) {
        if (Build.MODEL.equals("XBHV811")) {
            size.x = getScreenWidthLg();
            size.y = getScreenHeightLg();
        } else {
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(size);
        }
    }
    public static void setProp(String pro, String value) {
        String cmd = "";
        cmd = "setprop " + pro + " " + value;

        try {
            Class<?> c = Class.forName("android.os.Tools");
            Method set = c.getMethod("system", String.class);
            set.invoke(c, cmd);
        } catch (Exception var5) {
        }

    }

    public static String getProp(String key, String defaultValue) {
        String value = defaultValue;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String)get.invoke(c, key, "unknown");
        } catch (Exception var5) {
        }

        return value;
    }
}
