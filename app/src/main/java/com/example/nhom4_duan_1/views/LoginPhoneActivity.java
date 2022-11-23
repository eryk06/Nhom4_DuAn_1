package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class LoginPhoneActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtNameNormal, edtPassNormal, edtRepassNormal, edtAddressNormal;
    TextView tvLoginNormal;
    String Phone, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        Intent intent = getIntent();
        Phone = intent.getStringExtra("Phone");
        Login = intent.getStringExtra("Login");
        System.out.println("Login : " + Login);

        edtNameNormal = findViewById(R.id.edtNameNormal);
        edtPassNormal = findViewById(R.id.edtPassNormal);
        edtRepassNormal = findViewById(R.id.edtRepassNormal);
        edtAddressNormal = findViewById(R.id.edtAddressNormal);

        tvLoginNormal = findViewById(R.id.tvLoginNormal);
        tvLoginNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameNormal.getText().toString().trim();
                String pass = edtPassNormal.getText().toString().trim();
                String repass = edtRepassNormal.getText().toString().trim();
                String address = edtAddressNormal.getText().toString().trim();
                if (name.length() > 0 || pass.length() > 0 || repass.length() > 0 || address.length() > 0 ){
                    if (repass.equals(pass)){
                        addUser(name, pass, address);
                    }
                }
            }
        });
    }

    public void addUser(String name, String pass, String address){
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Pass", pass);
        user.put("Phone", Phone);
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
                        Intent intent = new Intent(LoginPhoneActivity.this, MainActivity.class);
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