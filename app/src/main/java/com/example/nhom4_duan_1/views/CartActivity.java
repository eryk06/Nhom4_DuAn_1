package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.adapters.CartAdapter;
import com.example.nhom4_duan_1.adapters.OderAdapter;
import com.example.nhom4_duan_1.models.Bills;
import com.example.nhom4_duan_1.models.Cart;
import com.example.nhom4_duan_1.models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class CartActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerCart;
    ArrayList<Products> listPro;
    ArrayList<Cart> listCart;
    ArrayList<Products> listTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listPro = new ArrayList<>();
        listCart = new ArrayList<>();
        listTemp = new ArrayList<>();

        recyclerCart = (RecyclerView) findViewById(R.id.recyclerCart);
        ImageView ivBackCart = findViewById(R.id.ivBackCart);
        ivBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDataProduct();
    }

    public void getDataCart() {
        listCart.clear();
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
                                listCart.add(cart);
//                                System.out.println(i + " ---" + list.get(list.size() - 1));
                            }
                            getDataCartProduct();
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void getDataProduct(){
        listPro.clear();
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> item = document.getData();
                                Products products = new Products();
                                products.setId(document.getId());
                                products.setName(item.get("Name").toString());
                                products.setImage(item.get("Image").toString());
                                products.setType(item.get("Type").toString());
                                products.setPrice(Double.parseDouble(item.get("Price").toString()));
                                listPro.add(products);
                            }
                            getDataCart();
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void getDataCartProduct(){
        listTemp.clear();
        for (Products lst1: listPro) {
            for (Cart lst2: listCart) {
                if (lst1.getId().equals(lst2.getId_Product())){
                    listTemp.add(lst1);
                }
            }
        }
        System.out.println( "halo " +listTemp.size());
        FillData();
    }

    public void deleteCart(String id){
        db.collection("Cart")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CartActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                        getDataProduct();
                    }
                });
    }

    public void FillData() {
        recyclerCart = (RecyclerView) findViewById(R.id.recyclerCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerCart.setLayoutManager(linearLayoutManager);
        System.out.println("ListPro: " + listPro.size());
        CartAdapter adapter = new CartAdapter(CartActivity.this, listCart,listTemp);
        recyclerCart.setAdapter(adapter);
    }
}
