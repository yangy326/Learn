package com.example.yangyang.demo.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yangyang.demo.Callback.OnAudioCallback;
import com.example.yangyang.demo.Callback.OnCompleteCallback;
import com.example.yangyang.demo.Callback.OnLoadCallbackListener;
import com.example.yangyang.demo.Callback.OnOssCallback;
import com.example.yangyang.demo.Callback.OnWordCallback;
import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.TestData.response.log.WordData;
import com.example.yangyang.demo.TestData.response.follow.FollowRsp;
import com.example.yangyang.demo.TestData.response.login.Data;
import com.example.yangyang.demo.TestData.response.login.RspModele;
import com.example.yangyang.demo.TestData.response.main.StudentResponse;
import com.example.yangyang.demo.Utils.DeviceIdUtil;
import com.example.yangyang.demo.net.netApi.ApiServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class netHelper {
    public static class AccountHelper {

        String url;


        public AccountHelper(String url) {
            this.url = url;
        }

        /*
           网络回调给Activity
         */



        private static OnLoadCallbackListener onLoadCallbackListener;

        private static OnAudioCallback onAudioCallback;

        private static OnOssCallback onOssCallback;

        private static OnCompleteCallback onCompleteCallback;

        public static void setOnCompleteCallback(OnCompleteCallback onCompleteCallback) {
            AccountHelper.onCompleteCallback = onCompleteCallback;
        }

        public static void setOnOssCallback(OnOssCallback onOssCallback) {

            AccountHelper.onOssCallback = onOssCallback;
        }

        public static void setOnAudioCallback(OnAudioCallback onAudioCallback) {
            AccountHelper.onAudioCallback = onAudioCallback;
        }

        public static void setOnLoadCallbackListener(OnLoadCallbackListener onLoadCallbackListener) {
            AccountHelper.onLoadCallbackListener = onLoadCallbackListener;
        }

        private static OnWordCallback onWordCallback;

        public static void setOnWordCallback(OnWordCallback onWordCallback) {
            AccountHelper.onWordCallback = onWordCallback;
        }

        private Context context;


        public AccountHelper(Context context) {
            this.context = context;
        }

        public static void login(String account, String password, String deviceId) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();


            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.accountLogin(account, password, deviceId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RspModele<Data>>() {
                        @Override
                        public void onCompleted() {
                            //TODO 注销


                        }

                        @Override
                        public void onError(Throwable e) {

                            onLoadCallbackListener.onLoadCallbackFail();

                        }

                        @Override
                        public void onNext(RspModele<Data> user) {

                            onLoadCallbackListener.onLoadCallbackSuccess(user);


                        }
                    });


        }


        public void getStudent() {
            final String token = context.getSharedPreferences("isCheckLogin", MODE_PRIVATE).getString("accessToken", null);
            Log.d("accessToken", token);


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .addHeader("Authorization", token)
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);

                        }
                    })
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.getStudent(MyApp.deviceId)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {


                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<StudentResponse>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            onLoadCallbackListener.onLoadCallbackFail();



                        }

                        @Override
                        public void onNext(StudentResponse responseBody) {
                            onLoadCallbackListener.onLoadCallbackSuccess(responseBody);





                          /*  try {
                                Log.d("AccountHelper",String.valueOf(responseBody.string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/


                            // Toast.makeText(context, String.valueOf(followComment.getList().size()), Toast.LENGTH_SHORT).show();


                        }
                    });


        }

        public void getfollow(String deviceId, Integer studentId, String studentName, String studentGroup) {
            final String token = context.getSharedPreferences("isCheckLogin", MODE_PRIVATE).getString("accessToken", null);
            Log.d("accessToken", token);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .addHeader("Authorization", token)
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);

                        }
                    })
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.getFollow(deviceId, studentId, studentName, studentGroup)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {


                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FollowRsp>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, "阿里云", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onNext(FollowRsp followRsp) {

                            //Log.d("sdfasfdas", String.valueOf(followRsp.getData().getRecordVOS().size()));

                            onLoadCallbackListener.onLoadCallbackSuccess(followRsp);





                          /*  try {
                                Log.d("AccountHelper",String.valueOf(responseBody.string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/


                            // Toast.makeText(context, String.valueOf(followComment.getList().size()), Toast.LENGTH_SHORT).show();


                        }
                    });


        }

        public void updateAudio(String deviceId, String fileName) {
            final String token = context.getSharedPreferences("isCheckLogin", MODE_PRIVATE).getString("accessToken", null);
            Log.d("accessToken", token);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .addHeader("Authorization", token)
                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);

                        }
                    })
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.updateAudio(deviceId, fileName)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {


                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RspLog>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            onAudioCallback.onLoadAudiokFail();


                        }

                        @Override
                        public void onNext(RspLog rspLog) {
                            try {
                                onAudioCallback.onLoadAudioSuccess(rspLog);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Log.d("sdfasfdas", String.valueOf(followRsp.getData().getRecordVOS().size()));






                          /*  try {
                                Log.d("AccountHelper",String.valueOf(responseBody.string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/


                            // Toast.makeText(context, String.valueOf(followComment.getList().size()), Toast.LENGTH_SHORT).show();


                        }
                    });


        }

        public void updateOss(String url, File file, final String contentType) throws IOException {
            final int[] code = new int[1];


            OkHttpClient client = new OkHttpClient.Builder()

                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();

//                            request=request.newBuilder().addHeader("Content-Type",contentType.toString()).build();

                            Response response = chain.proceed(request);
                            if (response.code() == 200){
                                code[0] = 200;
                            }

                            return response;

                        }
                    })
                    .build();


            byte[] buffer = null;
            try {

                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                fis.close();
                bos.close();
                buffer = bos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            RequestBody body = RequestBody.create(MediaType.parse(contentType), buffer);
//            MultipartBody.Part part = MultipartBody.Part.create(body);
//            String type = "Content-Type:"+ contentType;


            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.updateOss(url, body)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {


                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            onOssCallback.onLoadOssFail();


                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {

                            if (code[0] == 200){
                                onOssCallback.onLoadOssSuccess();
                            }
                            else {
                                onOssCallback.onLoadOssFail();
                            }



                        }
                    });


        }

        public void updateCompelete(String deviceId, int id, int status, String url) {
            final String token = context.getSharedPreferences("isCheckLogin", MODE_PRIVATE).getString("accessToken", null);
            Log.d("accessToken", token);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .addHeader("Authorization", token)

                                    .method(original.method(), original.body())
                                    .build();

                            return chain.proceed(request);

                        }
                    })
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.updateCompelete("123", id, status, url)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {


                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RspLog>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {
                            onCompleteCallback.onLoadCompleteFail();


                        }

                        @Override
                        public void onNext(RspLog rspLog) {
                            onCompleteCallback.onLoadCompleteSuccess(rspLog);


                        }
                    });


        }
        public  void addWord(String deviceId, final WordConstruct wordConstruct){
           final byte isConnected = wordConstruct.getIsConnected();
            final String token = context.getSharedPreferences("isCheckLogin",MODE_PRIVATE).getString("accessToken",null);
            Log.d("accessToken",token);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .addHeader("Authorization",token)
                                    .addHeader("Content-Type","application/json")
                                    .method(original.method(),original.body())
                                    .build();

                            return chain.proceed(request);

                        }
                    })
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();


            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(MyApp.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            ApiServer apiServer = retrofit.create(ApiServer.class);
            apiServer.addWord(deviceId,wordConstruct)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {



                        }
                    })

                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<WordData>() {
                        @Override
                        public void onCompleted() {



                        }

                        @Override
                        public void onError(Throwable e) {
                            onWordCallback.onLoadWordkFail(wordConstruct);


                        }

                        @Override
                        public void onNext(WordData wordData) {
                            if (isConnected == 0){
                                onCompleteCallback.onLoadCompleteSuccess();
                            }
                            else {
                                onWordCallback.onLoadWordSuccess(wordData);
                            }





                        }
                    });








        }


    }
}
