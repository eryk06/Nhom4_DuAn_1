package com.example.nhom4_duan_1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nhom4_duan_1.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


    }
}