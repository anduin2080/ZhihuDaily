package com.example.zhihudaily.Utils;

import android.app.Application;
import android.os.Handler;

import android.os.Process;
import org.litepal.LitePal;

public class BaseApplication extends Application {
    private static BaseApplication application;

    private static int mainThreadId;
    private static Handler handler;

    @Override
    public void onCreate(){
        super.onCreate();
        application = this;
        mainThreadId = Process.myTid();
        handler = new Handler();
        LitePal.initialize(this);
    }

    public static BaseApplication getApplication(){
        return application;
    }

    public static BaseApplication getContext(){
        return application;
    }

    public static int getMainThreadId(){
        return mainThreadId;
    }

    public static Handler getHandler(){
        return handler;
    }
}
