package com.example.yangyang.demo.Activity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangyang.demo.Callback.OnLoadCallbackListener;
import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.response.follow.FollowRecord;
import com.example.yangyang.demo.TestData.response.follow.FollowRsp;
import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.example.yangyang.demo.net.netHelper;
import com.example.yangyang.demo.service.AudioService;
import com.example.yangyang.demo.service.MyService;
import com.example.yangyang.demo.widget.FollowCommentAdapter;

import java.util.List;

public class LogAcitivity extends AppCompatActivity implements OnLoadCallbackListener, View.OnClickListener {
    RecyclerView recyclerView;

    FollowCommentAdapter adapter;

    private TextView studentName,className, studentId ,openClass,takeClass;

    private int id ;
    private Long teacherGroup;

    private ImageView write,call,back;

    private ProgressBar takeProgressBar , openProgressBar;


    private String name ,Class, phone ;

















    RecyclerView.ViewHolder viewHolder;

    private AudioService.AudioBinder musicControl;

    private List<FollowRecord> list ;




   // private List<follow> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_acitivity);
        initWidget();
       // initfollow();

        recyclerView = (RecyclerView)findViewById(R.id.log_recycleView);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        //adapter =  new FollowCommentAdapter(list,this);
        //recyclerView.setAdapter(adapter);
       // netHelper.AccountHelper helper = new netHelper.AccountHelper(this,this);
        netHelper.AccountHelper.setOnLoadCallbackListener(this);
        netHelper.AccountHelper accountHelper= new netHelper.AccountHelper(this);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("studentName");
        Log.d("sdafs",name);
        id = bundle.getInt("studentId");
        Class = bundle.getString("studentGroup");
        phone = bundle.getString("phone");
        teacherGroup =  bundle.getLong("teacherGroup");

        studentName.setText(name);
        studentId.setText(String.valueOf(id));
        className.setText(Class);

        accountHelper.getfollow(MyApp.deviceId,id,name,Class);




        //AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onLoadCallbackSuccess(Object o) {
        FollowRsp followRsp = (FollowRsp) o;

        openClass.setText("也开课"+String.valueOf(followRsp.getData().getOpenCourseNum()));
        takeClass.setText("也消课"+String.valueOf(followRsp.getData().getTakeCourseNum()));
        takeProgressBar.setMax(followRsp.getData().getCourseNum());
        takeProgressBar.setProgress(followRsp.getData().getTakeCourseNum());
        openProgressBar.setMax(followRsp.getData().getCourseNum());
        openProgressBar.setProgress(followRsp.getData().getOpenCourseNum());


        list = followRsp.getData().getRecordVOS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =  new FollowCommentAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoadCallbackFail() {

    }



    private void initWidget() {
        takeProgressBar = (ProgressBar)findViewById(R.id.pgbar_log_take);
        openProgressBar = (ProgressBar)findViewById(R.id.pgbar_log_open);
        studentName = (TextView)findViewById(R.id.txt_log_studentname);
        studentId = (TextView)findViewById(R.id.txt_log_studentid);
        className = (TextView)findViewById(R.id.txt_log_classname);
        openClass = (TextView)findViewById(R.id.txt_log_openclass);
        takeClass = (TextView)findViewById(R.id.txt_log_takeclass);
        write = (ImageView) findViewById(R.id.img_log_write);
        call = (ImageView) findViewById(R.id.img_log_call);
        back = (ImageView) findViewById(R.id.img_log_back);
        call.setOnClickListener(this);
        write.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_log_back:
                finish();
                break;
            case R.id.img_log_call:
                Intent intent3 = new Intent(this, MyService.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userId",id);
                bundle.putString("group",Class);
                bundle.putString("studentName",name);
                bundle.putString("userPhoneNumber",phone);
                bundle.putLong("teacherGroup",teacherGroup);


                intent3.putExtras(bundle);
                startService(intent3);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.img_log_write:
                Intent intent1 = new Intent(this,FollowActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("userId",id);
                bundle1.putString("group",Class);
                bundle1.putString("studentName",name);
                bundle1.putString("userPhoneNumber",phone);
                bundle1.putBoolean("isConnected",false);
                bundle1.putInt("time",0);
                bundle1.putLong("teacherGroup",teacherGroup);
                intent1.putExtras(bundle1);
                startActivity(intent1);

        }
    }

    @Override
    protected  void onStop() {
        if (FollowCommentAdapter.played){
            FollowCommentAdapter.played = false;
            unbindService(adapter.getServiceConnection());
        }
        //

        super.onStop();

    }
    @Override
    protected void onStart() {
        super.onStart();
        netHelper.AccountHelper.setOnLoadCallbackListener(this);
    }

}
