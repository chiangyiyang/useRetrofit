package com.yiyang.useretrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ClassDBService {
    @GET("code/11-14_project/api/api_read_all_get.php")
    Call<List<Student>> getAllStudentData();

    @GET("code/11-14_project/api/api_del_get.php")
    Call<ResponseBody> delStudentData(@Query("cID") String cID);

    @GET("code/11-14_project/api/api_add_get.php")
    Call<ResponseBody> addStudentData(
            @Query("cName") String cName,
            @Query("cSex") String cSex,
            @Query("cBirthday") String cBirthday,
            @Query("cEmail") String cEmail,
            @Query("cPhone") String cPhone,
            @Query("cAddr") String cAddr
    );

    @GET("code/11-14_project/api/api_update_get.php")
    Call<ResponseBody> modifyStudentData(
            @Query("cID") String cID,
            @Query("cName") String cName,
            @Query("cSex") String cSex,
            @Query("cBirthday") String cBirthday,
            @Query("cEmail") String cEmail,
            @Query("cPhone") String cPhone,
            @Query("cAddr") String cAddr
    );
}
