package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginEmailActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtNameGG,edtPhoneGG, edtAddressGG;
    TextView tvLoginOkk;
    String Pass, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        Intent intent = getIntent();
        Pass = intent.getStringExtra("Pass");
        Login = intent.getStringExtra("Login");

        edtNameGG = findViewById(R.id.edtNameGG);
        edtPhoneGG = findViewById(R.id.edtPhoneGG);
        edtAddressGG = findViewById(R.id.edtAddressGG);

        tvLoginOkk = findViewById(R.id.tvLoginOkk);
        tvLoginOkk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameGG.getText().toString().trim();
                String phone = edtPhoneGG.getText().toString().trim();
                String address = edtAddressGG.getText().toString().trim();
                if (name.length() > 0 || phone.length() >0 || address.length() >0){
                    addUser(name,phone,address);
                }
            }
        });
    }

    public void addUser(String name, String phone, String address){
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Pass", Pass);
        user.put("Phone", phone);
        user.put("Address", address);
        user.put("Image", "123");

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        addUserOnline(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Lỗi thêm User");
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
                        Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
                        intent.putExtra("Id",id);
                        intent.putExtra("Login","email");
                        startActivity(intent);
                        finish();
                        System.out.println("Thêm UserOnline thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Lỗi thêm UserOnline");
                    }
                });
    }
}