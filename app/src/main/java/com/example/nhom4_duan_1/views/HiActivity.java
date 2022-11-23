package com.example.nhom4_duan_1.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;

public class HiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi);

    }

    public void getContinue(View view){
        Intent intent = new Intent(HiActivity.this, MainActivity.class);
        intent.putExtra("Id","0");
        startActivity(intent);
        finish();
    }
}