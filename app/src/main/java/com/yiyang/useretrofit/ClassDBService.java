package com.yiyang.useretrofit;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ClassDBService {
    @GET("code/11-14_project/api/api_read_all_get.php")
    Call<List<Student>> getAllStudentData();

//    @GET("code/11-14_project/api/api_del_get.php")
//    Call<ResponseBody> delStudentData(@Query("cID") String cID);
    @FormUrlEncoded
    @POST("code/11-14_project/api/api_del_post.php")
    Call<ResponseBody> delStudentData(@Field("cID") String cID);

//    @GET("code/11-14_project/api/api_add_get.php")
//    Call<ResponseBody> addStudentData(
//            @Query("cName") String cName,
//            @Query("cSex") String cSex,
//            @Query("cBirthday") String cBirthday,
//            @Query("cEmail") String cEmail,
//            @Query("cPhone") String cPhone,
//            @Query("cAddr") String cAddr
//    );

    @FormUrlEncoded
    @POST("code/11-14_project/api/api_add_post.php")
    Call<ResponseBody> addStudentData(
            @Field("cName") String cName,
            @Field("cSex") String cSex,
            @Field("cBirthday") String cBirthday,
            @Field("cEmail") String cEmail,
            @Field("cPhone") String cPhone,
            @Field("cAddr") String cAddr
    );

//    @GET("code/11-14_project/api/api_update_get.php")
//    Call<ResponseBody> modifyStudentData(
//            @Query("cID") String cID,
//            @Query("cName") String cName,
//            @Query("cSex") String cSex,
//            @Query("cBirthday") String cBirthday,
//            @Query("cEmail") String cEmail,
//            @Query("cPhone") String cPhone,
//            @Query("cAddr") String cAddr
//    );

    @FormUrlEncoded
    @POST("code/11-14_project/api/api_update_post.php")
    Call<ResponseBody> modifyStudentData(
            @Field("cID") String cID,
            @Field("cName") String cName,
            @Field("cSex") String cSex,
            @Field("cBirthday") String cBirthday,
            @Field("cEmail") String cEmail,
            @Field("cPhone") String cPhone,
            @Field("cAddr") String cAddr
    );


    @Multipart
    @POST("code/11-14_project/file_upload/uploadFile.php")
    Call<ResponseBody> uploadFile(@Part("description") RequestBody description,
                                  @Part MultipartBody.Part file);
}
