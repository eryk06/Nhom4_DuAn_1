package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.adapters.CartAdapter;
import com.example.nhom4_duan_1.adapters.OderAdapter;
import com.example.nhom4_duan_1.models.Bills;
import com.example.nhom4_duan_1.models.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class CartActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCart = (RecyclerView) findViewById(R.id.recyclerCart);
        ImageView ivBackCart = findViewById(R.id.ivBackCart);
        ivBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getData();
    }

    public void getData() {
        ArrayList<Cart> list = new ArrayList<>();
        db.collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i += 1;
                                Map<String, Object> item = document.getData();
                                Cart cart = new Cart();
                                cart.setId(document.getId());
                                cart.setId_Product(item.get("Id_Product").toString());
                                cart.setAmount(Integer.parseInt(item.get("Amount").toString()));
                                cart.setTotal(Double.parseDouble(item.get("Total").toString()));
                                list.add(cart);
                                System.out.println(i + " ---" + list.get(list.size() - 1));
                            }
                            FillData(list);
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void FillData(ArrayList<Cart> list) {
        recyclerCart = (RecyclerView) findViewById(R.id.recyclerCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerCart.setLayoutManager(linearLayoutManager);
        CartAdapter adapter = new CartAdapter(CartActivity.this, list);
        recyclerCart.setAdapter(adapter);
    }
}
