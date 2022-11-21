package com.example.nhom4_duan_1.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Vouchers;

public class UsersActivity extends AppCompatActivity {
    LinearLayout llMO, llVouchers, llPM, llTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        llMO = (LinearLayout) findViewById(R.id.llMO);
        llVouchers = (LinearLayout) findViewById(R.id.llVouchers);
        llPM = (LinearLayout) findViewById(R.id.llPM);
        llTP = (LinearLayout) findViewById(R.id.llTP);

        llMO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, OderActivity.class);
                startActivity(intent);
            }
        });

        llVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, OderActivity.class);
                startActivity(intent);
            }
        });

    }
}