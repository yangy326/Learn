package com.example.yangyang.demo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yangyang.demo.Activity.MainActivity;


import com.example.yangyang.demo.Callback.OnLoadCallbackListener;
import com.example.yangyang.demo.TestData.response.login.RspModele;
import com.example.yangyang.demo.TestData.response.login.Data;
import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.example.yangyang.demo.net.netHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements OnLoadCallbackListener, View.OnClickListener {
    private Intent intent;
    private EditText CMS_account , CMS_password;
    private Button login;

    private ProgressDialog dialog ;
    String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG
    };

    List<String> mPermissionList = new ArrayList<>();


    int mRequestCode = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        initPermission();

        netHelper.AccountHelper.setOnLoadCallbackListener(this);






        initWidget();

        editSetHint();










    }
    private void initPermission(){

        // 逐个判断是否还有未通过的权限
        for (int i = 0;i<permissions.length;i++){
            if (ContextCompat.checkSelfPermission(this,permissions[i])  != PackageManager.PERMISSION_GRANTED){
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }        //申请权限
        if (mPermissionList.size()>0){//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this,permissions,mRequestCode);
        }
        else {
            isCheckLogin();



        }
    }



    private void initWidget(){
        CMS_account = (EditText)findViewById(R.id.edit_account);

        CMS_password = (EditText)findViewById(R.id.edit_password);

        login = (Button)findViewById(R.id.btn_login);

        login.setOnClickListener(this);

    }
    private void editSetHint(){
        SpannableString ss = new SpannableString("请输入CMS账号");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14,true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        CMS_account.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失

        SpannableString s = new SpannableString("请输入CMS密码");
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan as = new AbsoluteSizeSpan(14,true);
        // 附加属性到文本
        s.setSpan(as, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        CMS_password.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }



    /**
     * 判断Token是否为空，为空则啥都不做，让其显示登陆界面，不为空则将登陆服务器
     */

    private void isCheckLogin(){
       //

        SharedPreferences sharedPreferences = getSharedPreferences("isCheckLogin",MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken",null);
       // Log.d("tosdafken",token);
        if (token != null){
            //String account = sharedPreferences.getString("account",null);
            //String password = sharedPreferences.getString("password",null);
            //String deviceId = DeviceIdUtil.getDeviceId(this);
            //netHelper.AccountHelper.login(account,password,deviceId);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            dialog.onStart();

        }


    }

    @Override
    public void onLoadCallbackSuccess(Object o) {

        dialog.cancel();
        RspModele<Data> rspModele = (RspModele<Data>) o;

        int code = rspModele.getCode();
        switch (code){

            case 200:
                SharedPreferences.Editor editor= getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
                String accessToken = rspModele.getData().getAccessToken();
                String userId = rspModele.getData().getUserId();
                editor.putString("userId",userId);
                editor.putString("accessToken",accessToken);
                editor.apply();

                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case 601:
                Toast.makeText(this, "账号不存在呀", Toast.LENGTH_SHORT).show();
                break;
            case 603:
                Toast.makeText(this, "密码错误呀", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "程序猿也不知道啥子错误呀", Toast.LENGTH_SHORT).show();
                break;
         }






        //editor.apply();




        
    }

    @Override
    public void onLoadCallbackFail() {



        dialog.cancel();
        Toast.makeText(this, "当前网络不佳呀", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean hasPermissionDismiss = false;
        if (mRequestCode==requestCode){

            for (int i = 0 ; i < grantResults.length ; i++){
                if (grantResults[i] == -1){

                    hasPermissionDismiss = true;
                    break;

                }
            }
            if (hasPermissionDismiss){

                Toast.makeText(getApplicationContext(), "滚吧", Toast.LENGTH_SHORT).show();
            }
            else {
                isCheckLogin();


            }

        }
    }

    @Override
    public void onClick(View v) {
        //e10adc3949ba59abbe56e057f20f883e
        // lijiatong
        String account = CMS_account.getText().toString().trim();

        String password = CMS_password.getText().toString().trim();



        netHelper.AccountHelper.login(account,password,MyApp.deviceId);

    }








   /* @Override
    public void Failed(int Res) {
        Toast.makeText(this, "不行啊", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void Success(UserCard userCard) {

        Toast.makeText(this, "可以的", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences("isCheckLogin",MODE_PRIVATE).edit();
        editor.putString("account",userCard.getAccount());
        editor.putString("password",userCard.getPassword());
        editor.putString("token",userCard.getToken());
        editor.apply();


    }*/

   /* @Override
    public void onClick(View v) {
        String account = CMS_account.getText().toString().trim();

        String password = CMS_password.getText().toString().trim();

        Login(account,password);

    }*/
}
