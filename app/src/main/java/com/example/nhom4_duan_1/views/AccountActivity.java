package com.example.nhom4_duan_1.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.nhom4_duan_1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    CircleImageView ciPic;
    private final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ciPic = (CircleImageView) findViewById(R.id.ciPic);

        ciPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            if (requestCode==GALLERY_REQ_CODE){
                ciPic.setImageURI(data.getData());
            }

        }
    }
}