package com.yiyang.useretrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_DEL = 0;
    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_MODIFY = 2;



    private ListView student_list;
    private MyAdapter myAdapter;

    //    private ArrayAdapter<String> dataAdapter;
    private List<Student> studentData;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_DEL) {
                String id = data.getStringExtra("id");
                ((MyApp) getApplicationContext()).api_service.delStudentData(id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        updateList();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            } else if (requestCode == REQUEST_ADD) {
                ((MyApp) getApplicationContext()).api_service.addStudentData(
                        data.getStringExtra("name"),
                        data.getStringExtra("gender"),
                        data.getStringExtra("birthday"),
                        data.getStringExtra("email"),
                        data.getStringExtra("tel"),
                        data.getStringExtra("addr")
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        updateList();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            } else if (requestCode == REQUEST_MODIFY) {
                ((MyApp) getApplicationContext()).api_service.modifyStudentData(
                        data.getStringExtra("id"),
                        data.getStringExtra("name"),
                        data.getStringExtra("gender"),
                        data.getStringExtra("birthday"),
                        data.getStringExtra("email"),
                        data.getStringExtra("tel"),
                        data.getStringExtra("addr")
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        updateList();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        }
        updateList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student_list = (ListView) findViewById(R.id.lstData);

        studentData = new ArrayList<Student>();
//        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        student_list.setAdapter(dataAdapter);

        myAdapter = new MyAdapter(this);
        student_list.setAdapter(myAdapter);


        //Update button
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateList();
            }
        });

        //Add button
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);

                intent.putExtra("name", "user1");
                intent.putExtra("gender", "M");
                intent.putExtra("birthday", "2016-11-25");
                intent.putExtra("email", "user1@classroom.com");
                intent.putExtra("tel", "0912345678");
                intent.putExtra("addr", "somewhere");

                startActivityForResult(intent, REQUEST_ADD);
            }
        });

//        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(MainActivity.this, dataAdapter.getItem(i), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, ModifyActivity.class);
//
//                intent.putExtra("id", String.valueOf(studentData.get(i).cID));
//                intent.putExtra("name", studentData.get(i).cName);
//                intent.putExtra("gender", studentData.get(i).cSex);
//                intent.putExtra("birthday", studentData.get(i).cBirthday);
//                intent.putExtra("email", studentData.get(i).cEmail);
//                intent.putExtra("tel", studentData.get(i).cPhone);
//                intent.putExtra("addr", studentData.get(i).cAddr);
//
//                startActivityForResult(intent, REQUEST_MODIFY);
//            }
//        };
//
//        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, DeleteActivity.class);
//
//                intent.putExtra("id", studentData.get(i).cID);
//                intent.putExtra("name", studentData.get(i).cName);
//                intent.putExtra("gender", studentData.get(i).cSex);
//                intent.putExtra("birthday", studentData.get(i).cBirthday);
//                intent.putExtra("email", studentData.get(i).cEmail);
//                intent.putExtra("tel", studentData.get(i).cPhone);
//                intent.putExtra("addr", studentData.get(i).cAddr);
//
//                startActivityForResult(intent, REQUEST_DEL);
//                return false;
//            }
//        };
//
//        student_list.setOnItemClickListener(onItemClickListener);
//        student_list.setOnItemLongClickListener(onItemLongClickListener);


        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.58.18:8081/")      //for emulator
                .baseUrl("http://192.168.43.233:8081/")   //for real device
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        api_service = retrofit.create(ClassDBService.class);
        ((MyApp) getApplicationContext()).api_service = retrofit.create(ClassDBService.class);

        updateList();
    }

    private void updateList() {
        final Call<List<Student>> request = ((MyApp) getApplicationContext()).api_service.getAllStudentData();

        //非同步呼叫
        request.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
//                List<Student> result = response.body();
//                Iterator it = result.iterator();

                MyApp app = (MyApp) getApplicationContext();
                app.allStudentData = response.body();
                Iterator it = app.allStudentData.iterator();

//                dataAdapter.clear();
                studentData.clear();

                while (it.hasNext()) {
                    Student student = (Student) it.next();
//                    dataAdapter.add(student.cName);
                    studentData.add(student);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private class MyAdapter extends BaseAdapter{
        private final LayoutInflater myInflater;

        public MyAdapter(Context context){
            myInflater = LayoutInflater.from(context);
        }

        @Override

        public int getCount() {
            return studentData.size();
        }

        @Override
        public Object getItem(int i) {
            return studentData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View curView = myInflater.inflate(R.layout.my_list_view_layout,null);

            final TextView txtInfo = (TextView) curView.findViewById(R.id.txtInfo);
            txtInfo.setText(studentData.get(i).cName);

            Button btnDel = (Button) curView.findViewById(R.id.btnDel);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this, "DEL:" + txtInfo.getText().toString(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, DeleteActivity.class);

                    intent.putExtra("id", studentData.get(i).cID);
                    intent.putExtra("name", studentData.get(i).cName);
                    intent.putExtra("gender", studentData.get(i).cSex);
                    intent.putExtra("birthday", studentData.get(i).cBirthday);
                    intent.putExtra("email", studentData.get(i).cEmail);
                    intent.putExtra("tel", studentData.get(i).cPhone);
                    intent.putExtra("addr", studentData.get(i).cAddr);

                    startActivityForResult(intent, REQUEST_DEL);
                }
            });

            Button btnModify = (Button) curView.findViewById(R.id.btnModify);
            btnModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(MainActivity.this, "MODIFY:" + txtInfo.getText().toString(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ModifyActivity.class);

                    intent.putExtra("id", String.valueOf(studentData.get(i).cID));
                    intent.putExtra("name", studentData.get(i).cName);
                    intent.putExtra("gender", studentData.get(i).cSex);
                    intent.putExtra("birthday", studentData.get(i).cBirthday);
                    intent.putExtra("email", studentData.get(i).cEmail);
                    intent.putExtra("tel", studentData.get(i).cPhone);
                    intent.putExtra("addr", studentData.get(i).cAddr);

                    startActivityForResult(intent, REQUEST_MODIFY);
                }
            });

            return curView;
        }
    }
}
