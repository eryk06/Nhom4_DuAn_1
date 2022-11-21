package com.example.nhom4_duan_1.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Vouchers;

public class UsersActivity extends AppCompatActivity {
    LinearLayout llMO, llVouchers, llPM, llTP;
    ImageView ivUser , ivBackUser;
    private final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        llMO = (LinearLayout) findViewById(R.id.llMO);
        llVouchers = (LinearLayout) findViewById(R.id.llVouchers);
        llPM = (LinearLayout) findViewById(R.id.llPM);
        llTP = (LinearLayout) findViewById(R.id.llTP);
        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivBackUser = findViewById(R.id.ivBackUser);

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQ_CODE);
            }
        });

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
                Intent intent = new Intent(UsersActivity.this, VoucherActivity.class);
                startActivity(intent);
            }
        });

        llPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        llTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersActivity.this, TermsvsPrivacyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            if (requestCode==GALLERY_REQ_CODE){
                ivUser.setImageURI(data.getData());
            }

        }
    }
}