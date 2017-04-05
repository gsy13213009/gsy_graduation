package com.gsy.graduation.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.gsy.graduation.application.BaseApplication;

import java.lang.reflect.Field;

/**
 * 设备相关工具类
 */
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static float dip2fpx(Context context, float pDipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return pDipValue * dm.density;
    }

    /**
     * 密度转换像素
     *
     * @param pDipValue dp值
     * @return 像素
     */
    public static float dip2fpx(float pDipValue) {
        return dip2fpx(BaseApplication.getApplication(), pDipValue);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (dipValue * dm.density + 0.5f);
    }

    /**
     * 密度转换像素
     *
     * @param dipValue dp值
     * @return 像素
     */
    public static int dip2px(float dipValue) {
        return dip2px(BaseApplication.getApplication(), dipValue);
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue() {
        return getDensityValue(BaseApplication.getApplication());
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     */
    public static float getDensityValue(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取手机品牌
     *
     * @return 手机品牌
     * @see Build#BRAND
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     * @see Build#MODEL
     */
    public static String getDeviceMode() {
        return Build.MODEL;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机版本号
     * @see android.os.Build.VERSION#RELEASE
     */
    public static String getDeviceVersionoRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取屏幕密度值
     *
     * @return 屏幕密度值
     * @see #getDensityValue(Context)
     */
    public static float getDmDensity(Context context) {
        return getDensityValue(context);
    }

    // /**
    // * 当前屏幕的density因子
    // * @param dmDensityDpi
    // * @retrun DmDensity Setter
    // * */
    // public static void setDmDensityDpi(float dmDensityDpi) {
    // mDensityDpi = dmDensityDpi;
    // }

    /**
     * 获取当前屏幕的显示密度（dpi）
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi() {
        return getDmDensityDpi(BaseApplication.getApplication());
    }

    /**
     * 当前屏幕的显示密度
     *
     * @retrun 当前屏幕的显示密度
     */
    public static float getDmDensityDpi(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /***
     *  获取设备的IMEI值
     * @return 设备的IMEI值
     */
    public static String getImeiValue() {
        String deviceId = null;
        try {
            deviceId = ((TelephonyManager) BaseApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
        } catch (Exception exception) {
        }
        return deviceId;
    }


    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return getScreenHeight(BaseApplication.getApplication());
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.widthPixels;
        } else {
            return dm.heightPixels;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getScreenWidth(BaseApplication.getApplication());
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.heightPixels;
        } else {
            return dm.widthPixels;
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception ignored) {

        }
        return sbar;
    }

    /**
     * 判断设备的API等级是否大于等于11
     *
     * @return true API等级大于等于11，false API等级小于11
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 判断SDK版本是否超过指定版本号
     *
     * @param sdkNum 指定的SDK版本号
     * @return true 大于指定版本号  false 反之，小于等于指定版本号
     */
    public static boolean isSDKVersionMoreThanSpecifiedNum(int sdkNum) {
        return Build.VERSION.SDK_INT > sdkNum ? true : false;
    }

    /**
     * 像素值转换为dp值
     *
     * @param context 上下文
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (pxValue / dm.density + 0.5f);
    }

    /**
     * 像素值转换为dp值
     *
     * @param pxValue 像素值
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        return px2dip(BaseApplication.getApplication(), pxValue);
    }

}
