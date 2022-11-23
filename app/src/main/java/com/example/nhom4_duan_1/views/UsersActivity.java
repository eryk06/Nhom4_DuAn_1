package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.UserOnline;
import com.example.nhom4_duan_1.models.Users;
import com.example.nhom4_duan_1.models.Vouchers;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinearLayout llMO, llVouchers, llPM, llTP;
    ImageView ivBackUser;
    CircleImageView ciPic;
    TextView tvNameUser, tvPhoneNumber, tvLogin;
    String IdUser, Login;
    private final int GALLERY_REQ_CODE = 1000;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Intent intent = getIntent();
        IdUser = intent.getStringExtra("Id");
        Login = intent.getStringExtra("Login");
        System.out.println("Login : " + Login);

        user = new Users();

        llMO = (LinearLayout) findViewById(R.id.llMO);
        llVouchers = (LinearLayout) findViewById(R.id.llVouchers);
        llPM = (LinearLayout) findViewById(R.id.llPM);
        llTP = (LinearLayout) findViewById(R.id.llTP);

        ciPic = (CircleImageView) findViewById(R.id.ciPic);
        ivBackUser = findViewById(R.id.ivBackUser);

        tvNameUser = (TextView) findViewById(R.id.tvNameUser);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvLogin = findViewById(R.id.tvLogin);

        if (IdUser.equals("0")== false){
            tvNameUser.setVisibility(View.VISIBLE);
            tvPhoneNumber.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            getUser();
        }


        ImageView ivBackTerm = findViewById(R.id.ivBackUser);
        ivBackTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout linearUserClick = findViewById(R.id.linearUserClick);
        linearUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IdUser.equals("0")== false){
                    Intent intent = new Intent(UsersActivity.this, AccountActivity.class);
                    intent.putExtra("Id",IdUser);
                    intent.putExtra("Login",Login);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        ivBackTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ciPic.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("Id",IdUser);
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
    protected void onRestart() {
        super.onRestart();
        getUser();

    }

    public void getUser(){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> item = document.getData();
                                System.out.println(document.getId());
                                System.out.println(IdUser);
                                System.out.println(document.getId().equals(IdUser));
                                if (document.getId().equals(IdUser)){
                                    user.setId(document.getId());
                                    user.setName(item.get("Name").toString());
                                    user.setImage(item.get("Image").toString());
                                    user.setPass(item.get("Pass").toString());
                                    user.setPhone(item.get("Phone").toString());
                                    user.setAddress(item.get("Address").toString());
                                    System.out.println("Day la User " + user );

                                    Picasso.get()
                                            .load(user.getImage())
                                            .placeholder(R.drawable.android)
                                            .error(R.drawable.man)
                                            .fit()
                                            .into(ciPic);
                                    tvNameUser.setText(user.getName());
                                    tvPhoneNumber.setText(user.getPhone());
                                    break;
                                }
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
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