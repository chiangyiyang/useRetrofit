package com.yiyang.useretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteActivity extends AppCompatActivity {

    private Button btnCancel;
    private String cID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        Intent intent = this.getIntent();


        cID = String.valueOf(intent.getIntExtra("id", 0));
        ((TextView) findViewById(R.id.txtID)).setText(cID);
        ((TextView) findViewById(R.id.txtName)).setText(intent.getStringExtra("name"));
        ((TextView) findViewById(R.id.txtGender)).setText(intent.getStringExtra("gender"));
        ((TextView) findViewById(R.id.txtBirthday)).setText(intent.getStringExtra("birthday"));
        ((TextView) findViewById(R.id.txtEmail)).setText(intent.getStringExtra("email"));
        ((TextView) findViewById(R.id.txtTel)).setText(intent.getStringExtra("tel"));
        ((TextView) findViewById(R.id.txtAddr)).setText(intent.getStringExtra("addr"));


        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);  //設置要回傳MainActivity的資料，並設置requestCode給予訊息RESULT_OK
                finish();
            }
        });

        Button btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                intent.putExtra("id", cID);
                setResult(RESULT_OK, intent);  //設置要回傳MainActivity的資料，並設置requestCode給予訊息RESULT_OK
                finish();
            }
        });
    }
}
