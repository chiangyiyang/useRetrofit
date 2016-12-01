package com.yiyang.useretrofit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private String baseUrl;


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


    private void uploadFile(Uri fileUri) {
        // create uploadFile service client
        ClassDBService service = ((MyApp) getApplicationContext()).api_service;

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.uploadFile(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
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

        //Orientation Sensor
        Button btnOrientationSensor = (Button) findViewById(R.id.btnOrientationSensor);
        btnOrientationSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, OrientationSensorActivity.class);
                startActivity(intent);

            }
        });

        //Chart
        Button btnChart = (Button) findViewById(R.id.btnChart);
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SimpleXYPlotActivity.class);
                startActivity(intent);
            }
        });

        //Upload file test
        Button btnUploadFile = (Button) findViewById(R.id.btnUploadFile);
        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                uploadFile( Uri.parse ("file:///storage/emulated/0/Download/01.jpg"));
            }
        });

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


        baseUrl = "http://192.168.43.233:8081/";
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.58.18:8081/")      //for emulator
                .baseUrl(baseUrl)   //for real device
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

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View curView = myInflater.inflate(R.layout.my_list_view_layout,null);

            final TextView txtInfo = (TextView) curView.findViewById(R.id.txtInfo);
            txtInfo.setText(studentData.get(i).cName);

//            ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
//            Bitmap bmpLogo;
//            imgLogo.setImageBitmap(bmpLogo);
            // show The Image in a ImageView
            new DownloadImageTask((ImageView) curView.findViewById(R.id.imgLogo))
                    .execute(baseUrl + "code/11-14_project/image_io_test/sendImg.php?cID=" + studentData.get(i).cID);


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
