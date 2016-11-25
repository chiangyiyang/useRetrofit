package com.yiyang.useretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = this.getIntent();
        ((EditText) findViewById(R.id.edtName)).setText(intent.getStringExtra("name"));
        ((EditText) findViewById(R.id.edtGender)).setText(intent.getStringExtra("gender"));
        ((EditText) findViewById(R.id.edtBirthday)).setText(intent.getStringExtra("birthday"));
        ((EditText) findViewById(R.id.edtEmail)).setText(intent.getStringExtra("email"));
        ((EditText) findViewById(R.id.edtTel)).setText(intent.getStringExtra("tel"));
        ((EditText) findViewById(R.id.edtAddr)).setText(intent.getStringExtra("addr"));

        Button btnCancel = (Button) findViewById(R.id.btnCancelNew);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);  //設置要回傳MainActivity的資料，並設置requestCode給予訊息RESULT_OK
                finish();
            }
        });

        Button btnAdd = (Button) findViewById(R.id.btnAddNew);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.putExtra("name", ((EditText) findViewById(R.id.edtName)).getText().toString() );
                intent.putExtra("gender", ((EditText) findViewById(R.id.edtGender)).getText().toString());
                intent.putExtra("birthday", ((EditText) findViewById(R.id.edtBirthday)).getText().toString());
                intent.putExtra("email", ((EditText) findViewById(R.id.edtEmail)).getText().toString());
                intent.putExtra("tel", ((EditText) findViewById(R.id.edtTel)).getText().toString());
                intent.putExtra("addr", ((EditText) findViewById(R.id.edtAddr)).getText().toString());
                setResult(RESULT_OK, intent);  //設置要回傳MainActivity的資料，並設置requestCode給予訊息RESULT_OK
                finish();
            }
        });
    }
}
