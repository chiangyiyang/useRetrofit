package com.yiyang.useretrofit;

import android.app.Application;

import java.util.List;

import retrofit2.Call;

public class MyApp extends Application {
    ClassDBService api_service;
    List<Student> allStudentData;

}
