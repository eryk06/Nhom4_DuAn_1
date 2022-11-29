package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class LoginPhoneActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtNameNormal, edtPassNormal, edtRepassNormal, edtAddressNormal;
    TextView tvLoginNormal;
    String Phone;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID = "idKey";
    public static final String LOGIN = "login";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        sharedpreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        Intent intent = getIntent();
        Phone = intent.getStringExtra("Phone");

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

    private void saveData(String id, String Login) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ID, id);
        editor.putString(LOGIN,Login);
        editor.commit();
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
                        getUser(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Lỗi thêm User");
                    }
                });
    }

    public void getUser(String id){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> item = document.getData();
                                Users user = new Users();
                                if (document.getId().equals(id)){
                                    user.setId(document.getId());
                                    user.setName(item.get("Name").toString());
                                    user.setImage(item.get("Image").toString());
                                    user.setPass(item.get("Pass").toString());
                                    user.setPhone(item.get("Phone").toString());
                                    user.setAddress(item.get("Address").toString());
                                    saveData(user.getId(),"normal");
                                    System.out.println("Day la User " + user );
                                    System.out.println(user);
                                    break;
                                }
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
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