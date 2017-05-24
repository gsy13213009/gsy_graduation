package com.gsy.graduation.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 初始化的application对象
 */
public class BaseApplication extends Application {

    private static Application mApplication = null;

    public static boolean mIsShow = false;

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
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
                .diskCacheSize(100 * 1024 * 1024)
                .build());
    }
}
