package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_duan_1.MainActivity;
import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

public class LoginEmailActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText edtNameGG,edtPhoneGG, edtAddressGG;
    LinearLayout lnLoginEmail;
    ImageView ivBackLoginEmail;
    String Pass;
    GoogleSignInClient googleSignInClient;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID = "idKey";
    public static final String LOGIN = "login";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        sharedpreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        Intent intent = getIntent();
        Pass = intent.getStringExtra("Pass");

        edtNameGG = findViewById(R.id.edtNameGG);
        edtPhoneGG = findViewById(R.id.edtPhoneGG);
        edtAddressGG = findViewById(R.id.edtAddressGG);
        ivBackLoginEmail = findViewById(R.id.ivBackLoginEmail);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(LoginEmailActivity.this, googleSignInOptions);

        ivBackLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });
            }
        });

        lnLoginEmail = findViewById(R.id.lnLoginEmail);
        lnLoginEmail.setOnClickListener(new View.OnClickListener() {
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

    private void saveData(String id, String Login) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(ID, id);
        editor.putString(LOGIN,Login);
        editor.commit();
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
                                if (document.getId().equals(id)){
                                    saveData(id,"email");
                                    Intent intent = new Intent(LoginEmailActivity.this, MainActivity.class);
                                    intent.putExtra("Id",id);
                                    intent.putExtra("Login","email");
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}