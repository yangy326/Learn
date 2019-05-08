package com.example.yangyang.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.TestData.response.log.WordData;
import com.example.yangyang.demo.db.table.PushFailed;
import com.example.yangyang.demo.db.table.PushFailed_Table;
import com.example.yangyang.demo.failback.Audioback;
import com.example.yangyang.demo.failback.Completeback;
import com.example.yangyang.demo.failback.Ossback;
import com.example.yangyang.demo.failback.Wordback;
import com.example.yangyang.demo.net.againNetHelper;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadFailService extends Service implements Audioback, Wordback, Ossback ,Completeback {
    List<PushFailed> faileds = new ArrayList<>();
    int i = 0;
    int length;
    int order;
    String fileName;
    againNetHelper helper;
    int recordId;
    @Override
    public void onCreate() {
        super.onCreate();
         helper = new againNetHelper(this);
        helper.setAudioback(this);
        helper.setOssback(this);
        helper.setWordback(this);
        helper.setCompleteback(this);


        faileds = SQLite.select()
                .from(PushFailed.class)
                .where()
                 .orderBy(PushFailed_Table.id,true)//按照升序
                // .limit(5)//限制条数
                .queryList();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         length = faileds.size();
        while (i > length){
            PushFailed pushFailed = faileds.get(i);
            order = pushFailed.getId();
            WordConstruct wordConstruct = pushFailed.build();
            helper.addWord(MyApp.deviceId,wordConstruct);





        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLoadAudioSuccess(Object o) throws IOException {
        RspLog rspLog = (RspLog) o ;

        String url = rspLog.getData().getUploadUrl() ;

        faileds.get(order).setFileUrl(rspLog.getData().getFileUrl());
        faileds.get(order).setUploadUrl(rspLog.getData().getUploadUrl());
        faileds.get(order).setUploadUrl(rspLog.getData().getContentType());
        faileds.get(order).setType(3);
        File file = new File(fileName);

        String contentType = rspLog.getData().getContentType();
        helper.updateOss(url,file,contentType);

    }

    @Override
    public void onLoadAudiokFail() {
        faileds.get(order).setType(1);

    }

    @Override
    public void onLoadWordSuccess(Object o) {
        WordData wordData = (WordData) o;
        if (wordData.getCode() == 200){

                recordId = wordData.getData();
                faileds.get(order).setRecordId(recordId);
                faileds.get(order).setType(2);
                helper.updateAudio(MyApp.deviceId,faileds.get(order).getFilename());

        }


    }

    @Override
    public void onLoadWordkFail(Object o) {
        i = length + 1;

    }

    @Override
    public void onLoadOssSuccess() {
        helper.updateCompelete(MyApp.deviceId,recordId,2,faileds.get(order).getFileUrl());

    }

    @Override
    public void onLoadOssFail() {

    }

    @Override
    public void onLoadCompleteSuccess() {


    }

    @Override
    public void onLoadCompleteSuccess(Object o) {
        RspLog rspLog = (RspLog) o;

        if (rspLog.getCode() == 200){

            Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
            faileds.remove(order);
            order ++;

        }
        else {
            Toast.makeText(this, "最终上传服务器失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoadCompleteFail() {

    }
}
