package com.yiyang.useretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DEL && resultCode == RESULT_OK) {
            String id = data.getStringExtra("id");
            api_service.delStudentData(id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    updateList();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }

        updateList();
    }

    private static final int REQUEST_DEL = 0;
    private ListView item_list;
    private ArrayAdapter<String> dataAdapter;
    private List<Student> students;
    public static ClassDBService api_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        students = new ArrayList<Student>();

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        item_list = (ListView) findViewById(R.id.lstData);
        item_list.setAdapter(dataAdapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, dataAdapter.getItem(i), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DeleteActivity.class);

                intent.putExtra("id", students.get(i).cID);
                intent.putExtra("name", students.get(i).cName);
                intent.putExtra("gender", students.get(i).cSex);
                intent.putExtra("birthday", students.get(i).cBirthday);
                intent.putExtra("email", students.get(i).cEmail);
                intent.putExtra("tel", students.get(i).cPhone);
                intent.putExtra("addr", students.get(i).cAddr);

                startActivityForResult(intent, REQUEST_DEL);
            }
        };

        item_list.setOnItemClickListener(onItemClickListener);


        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.58.18:8081/")      //for emulator
                .baseUrl("http://192.168.43.233:8081/")   //for real device
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api_service = retrofit.create(ClassDBService.class);

        updateList();
    }
    private void updateList() {
        final Call<List<Student>> repos = api_service.getAllStudentData();

        //非同步呼叫
        repos.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
//                List<Student> result = response.body();
//                Iterator it = result.iterator();

                MyApp app = (MyApp) getApplicationContext();
                app.students  = response.body();
                Iterator it = app.students.iterator();

                dataAdapter.clear();
                students.clear();

                while (it.hasNext()) {
                    Student student = (Student) it.next();
                    dataAdapter.add(student.cName);
                    students.add(student);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
