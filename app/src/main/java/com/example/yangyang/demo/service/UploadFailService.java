package com.example.yangyang.demo.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import com.example.yangyang.demo.Activity.MainActivity;
import com.example.yangyang.demo.LoginActivity;
import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.db.table.PushFailed;
import com.example.yangyang.demo.db.table.PushFailed_Table;
import com.example.yangyang.demo.failback.Audioback;
import com.example.yangyang.demo.failback.Completeback;
import com.example.yangyang.demo.failback.Ossback;
import com.example.yangyang.demo.failback.Wordback;
import com.example.yangyang.demo.net.againNetHelper;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadFailService extends Service implements Ossback, Wordback, Completeback {
    List<PushFailed> faileds = new ArrayList<>();
    int i = 0  ;
    int length;
    int firstorder = -1;
    String fileName , fileUrl ,contentType , uploadUrl ;
    String teacherId;
    againNetHelper helper;
    int recordId;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("isCheckLogin",MODE_PRIVATE);
        teacherId = sharedPreferences.getString("userId",null);
        helper = new againNetHelper(this);
        helper.setOssback(this);
        helper.setWordback(this);
        helper.setCompleteback(this);
        faileds = SQLite.select()
                .from(PushFailed.class)
                .where(PushFailed_Table.TeacherId.eq(teacherId))
                 .orderBy(PushFailed_Table.id,true)//按照升序
                // .limit(5)//限制条数
                .queryList();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                length = faileds.size();
                if (length == 0){
                    Toast.makeText(this, "不需要重传", Toast.LENGTH_SHORT).show();
                    stopSelf();
                }
                else {
                    doThis();
                    Toast.makeText(this, "开始重传了", Toast.LENGTH_SHORT).show();
                 }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLoadOssSuccess() {
        faileds.get(i).setStatus(2);
        helper.updateCompelete(MyApp.deviceId,recordId,2,fileUrl);


    }

    @Override
    public void onLoadOssFail() {
        Toast.makeText(this, "重传OSS失败", Toast.LENGTH_SHORT).show();
        if (recordId != -1){
            faileds.get(i).setType(2);
            SQLite.update(PushFailed.class)
                    .set(PushFailed_Table.type.eq(2))
                    .where(PushFailed_Table.recordId.is(recordId));
        }

        stopSelf();

    }

    @Override
    public void onLoadWordSuccess(Object o) {
        RspLog rspLog = (RspLog) o;
        if (rspLog.getCode() == 200){
            String uploadUrl = rspLog.getData().getUploadUrl().getUploadUrl() ;

            fileUrl = rspLog.getData().getUploadUrl().getFileUrl();

             contentType = rspLog.getData().getUploadUrl().getContentType();

            recordId = rspLog.getData().getRecordId();

            recordId = rspLog.getData().getRecordId();
            firstorder = rspLog.getData().getRecordId();

            faileds.get(i).setUploadUrl(uploadUrl);
            faileds.get(i).setRecordId(recordId);
            faileds.get(i).setFileUrl(fileUrl);
            faileds.get(i).setContentType(contentType);
            File fileAudio = new File(fileName);

            try {
                helper.updateOss(uploadUrl,fileAudio,contentType);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "上传记录接口错误", Toast.LENGTH_SHORT).show();
            stopSelf();
        }

    }

    @Override
    public void onLoadWordkFail(Object o) {
        Toast.makeText(this, "重传记录失败", Toast.LENGTH_SHORT).show();
        stopSelf();
}

    @Override
    public void onLoadTokenFail() {
        Toast.makeText(this, "账户不匹配，请重新登陆", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor= getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
        editor.putString("accessToken",null);
        editor.apply();
        stopSelf();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onLoadCompleteSuccess() {
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        faileds.get(i).delete();
        faileds.remove(i);
        length -- ;
        if (i == length){

            stopSelf();
        }
        else {
            firstorder = -1;
            doThis();
        }


    }

    @Override
    public void onLoadCompleteSuccess(Object o) {
        RspLog rspLog = (RspLog) o;

        if (rspLog.getCode() == 200){

            faileds.get(i).delete();
            faileds.remove(i);
            length -- ;
            if (i == length){

                stopSelf();
            }
            else {
                firstorder = -1;
                doThis();
            }





        }


    }

    @Override
    public void onLoadCompleteFail() {
        Toast.makeText(this, "最后一步服务器错误", Toast.LENGTH_SHORT).show();
        stopSelf();

    }



  private void doThis(){

            PushFailed pushFailed = faileds.get(i);
            uploadUrl = pushFailed.getUploadUrl();
            contentType = pushFailed.getContentType();
            fileName = pushFailed.getFilename();
            fileUrl = pushFailed.getFileUrl();

            WordConstruct wordConstruct = pushFailed.build();
            if (faileds.get(i).getType() == 1){
               helper.addWord(MyApp.deviceId,fileName,wordConstruct);
            }
            else {
                File file = new File(fileName);
                try {
                    helper.updateOss(uploadUrl,file,contentType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





}
