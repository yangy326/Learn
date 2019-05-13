package com.example.yangyang.demo.widget;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.response.follow.FollowRecord;
import com.example.yangyang.demo.service.AudioService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;


public class FollowCommentAdapter extends RecyclerView.Adapter<ViewHolder> implements AudioService.ChangeTime {








    private List<FollowRecord> list ;

    private boolean isplay = false , isFirst = true;

    private int judegPosition = -1;

    private ImageView lastImage;



    private static Context context;

    private AudioManager audioManager;

    private AudioService.AudioBinder musicControl;

    private  ViewHolder mviewHolder ;

    public static boolean played = false;



    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (AudioService.AudioBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public FollowCommentAdapter(List<FollowRecord> list, Context context ) {
        this.list = list;
        this.context = context;
        AudioService.setChangeTime(this);




        //AudioService.setChangeTime(this);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_follow,viewGroup,false);
        ViewHolder holder = new ViewHolder(view,context);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final FollowRecord followRecord = list.get(i);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(followRecord.getCreateTime() );
        String str = sdf.format(date);

        viewHolder.time.setText(str);
        viewHolder.username.setText(followRecord.getTeacherName());
        Log.d("sdafgsdgsdf", String.valueOf(followRecord.getIsConnected()));
        Log.d("sdafgsdgsdf", String.valueOf(followRecord.getAudioUrl()));
        if (followRecord.getIsConnected() == 0){


            if (followRecord.getTag() != null){
                viewHolder.label.setText(followRecord.getTag());
                viewHolder.comment.setText(followRecord.getWordRecord());
                viewHolder.media.setVisibility(View.GONE);


            }
            else {
                viewHolder.comment.setText("用户未接听");
                viewHolder.processtime.setVisibility(View.GONE);
                viewHolder.media.setVisibility(View.GONE);
                viewHolder.label.setVisibility(View.GONE);

            }





        }
        else {
            if (followRecord.getTag() != null){
                viewHolder.label.setText(followRecord.getTag());
                viewHolder.comment.setText(followRecord.getWordRecord());



            }
            else {

                viewHolder.processtime.setVisibility(View.GONE);

                viewHolder.label.setVisibility(View.GONE);

            }




        }
        //viewHolder.comment.setText("天青色等烟雨而我在等你天青色等烟雨而我在等你天青色等烟雨而我在等你天青色等烟雨而我在等你天青色等烟雨而我在等你天青色等烟雨而我在等你");

        //viewHolder.label.setVisibility(View.GONE);

        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sdafgsdgsdf", String.valueOf(followRecord.getAudioUrl()));
                played = true;
                if (judegPosition < 0) {
                    judegPosition = i;
                    Intent intent = new Intent(context, AudioService.class);
                    intent.putExtra("path", followRecord.getAudioUrl());
                    context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);

                    mviewHolder = viewHolder;
                    isplay = true;
                    viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.pause));
                    lastImage = viewHolder.play;

                } else {
                    if (judegPosition != i) {
                        lastImage.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
                        musicControl.reset(followRecord.getAudioUrl());
                        mviewHolder = viewHolder;
                        isplay = true;
                        viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.pause));
                        lastImage = viewHolder.play;
                        judegPosition = i;


                    } else {

                        if (isplay) {
                            musicControl.pause();
                            viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
                            isplay = false;


                        } else {
                            musicControl.restart();
                            viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.pause));
                            isplay = true;

                        }
                    }
                }

            }
        });


    }

        //});



    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        else {
            return list.size();
        }
    }

    @Override
    public void onChange(final int c) {

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mviewHolder == null){

                }
                else {
                    ShowTime(c,mviewHolder.audiotime);
                    mviewHolder.voice.setProgress(c);
                }



            }
        });


    }

    @Override
    public void onAlltime(final int time) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ShowTime(time,mviewHolder.alltime);
                mviewHolder.voice.setMax(time);

            }
        });
        ShowTime(time,mviewHolder.alltime);
        mviewHolder.voice.setMax(time);
    }

    private  void ShowTime(int time , TextView view){
        int showtime = time / 1000 ;
        int minute = showtime / 60 ;
        int second = showtime % 60 ;
        if (minute < 10 ){
            if (second < 10){
                view.setText("0"+String.valueOf(minute)+":"+"0"+String.valueOf(second));
            }
            else {
                view.setText("0"+String.valueOf(minute)+":"+String.valueOf(second));
            }
        }
        else {
            if (second < 10){
                view.setText(String.valueOf(minute)+":"+"0"+String.valueOf(second));
            }
            else {
                view.setText(String.valueOf(minute)+":"+String.valueOf(second));
            }
        }
    }


    public static Context getContext() {
        return context;
    }
}
 /* ;
                if (isFirst){

                    Intent intent = new Intent(context,AudioService.class);
                    intent.putExtra("path","/storage/emulated/0/MIUI/sound_recorder/call_rec/通话录音@183 0854 7877(18308547877)_20190419123402.mp3");
                    context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);

                    mviewHolder = viewHolder;
                    isplay = true;
                    viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.pause));
                    isFirst  = false;

                }
                else {
                    if (judegPosition == i){
                        if (isplay){
                            musicControl.pause();
                            viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
                            isplay = false ;


                        }
                        else {
                            musicControl.restart();
                            viewHolder.play.setImageDrawable(context.getResources().getDrawable(R.drawable.pause));
                            isplay = true ;

                        }

                    }
                    else {

                    }


                }*/
