package com.gsy.graduation.application;

import android.app.Application;

/**
 * 初始化的application对象
 */
public class BaseApplication extends Application {

    private static Application mApplication = null;

    public static Application getApplication() {
        return mApplication;
    }

    public static void setApplication(Application application) {
        mApplication = application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }
}
