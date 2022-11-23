package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                        addUserOnline(Id);
                    }
                    else {
                        lnErr.setVisibility(View.VISIBLE);
                        CountDownTimer timer = new CountDownTimer(3000,3000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                lnErr.setVisibility(View.GONE);
                            }
                        }.start();

                    }
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Enter your pass", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUserOnline(String id){
        Map<String, Object> user = new HashMap<>();
        user.put("Id_User", id);

        db.collection("UserOnline")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        intent.putExtra("Id",id);
                        intent.putExtra("Login",Login);

                        startActivity(intent);
                        finish();
                        System.out.println("Thêm User thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Lỗi thêm User");
                    }
                });
    }
}