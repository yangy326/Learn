package com.example.yangyang.demo.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.util.DiffUtil;
import android.util.Log;


//import com.example.yangyang.demo.widget.DiffCallBack;

import com.example.yangyang.demo.widget.FollowCommentAdapter;
import com.example.yangyang.demo.widget.ViewHolder;



import java.io.IOException;

public class AudioService extends Service {

    Message message = null;

    boolean isPrepare = false;

    boolean flag = true ;


    static ViewHolder viewHolder;

    public static void setViewHolder(ViewHolder viewHolder) {
        viewHolder = viewHolder;
    }


    MediaPlayer mediaPlayer = new MediaPlayer();

    Runnable runnable;



    MyThead myThead ;

    public interface ChangeTime{
        void onChange(int time);
        void onAlltime(int time);
    }

    public static ChangeTime changeTime;


    public static void setChangeTime(ChangeTime changeTime) {
        AudioService.changeTime = changeTime;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mediaPlayer == null){
                        stopSelf();
                    }
                    else {
                        changeTime.onChange(mediaPlayer.getCurrentPosition());
                    }

                    /*if (mediaPlayer != null){
                        if (mediaPlayer.isPlaying()){

                        }
                    }*/

                }
            }
        }).start();






    }




    @Override
    public IBinder onBind(Intent intent) {

       initMediaplayer(intent.getStringExtra("path"));

        return  new AudioBinder();
    }



    public class AudioBinder extends Binder {



        public void pause(){
            mediaPlayer.pause();
        }

        public void restart(){
            mediaPlayer.start();
        }

        public void reset(String path) {
            mediaPlayer.stop();

            isPrepare = false;
            mediaPlayer.reset();
            initMediaplayer(path);


        }









    }
    public void initMediaplayer(String path){
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    isPrepare = true ;
                    int time = mp.getDuration();
                    changeTime.onAlltime(time);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();

        isPrepare = false;
        flag = false;
        super.onDestroy();






    }
    class MyThead extends Thread{
        @Override
        public void run() {

                if (isPrepare){
                    try {
                        Thread.sleep(1010);










                        // Log.d("AudioService",String.valueOf(message.arg1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }




            }
        }
    
}
