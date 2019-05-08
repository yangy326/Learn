package com.example.yangyang.demo.Utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class DeviceIdUtil {
    public static List<String> getAndroidIMEI0(Context context) {
        List<String> imei = new ArrayList<>();
        if (context == null) {
            return imei;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return imei;
            } else {
                for (int slot = 0; slot < telephonyManager.getPhoneCount(); slot++) {
                    String a = telephonyManager.getDeviceId(slot);
                    imei.add(a);
                }
            }
        }
        return imei;
    }
    public static String generateAndSaveDeviceId(Context context) {
        String imei = null;
        List<String> list = getAndroidIMEI0(context);
        for (String s : list) {
                if (!TextUtils.isEmpty(s)) {
                    imei = s;
                    break;
                }
            }

        if (TextUtils.isEmpty(imei)) {
            imei = String.valueOf(UUID.randomUUID());
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
        editor.putString("deviceID",imei);
        editor.apply();


        return imei;
    }

    // 获取deviceId
    public static String getDeviceId(Context context) {
        String deviceId = context.getSharedPreferences("isCheckLogin",MODE_PRIVATE).getString("deviceId",null);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = generateAndSaveDeviceId(context);
        }
        return deviceId;
    }
}
