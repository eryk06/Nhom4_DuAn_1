package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.UserOnline;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<UserOnline> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        list = new ArrayList<>();
        getUserOnline();

    }

    public void getUserOnline(){
        db.collection("UserOnline")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i+=1;
                                Map<String, Object> item = document.getData();
                                UserOnline users = new UserOnline();
                                users.setId(item.get("Id_User").toString());
                                list.add(users);
                                System.out.println(i + " ---" + list.get(list.size()-1));
                            }
                            if (list.size() >1){
                                for (UserOnline lst: list) {
                                    if (lst.getId().equals("123") == false) {
                                        CountDownTimer timer = new CountDownTimer(3000,3000) {
                                            @Override
                                            public void onTick(long l) {
                                            }

                                            @Override
                                            public void onFinish() {
                                                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                                intent.putExtra("Id",lst.getId());
                                                System.out.println("idUser: " + lst.getId());
                                                startActivity(intent);
                                                finish();
                                            }
                                        }.start();
                                    }
                                }
                            }
                            else {
                                CountDownTimer timer = new CountDownTimer(3000,3000) {
                                    @Override
                                    public void onTick(long l) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        Intent intent = new Intent(WelcomeActivity.this, HiActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }.start();
                            }
                        } else {
                        Log.w(">>>TAG", "Error getting documents.", task.getException());
                    }
                }
            });
    }
}