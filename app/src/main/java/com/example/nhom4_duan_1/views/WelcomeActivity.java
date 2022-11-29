package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.UserOnline;
import com.example.nhom4_duan_1.models.Users;
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

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID = "idKey";
    public static final String LOGIN = "login";
    SharedPreferences sharedpreferences;

    String id, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedpreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        id = sharedpreferences.getString(ID,"");
        login = sharedpreferences.getString(LOGIN,"");

        System.out.println("id : " + id +"---- login" + login);
        Toast.makeText(this, "id : " + id +"---- login" + login, Toast.LENGTH_SHORT).show();

        if (!id.isEmpty() && !login.isEmpty()){
            getUser();
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

//        list = new ArrayList<>();
//        getUserOnline();

    }

    public void loadData(){
    }

    public void getUser(){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(id)){
                                    CountDownTimer timer = new CountDownTimer(3000,3000) {
                                        @Override
                                        public void onTick(long l) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            intent.putExtra("Id",id);
                                            intent.putExtra("Login",login);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }.start();
                                    break;
                                }
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
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