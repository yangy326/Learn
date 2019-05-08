package com.example.yangyang.demo.net.netApi;

import com.example.yangyang.demo.TestData.request.WordConstruct;
import com.example.yangyang.demo.TestData.response.log.RspLog;
import com.example.yangyang.demo.TestData.response.log.WordData;
import com.example.yangyang.demo.TestData.response.follow.FollowRsp;
import com.example.yangyang.demo.TestData.response.login.RspModele;
import com.example.yangyang.demo.TestData.response.login.Data;
import com.example.yangyang.demo.TestData.response.main.StudentResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServer {


    @GET("v1/call/login")
    rx.Observable<RspModele<Data>> accountLogin(@Query("account") String account, @Query("password") String password, @Query("deviceId") String deviceId);



    @GET("v1/call/callrecord")
    rx.Observable<StudentResponse> getStudent(@Query("deviceId") String deviceId);

    @POST("/v1/call/callrecorddetails")
    rx.Observable<FollowRsp> getFollow(@Query("deviceId") String deviceId, @Query("studentId") Integer studentId, @Query("studentName") String studentName, @Query("studentGroup") String studentGroup);

    @POST("/v1/call/callrecord/add")
    rx.Observable<WordData> addWord(@Query("deviceId") String deviceId, @Body WordConstruct wordConstruct);



    @POST("/v1/call/callrecord/Audioback")
    rx.Observable<RspLog> updateAudio(@Query("deviceId") String deviceId, @Query("fileName") String fileName);

//    @Multipart
//    @Headers({"Content-Type:Audioback/mp3"})
    @PUT()
    rx.Observable<ResponseBody> updateOss(@Url String url , @Body RequestBody fileBytes);
//    rx.Observable<ResponseBody> updateOss(@Header("header") String header ,@Url String url ,@Part MultipartBody.Part body);

    @POST("/v1/call/callrecord/update")
    rx.Observable<RspLog> updateCompelete(@Query("deviceId") String deviceId,@Query("id") int  id,@Query("status") int  status,@Query("url") String url);





}
