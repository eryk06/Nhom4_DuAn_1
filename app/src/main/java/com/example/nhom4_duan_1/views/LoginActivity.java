package com.example.nhom4_duan_1.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;
    ArrayList<Users> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtSDT = (EditText) findViewById(R.id.edtSDT);
        Button btnContinue = (Button) findViewById(R.id.btnContinue);
        ImageView ivBackLogin = findViewById(R.id.ivBackLogin);
        ivBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
        getUser();
        signInButton = findViewById(R.id.btnGoogle);

        // google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
        // kiểm tra login google
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.d(">>>>>>TAG", "onCreate: Đã login Google");
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                googleLauncher.launch(intent);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtSDT.getText().toString().trim();
                if (sdt.length() >0){
                    int check = 0 ;
                    Users userCheck = new Users();
                    for (Users user1: list) {
                        if (user1.getPhone().equals(sdt)){
                            check = 1;
                            userCheck = user1;
                            break;
                        }
                    }
                    if (check == 1){
                        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                        intent.putExtra("Id",userCheck.getId());
                        intent.putExtra("Pass",userCheck.getPass());
                        intent.putExtra("Login","normal");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, LoginPhoneActivity.class);
                        intent.putExtra("Phone", sdt);
                        intent.putExtra("Login","normal");
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String pass = account.getId();
                        int check = 0;
                        Users userCheck = new Users();
                        for (Users user1: list) {
                            if (user1.getPass().equals(pass)){
                                check = 1;
                                userCheck = user1;
                                break;
                            }
                        }
                        if (check == 1){
                            addUserOnline(userCheck.getId());
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
                            intent.putExtra("Pass",pass);
                            intent.putExtra("Login","email");
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        Log.d(">>>>>>TAG", "onActivityResult Error: " + e.getMessage());
                    }
                }
            }
    );

    public void addUserOnline(String id){
        Map<String, Object> user = new HashMap<>();
        user.put("Id_User", id);

        db.collection("UserOnline")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    public void getUser(){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i+=1;
                                Map<String, Object> item = document.getData();
                                Users user = new Users();
                                user.setId(document.getId());
                                user.setName(item.get("Name").toString());
                                user.setImage(item.get("Image").toString());
                                user.setPass(item.get("Pass").toString());
                                user.setPhone(item.get("Phone").toString());
                                user.setAddress(item.get("Address").toString());
                                list.add(user);
                                System.out.println(i + " ---" + list.get(list.size()-1));
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}