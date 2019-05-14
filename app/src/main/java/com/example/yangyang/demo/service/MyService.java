package com.example.yangyang.demo.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import com.example.yangyang.demo.Activity.FollowActivity;
import com.example.yangyang.demo.reciever.PhoneReceiver;

public class MyService extends Service {
    private String phoneNumber;
    private int userId , time ;
    private boolean isConnected;
    PhoneReceiver phoneReceiver;
    String group,studentName,userPhoneNumber;
    int count = 0 ;
    private Long teacherGroup;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentfilter =new IntentFilter();
        intentfilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        intentfilter.addAction("android.intent.action.PHONE_STATE");
        intentfilter.setPriority(Integer.MAX_VALUE);
        phoneReceiver = new PhoneReceiver();
        registerReceiver(phoneReceiver, intentfilter);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (count == 0){
            userId = intent.getExtras().getInt("userId");
            studentName = intent.getExtras().getString("studentName");
            group = intent.getExtras().getString("group");
            userPhoneNumber = intent.getExtras().getString("userPhoneNumber");
            teacherGroup = intent.getExtras().getLong("teacherGroup");

         }else {
            time = intent.getExtras().getInt("time");
            isConnected = intent.getExtras().getBoolean("isConnected");
            Intent toFollow = new Intent(this,FollowActivity.class);
            toFollow.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putInt("userId",userId);
            bundle.putString("studentName",studentName);
            bundle.putString("group",group);
            bundle.putBoolean("isConnected",isConnected);
            bundle.putInt("time",time);
            bundle.putBoolean("isLog",false);
            bundle.putString("userPhoneNumber",userPhoneNumber);
            bundle.putLong("teacherGroup",teacherGroup);
            toFollow.putExtras(bundle);
            startActivity(toFollow);


        }
        count ++ ;


        //Log.d(LogCat.TAG,"onStartCommand");


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(phoneReceiver);


        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
