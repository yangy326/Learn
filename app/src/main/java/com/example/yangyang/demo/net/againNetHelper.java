package com.example.yangyang.demo.net;

import android.content.Context;
import android.util.Log;

import com.example.yangyang.demo.MyApp;
import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.failback.Audioback;
import com.example.yangyang.demo.failback.Completeback;
import com.example.yangyang.demo.failback.Ossback;
import com.example.yangyang.demo.failback.Wordback;
import com.example.yangyang.demo.net.netApi.ApiServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
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

public class againNetHelper {
    private Context context;



    private static Wordback wordback;

    private static Ossback ossback;

    private static Completeback completeback;


    public static void setCompleteback(Completeback completeback){
        againNetHelper.completeback = completeback;
    }




    public void setWordback(Wordback wordback) {
        againNetHelper.wordback = wordback;
    }

    public void setOssback(Ossback ossback) {
        againNetHelper.ossback = ossback;
    }

    public againNetHelper(Context context) {
        this.context = context;
    }

    public  void addWord(String deviceId, String fileName ,final WordConstruct wordConstruct){
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
        apiServer.addWord(deviceId,fileName,wordConstruct)
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
                        wordback.onLoadWordkFail(wordConstruct);


                    }

                    @Override
                    public void onNext(RspLog rspLog) {
//                            try {
//                               Log.d("asdfasdfda",rspLog.string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        if (rspLog.getCode() == 200){
                            if (isConnected == 0){
                                completeback.onLoadCompleteSuccess();
                            }
                            else {
                                wordback.onLoadWordSuccess(rspLog);
                            }
                        }
                        else {
                            wordback.onLoadTokenFail();
                        }
                    }
                });








    }
  /*  public void updateAudio(String deviceId, String fileName) {
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
                        audioback.onLoadAudiokFail();


                    }

                    @Override
                    public void onNext(RspLog rspLog) {

                        try {
                            audioback.onLoadAudioSuccess(rspLog);
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


                   // }
            //    });


  //  }
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
                        ossback.onLoadOssFail();


                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        if (code[0] == 200){
                            ossback.onLoadOssSuccess();
                        }
                        else {
                            ossback.onLoadOssFail();
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
                        completeback.onLoadCompleteFail();


                    }

                    @Override
                    public void onNext(RspLog rspLog) {
                        completeback.onLoadCompleteSuccess(rspLog);


                    }
                });


    }
}


