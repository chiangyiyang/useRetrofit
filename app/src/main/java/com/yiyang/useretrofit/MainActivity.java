package com.yiyang.useretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        GitHubService service = retrofit.create(GitHubService.class);
//
//        Call<List<Repo>> repos = service.listRepos("octocat");
////        Call<List<Repo>> repos = service.listRepos("chiangyiyang");
//
//        //非同步呼叫
//        repos.enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                List<Repo> result = response.body();
//                for (Repo item:
//                     result) {
//                    Log.d("Github Data", "ID: " + item.id + "  Name: " + item.name + " Full Name: " + item.full_name);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Repo>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }


        //測試PHP網頁
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.58.18:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClassDBService service = retrofit.create(ClassDBService.class);
        Call<List<Student>> repos = service.getStudentData("1");

        //非同步呼叫
        repos.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                List<Student> result = response.body();
                for (Student item :
                        result) {
                    Log.d("ClassDB Data", "ID: " + item.cID + "  Name: "
                            + item.cName + " Email: " + item.cEmail);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


//
//interface GitHubService {
//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
//}
//
//class Repo {
//    int id;
//    String name;
//    String full_name;
//}


//http://localhost:8081/code/11-14_project/api/api_read_get.php?cID=1
interface ClassDBService {
    @GET("code/11-14_project/api/api_read_get.php")
    Call<List<Student>> getStudentData(@Query("cID") String cID);
}

class Student {
    int cID;
    String cName;
    String cEmail;
}