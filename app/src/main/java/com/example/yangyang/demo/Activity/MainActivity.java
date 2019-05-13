package com.example.yangyang.demo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangyang.demo.Callback.OnLoadCallbackListener;
import com.example.yangyang.demo.LoginActivity;
import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.TestData.response.main.StudentResponse;
import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.example.yangyang.demo.net.netHelper;
//import com.example.yangyang.demo.widget.DiffCallBack;
import com.example.yangyang.demo.R;
import com.example.yangyang.demo.service.UploadFailService;
import com.example.yangyang.demo.widget.ContacterAdapter;
import com.example.yangyang.demo.widget.DiffCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener,OnLoadCallbackListener {

    private int page = 1 , size = 10;

    private RecyclerView recyclerView;

    private LinearLayout search;

    private netHelper.AccountHelper accountHelper;

    private int i = 0;

    private static String UserId;

    private int location = 0;



    ContacterAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Student> list = new ArrayList<>();

    private List<Student> mlist = new ArrayList<>();

    private List<Student> newlist = new ArrayList<>();

    private List<Student> mnewlist = new ArrayList<>();

    private TextView signout;

    private static final int H_CODE_UPDATE = 1;
    private List<Student> mNewDatas;//增加一个变量暂存newList
    /*private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    //取出Result
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    diffResult.dispatchUpdatesTo(adapter);
                    //别忘了将新数据给Adapter

                    list = newlist;
                    adapter.set(list);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("isCheckLogin",MODE_PRIVATE);
        UserId = sharedPreferences.getString("userId",null);


        //initStudent();
        initWidget();
        String deviceId = DeviceIdUtil.getDeviceId(this);
        Log.d("sdfsdgdhhfg",deviceId);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();

                editor.putString("userId",null);
                editor.putString("accessToken",null);
                editor.apply();
                Intent intent = new Intent(v.getContext(),LoginActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });

        accountHelper= new netHelper.AccountHelper(this);
        accountHelper.getStudent(page,size);
        Intent intent = new Intent(this, UploadFailService.class);
        startService(intent);

    }

    private void initWidget(){

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        signout = (TextView)findViewById(R.id.sign_out);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new FootScrollListener() {
            @Override
            void myLoad() {
                adapter.setLoadstate(1);
                Toast.makeText(MainActivity.this, "sdfasdf", Toast.LENGTH_SHORT).show();





            }
        });


        search = (LinearLayout) findViewById(R.id.search_layout);
        search.setOnClickListener(this);



    }




    @Override
    public void onRefresh() {
        accountHelper.getStudent(1,10);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_layout:
                Intent intent = new Intent(this,SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) list);
                intent.putExtras(bundle);
                startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MAINActivity","onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        netHelper.AccountHelper.setOnLoadCallbackListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAINActivity","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MAINActivity","onPause");
    }

    @Override
    public void onLoadCallbackSuccess(Object o) {
        StudentResponse studentResponse = (StudentResponse) o;
        if (studentResponse.getCode() == 701){
            Toast.makeText(this, "7天自动登陆也过期，请重新登陆", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor= getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
            editor.putString("accessToken",null);
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (studentResponse.getCode() == 703){
            Toast.makeText(this, "账户不匹配，请重新登陆", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor= getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
            editor.putString("accessToken",null);
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (studentResponse.getCode() == 200){
            if (i == 0){
                list = studentResponse.getList();

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter =  new ContacterAdapter(list);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
                i++;
            }
            else {
                newlist = studentResponse.getList();
                list = newlist;
                adapter.set(list);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                 /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(list, newlist), true);
                        Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                        message.obj = diffResult;//obj存放DiffResult
                        message.sendToTarget();





                    }
                }).start();*/

            }



        }
        else {

            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();

        }








    }

    @Override
    public void onLoadCallbackFail() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();

    }
    abstract class FootScrollListener extends RecyclerView.OnScrollListener{
        private boolean isScrollUp=false;
        @Override    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            /*newState表示当前滑动的状态        SCROLL_STATE_IDLE:不滑动        SCROLL_STATE_DRAGGING;滑动（手指在屏幕上）        SCROLL_STATE_SETTLING;滑动（手指移开屏幕）        */
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();        // 当不滑动时
             if (newState == RecyclerView.SCROLL_STATE_IDLE) {            //返回最后一个完成可见视图位置
                  int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                  int itemCount = manager.getItemCount();            // 判断是否滑动到了最后一个item，并且是向上滑动
                  if (lastItemPosition == (itemCount - 1) && isScrollUp) {
                      // 加载更多
                        myLoad();
                  }
             }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            isScrollUp = (dy > 0);
        }
        abstract void myLoad();
    }





}
