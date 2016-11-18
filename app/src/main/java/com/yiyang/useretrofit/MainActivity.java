package com.yiyang.useretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView item_list;
    private ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        item_list = (ListView) findViewById(R.id.lstData);
        item_list.setAdapter(dataAdapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Hello Toast", Toast.LENGTH_LONG).show();
            }
        };

        item_list.setOnItemClickListener(onItemClickListener);


        //測試PHP網頁
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.58.18:8081/")      //for emulator
                .baseUrl("http://192.168.43.233:8081/")   //for real device
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClassDBService service = retrofit.create(ClassDBService.class);
//        final Call<List<Student>> repos = service.getStudentData("1");
        final Call<List<Student>> repos = service.getAllStudentData();

        //非同步呼叫
        repos.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                List<Student> result = response.body();
                Iterator it = result.iterator();

                while (it.hasNext()) {
                    Student student = (Student) it.next();
                    dataAdapter.add(student.cName);
                }

            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
