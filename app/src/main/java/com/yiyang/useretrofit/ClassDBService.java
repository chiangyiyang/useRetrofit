package com.yiyang.useretrofit;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ClassDBService {
    @GET("code/11-14_project/api/api_read_all_get.php")
    Call<List<Student>> getAllStudentData();

    @GET("code/11-14_project/api/api_del_get.php")
    Call<String> delStudentData(@Query("cID") String cID);
}
