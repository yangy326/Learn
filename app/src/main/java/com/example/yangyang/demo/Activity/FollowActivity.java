package com.example.yangyang.demo.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangyang.demo.Callback.OnAudioCallback;
import com.example.yangyang.demo.Callback.OnCompleteCallback;
import com.example.yangyang.demo.Callback.OnLoadCallbackListener;
import com.example.yangyang.demo.Callback.OnOssCallback;
import com.example.yangyang.demo.Callback.OnWordCallback;
import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.TestData.response.log.WordData;
import com.example.yangyang.demo.TestData.response.follow.FollowRsp;
import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.example.yangyang.demo.Utils.FileComparator;
import com.example.yangyang.demo.Utils.GetAudioPathUtil;
import com.example.yangyang.demo.db.table.PushFailed;
import com.example.yangyang.demo.db.table.PushFailed_Table;
import com.example.yangyang.demo.net.netHelper;
import com.example.yangyang.demo.service.MyService;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FollowActivity extends AppCompatActivity implements View.OnClickListener,OnLoadCallbackListener, OnWordCallback, OnCompleteCallback, OnOssCallback, OnAudioCallback {

    private String BackLable;

    private TextView lable = null;

    private TextView button1, button2, button3, button4, button5, button6;
    private Button submit;

    private TextView StudentName, ClassName, StudentId, OpenClass, CloseClass;

    private EditText Comment;

    private ImageView close;

    private File fileAudio;

    private boolean word , audio ,status;

    private ProgressBar takeProgressBar , openProgressBar;

    private int recordId;



    private boolean connected;

    private int time , userId;

    private String wordRecord,tag = "",userPhoneNumber ,fileName ;


    private String studentName ,group ,fileUrl;

    private int teacherGroup;

    private byte isConnected;

    netHelper.AccountHelper accountHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow);
        InitWidget();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);


        netHelper.AccountHelper.setOnLoadCallbackListener(this);
        netHelper.AccountHelper.setOnWordCallback(this);
        netHelper.AccountHelper.setOnAudioCallback(this);
        netHelper.AccountHelper.setOnOssCallback(this);
        netHelper.AccountHelper.setOnCompleteCallback(this);
        accountHelper= new netHelper.AccountHelper(this);





        studentName = getIntent().getExtras().getString("studentName");
        userId = getIntent().getExtras().getInt("userId");
        teacherGroup = (int) getIntent().getExtras().getLong("teacherGroup");
        userPhoneNumber = getIntent().getExtras().getString("userPhoneNumber");
        group = getIntent().getExtras().getString("group");
        time = getIntent().getExtras().getInt("time");
        connected = getIntent().getExtras().getBoolean("isConnected");
        StudentName.setText(studentName);
        StudentId.setText(String.valueOf(userId));
        ClassName.setText(group);
        accountHelper.getfollow(MyApp.deviceId,userId,studentName,group);
        Toast.makeText(this, String.valueOf(connected), Toast.LENGTH_SHORT).show();
        if (connected){
            isConnected = 1;

            File child = new File(GetAudioPathUtil.getAudioPath());
            File[] files = child.listFiles();

            List<File> fileList = new ArrayList<File>();
            for (int i = 0; i < files.length; i++) {
                fileList.add(files[i]);
            }
            Collections.sort(fileList, new FileComparator());
            fileName = fileList.get(0).getName();
            fileAudio = fileList.get(0);
        }
        else {
            isConnected = 0;
        }















    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private String LabelChange(TextView textView) {


        if (lable == null) {
            lable = textView;
            lable.setTextColor(0xFF00A9FF);
            return lable.getText().toString().trim();
        } else {
            if (textView == lable) {
                lable.setTextColor(0xFF373737);
                lable = null;
                return "";

            } else {
                lable.setTextColor(0xFF373737);
                lable = textView;
                lable.setTextColor(0xFF00A9FF);
                return lable.getText().toString().trim();

            }
        }



    }

    private void InitWidget() {
        button1 = (TextView) findViewById(R.id.btn_follow_afterclass);
        button2 = (TextView) findViewById(R.id.btn_follow_qingjia);
        button3 = (TextView) findViewById(R.id.btn_follow_xufei);
        button4 = (TextView) findViewById(R.id.btn_follow_shengji);
        button5 = (TextView) findViewById(R.id.btn_follow_goutong);
        button6 = (TextView) findViewById(R.id.btn_follow_qita);
        submit = (Button) findViewById(R.id.btn_follow_tijiao);
        takeProgressBar = (ProgressBar)findViewById(R.id.take_progressBar);
        openProgressBar = (ProgressBar)findViewById(R.id.open_progressBar);

        StudentName = (TextView) findViewById(R.id.txt_follow_studentname);
        ClassName = (TextView) findViewById(R.id.txt_follow_classname);
        StudentId = (TextView) findViewById(R.id.txt_follow_studentid);
        OpenClass = (TextView) findViewById(R.id.txt_follow_openclass);
        CloseClass = (TextView) findViewById(R.id.txt_follow_closeclass);

        Comment = (EditText) findViewById(R.id.edit_follow_comment);

        close = (ImageView)findViewById(R.id.img_follow_close);


        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button1.setOnClickListener(this);
        submit.setOnClickListener(this);
        close.setOnClickListener(this);


    }
    public void initWidgetShow(Student student){


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_follow_afterclass:
                tag = LabelChange(button1);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_qingjia:
                tag = LabelChange(button2);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_xufei:
                tag = LabelChange(button3);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_shengji:
                tag = LabelChange(button4);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_goutong:
                tag = LabelChange(button5);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_qita:
                tag = LabelChange(button6);
                Toast.makeText(this, BackLable, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_follow_tijiao:
                wordRecord = Comment.getText().toString().trim();
                Log.d("sadfsadfsdaf",wordRecord);

                if (tag.isEmpty()  && wordRecord.isEmpty()){
                    Toast.makeText(this, "请填写标签与记录", Toast.LENGTH_SHORT).show();
                }
                else if(!tag.isEmpty() && wordRecord.isEmpty()){
                    Toast.makeText(this, "别忘记写记录了呀", Toast.LENGTH_SHORT).show();
                }
                else if(tag.isEmpty() && !wordRecord.isEmpty()){
                    Toast.makeText(this, "你怎么能忘记标签了", Toast.LENGTH_SHORT).show();
                }
                else {


                    WordConstruct wordConstruct = new WordConstruct(userId,userPhoneNumber,group,teacherGroup,isConnected,time,wordRecord,tag);
                    /*PushFailed failedword = new PushFailed();
                    failedword.setUserId(wordConstruct.getUserId());
                    failedword.setCallDuration(wordConstruct.getCallDuration());
                    failedword.setTeacherGroup(wordConstruct.getTeacherGroup());
                    failedword.setUserGroup(wordConstruct.getUserGroup());
                    failedword.setUserPhoneNumber(wordConstruct.getUserPhoneNumber());
                    failedword.setIsConnected(wordConstruct.getIsConnected());
                    failedword.setTag(wordConstruct.getTag());
                    failedword.setWordRecord(wordConstruct.getWordRecord());
                    if (isConnected == 1){
                        failedword.setFilename(fileName);
                        failedword.setType(2);

                    }
                    else {
                        failedword.setType(1);
                    }
                    failedword.save();*/
                    accountHelper.addWord(MyApp.deviceId,wordConstruct);
                }






                break;
            case R.id.img_follow_close:
                 finish();
                 break;

        }




    }

    @Override
    public void onLoadCallbackSuccess(Object o) {


        FollowRsp followRsp = (FollowRsp) o;

        Log.d("sdafdasfsadfsda", String.valueOf(followRsp.getData().getTakeCourseNum()));

        Log.d("sdafdasfsadfsda", String.valueOf(followRsp.getData().getOpenCourseNum()));

        OpenClass.setText("也开课" + followRsp.getData().getOpenCourseNum());
        CloseClass.setText("也消课" + followRsp.getData().getTakeCourseNum());
        takeProgressBar.setMax(followRsp.getData().getCourseNum());
        takeProgressBar.setProgress(followRsp.getData().getTakeCourseNum());
        openProgressBar.setMax(followRsp.getData().getCourseNum());
        openProgressBar.setProgress(followRsp.getData().getOpenCourseNum());

        //todo 两个seekbar的显示





    }

    @Override
    public void onLoadCallbackFail() {


    }

    @Override
    public void onLoadWordSuccess(Object o) {

        WordData wordData = (WordData) o;
        if (wordData.getCode() == 200){
            if (connected){
                recordId = wordData.getData();


                accountHelper.updateAudio(MyApp.deviceId,fileName);
            }
        }

    }

    @Override
    public void onLoadWordkFail(Object o) {




        Toast.makeText(this, "上传文字Http失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadCompleteSuccess() {
        Toast.makeText(this, "上传成功了文字", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadCompleteSuccess(Object o) {
        RspLog rspLog = (RspLog) o;

        if (rspLog.getCode() == 200){

            Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "最终上传服务器失败", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onLoadCompleteFail() {
        Toast.makeText(this, "最终上传Http失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadOssSuccess() {
        accountHelper.updateCompelete(MyApp.deviceId,recordId,2,fileUrl);


    }

    @Override
    public void onLoadOssFail() {
        Toast.makeText(this, "上传阿里云Http失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoadAudioSuccess(Object o) throws IOException {
        RspLog rspLog = (RspLog) o ;

        String url = rspLog.getData().getUploadUrl() ;

        fileUrl = rspLog.getData().getFileUrl();

        String contentType = rspLog.getData().getContentType();
        accountHelper.updateOss(url,fileAudio,contentType);

    }

    @Override
    public void onLoadAudiokFail() {



        Toast.makeText(this, "上传音频文件Http失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
       // netHelper.AccountHelper.setOnLoadCallbackListener(null);


    }
}
