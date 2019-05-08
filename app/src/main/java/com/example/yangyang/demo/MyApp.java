package com.example.yangyang.demo;

import android.app.Application;

import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MyApp extends Application {
    public static String deviceId ;

    public static String BaseUrl = "10.60.7.252:8999";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化DBFLOW
        FlowManager.init(this);
        deviceId = DeviceIdUtil.getDeviceId(this);
    }
}
