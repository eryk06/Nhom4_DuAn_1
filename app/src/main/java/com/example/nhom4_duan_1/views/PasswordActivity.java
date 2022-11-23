package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity {
    EditText edtLogin;
    LinearLayout lnErr,lnOkLogin;
    String Id, Login, Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        edtLogin = findViewById(R.id.edtLoginPassword);
        lnErr = findViewById(R.id.lnErr);
        lnOkLogin = findViewById(R.id.lnOkLogin);

        lnErr.setVisibility(View.GONE);

        Intent intent = getIntent();
        Id = intent.getStringExtra("Id");
        Login = intent.getStringExtra("Login");
        Pass = intent.getStringExtra("Pass");

        lnOkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtLogin.getText().toString().trim();
                if (pass.length() >0){
                    if (pass.equals(Pass)){
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        intent.putExtra("Id",Id);
                        intent.putExtra("Login","normal");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        lnErr.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Enter your pass", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}