package com.example.yangyang.demo.reciever;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.yangyang.demo.Activity.FollowActivity;

import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.service.MyService;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.provider.CallLog.Calls.OUTGOING_TYPE;



public class PhoneReceiver extends BroadcastReceiver {
    private String phone = "actertwerwtrivity";

      boolean Callstate = false;

     boolean Calllaststate = false;

    volatile boolean  isConnected ,isSend = true;

    long durationTime ;

     int time ;

     String number ;

     int count = 0;


    /*private ComingCallBack mcomingCallBack;
    public PhoneReceiver(ComingCallBack comingCallBack) {
        mcomingCallBack = comingCallBack;
    }*/

    @Override
    public void onReceive(final Context context, Intent intent) {



        //Log.d("CALL_STATE_RINGING", intent.getExtras().getString("phoneNumber"));
        //Log.d("CALL_STATE_RINGING", String.valueOf(intent.getExtras().getInt("userId")));


        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
        /*tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        System.out.println("action"+intent.getAction());

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);*/



        int state=tm.getCallState();

        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:


                Log.d("dsfsdfsdds","来电");
                break;


            case TelephonyManager.CALL_STATE_OFFHOOK:


                Log.d("dsfsdfsdds","正在通话中");
                Callstate = true ;
                Calllaststate = true;
                //Log.d("CALL_STATE_IDLE", "CALL_STATE_OFFHOOK" + String.valueOf(Callstate));
                //Log.d("CALL_STATE_IDLE","CALL_STATE_OFFHOOK" + String.valueOf(Calllaststate));
                break;


            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("dsfsdfsdds","通话结束");

                Calllaststate = false;


                //Log.d("CALL_STATE_IDLE",String.valueOf(Calllaststate));




                break;            }


                    if (!Calllaststate && Callstate)  {

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ContentResolver cr = context.getContentResolver();
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(context, "请允许应用读取通话日志", Toast.LENGTH_LONG).show();

                        }

                        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION, CallLog.Calls.DATE}, CallLog.Calls.NUMBER + "=? and " + CallLog.Calls.TYPE + "= ?", new String[]{MyApp.phoneNumber, String.valueOf(OUTGOING_TYPE)}, CallLog.Calls.DEFAULT_SORT_ORDER);

                        if (cursor.moveToFirst()) {
                            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
                            durationTime = cursor.getLong(durationIndex);
                            Log.d("dsfsdfdddsdds", String.valueOf(durationTime));

                            //根据通话时长是否大于0判断是否接通

                            if (durationTime > 0) {
                                isConnected = true;
                            } else {
                                isConnected = false;
                            }
                        }

                        Callstate = false;
                        Calllaststate = false ;

                        isSend =false;
                        count = 0;
                        Log.d("dsfsadf","here");
                        long time1 = durationTime;
                        time = (int) time1;
                        Intent intent2 = new Intent(context,MyService.class);

                        Bundle bundle = new Bundle();
                        bundle.putInt("time", time);
                        bundle.putBoolean("isConnected",isConnected);
                        intent2.putExtras(bundle);
                        context.startService(intent2);
                        isSend = true;







                }














        //如果是去电
        /*if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            String phoneNumber = intent
                    .getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG, "call OUT:" + phoneNumber);
        }else{*/
            //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电.
            //如果我们想要监听电话的拨打状况，需要这么几步 :
    // 第一：获取电话服务管理器TelephonyManager manager = this.getSystemService(TELEPHONY_SERVICE);
    // 第二：通过TelephonyManager注册我们要监听的电话状态改变事件。manager.listen(new MyPhoneStateListener(),
                    // PhoneStateListener.LISTEN_CALL_STATE);这里的PhoneStateListener.LISTEN_CALL_STATE就是我们想要
                    // 监听的状态改变事件，初次之外，还有很多其他事件哦。
    // 第三步：通过extends PhoneStateListener来定制自己的规则。将其对象传递给第二步作为参数。
    // 第四步：这一步很重要，那就是给应用添加权限。android.permission.READ_PHONE_STATE


            //设置一个监听器
        }

   /* PhoneStateListener listener=new PhoneStateListener(){

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d("ass","挂断");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("ass","接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d("ass","来电号码");

                    break;
            }
        }
    };*/
}

