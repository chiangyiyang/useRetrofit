package com.yiyang.useretrofit;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ClassDBService {
    @GET("code/11-14_project/api/api_read_all_get.php")
    Call<List<Student>> getAllStudentData();
}
