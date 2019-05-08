package com.example.yangyang.demo.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangyang.demo.R;
import com.example.yangyang.demo.service.AudioService;


import cn.carbs.android.expandabletextview.library.ExpandableTextView;

import static android.content.Context.BIND_AUTO_CREATE;

public class ViewHolder extends RecyclerView.ViewHolder {




        //View view;
        TextView time,username,label,alltime ,audiotime;
        ExpandableTextView comment;
        TextView processtime;
        ImageView play;
        SeekBar voice;
        LinearLayout media ;

    private Messenger mActivityMessenger;

    /*public void showTime(int time){
        int showtime = time / 1000 ;
        int minute = showtime / 60 ;
        int second = showtime % 60 ;
        if (minute < 10 ){
            if (second < 10){
                processtime.setText("0"+String.valueOf(minute)+":"+"0"+String.valueOf(second));
            }
            else {
                processtime.setText("0"+String.valueOf(minute)+":"+String.valueOf(second));
            }
        }
        else {
            if (second < 10){
                processtime.setText(String.valueOf(minute)+":"+"0"+String.valueOf(second));
            }
            else {
                processtime.setText(String.valueOf(minute)+":"+String.valueOf(second));
            }
        }
    }*/




        public ViewHolder(@NonNull View itemView , final Context context) {
            super(itemView);


            //view = itemView;
            media = (LinearLayout)itemView.findViewById(R.id.media);
            username = (TextView)itemView.findViewById(R.id.txt_item_username);
            label = (TextView)itemView.findViewById(R.id.txt_item_label);
            time = (TextView)itemView.findViewById(R.id.txt_item_time);
            alltime = (TextView)itemView.findViewById(R.id.txt_audio_alltime);
            audiotime = (TextView)itemView.findViewById(R.id.txt_audio_time);
            comment = (ExpandableTextView)itemView.findViewById(R.id.expand_txt_item_comment);
            processtime = (TextView)itemView.findViewById(R.id.txt_audio_time);

            play = (ImageView)itemView.findViewById(R.id.img_item_play);

            voice = (SeekBar) itemView.findViewById(R.id.bar_audio_progress);











        }

       /* private  void ShowTime(int time){
            int showtime = time / 1000 ;
            int minute = showtime / 60 ;
            int second = showtime % 60 ;
            if (minute < 10 ){
                if (second < 10){
                    processtime.setText("0"+String.valueOf(minute)+":"+"0"+String.valueOf(second));
                }
                else {
                    processtime.setText("0"+String.valueOf(minute)+":"+String.valueOf(second));
                }
            }
            else {
                if (second < 10){
                    processtime.setText(String.valueOf(minute)+":"+"0"+String.valueOf(second));
                }
                else {
                    processtime.setText(String.valueOf(minute)+":"+String.valueOf(second));
                }
            }
        }*/




}
