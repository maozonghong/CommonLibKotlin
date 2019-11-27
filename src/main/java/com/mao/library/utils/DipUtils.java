package com.mao.library.utils;

import android.content.res.Resources;
import android.util.TypedValue;

import com.mao.library.abs.AbsApplication;

public class DipUtils {
    public static float getDip() {
        return AbsApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    public static int getIntDip(float i) {
        return (int) getFloatDip(i);
    }

    public static float getFloatDip(float i) {
        return AbsApplication.getInstance().getResources().getDisplayMetrics().density * i;
    }

    public static int getScreenWidth() {
        return AbsApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return AbsApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, AbsApplication.getInstance().getResources().getDisplayMetrics());
    }


    public static int getNavigationBarHeight() {
        Resources resources = AbsApplication.getInstance().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        // 获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
