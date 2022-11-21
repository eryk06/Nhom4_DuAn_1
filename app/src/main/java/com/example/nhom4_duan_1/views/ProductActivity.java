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
import android.widget.TextView;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.adapters.ProductAdapter;
import com.example.nhom4_duan_1.dao.BillDao;
import com.example.nhom4_duan_1.models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerProduct;
    int check = 0;
    String search = "";
    List<String> listString;
    ArrayList<Products> list;
    TextView tvnameProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        list = new ArrayList<>();
        tvnameProduct = findViewById(R.id.tvNameProduct);

        System.out.println("check: " + check);

        ImageView ivBackProduct = findViewById(R.id.ivBackProduct);
        ivBackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getDataFirebase();

    }

    public void getDataActivity(){
        tvnameProduct.setText("Search work ' "+ search + " '");
        ArrayList<Products> listTemp = new ArrayList<>();
        for (Products lst1: list) {
            for (String lst2: listString) {
                if (lst1.getId().equals(lst2)){
                    listTemp.add(lst1);
                }
            }
        }
        System.out.println("ListTemp: "+ listTemp);
        FillData(listTemp);
    }


    public void getDataFirebase(){
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i+=1;
                                Map<String, Object> item = document.getData();
                                Products products = new Products();
                                products.setId(document.getId());
                                products.setName(item.get("Name").toString());
                                products.setImage(item.get("Image").toString());
                                products.setType(item.get("Type").toString());
                                products.setPrice(Double.parseDouble(item.get("Price").toString()));
                                list.add(products);
                                System.out.println(i + " ---" + list.get(list.size()-1));
                            }

                            Intent intent = getIntent();
                            listString = intent.getStringArrayListExtra("list");
                            check = intent.getIntExtra("check",0);
                            search = intent.getStringExtra("search");
                            if (check == 0){
                                FillData(list);
                            }
                            if (check == 1){
                                getDataActivity();
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void FillData(ArrayList<Products> list){
        recyclerProduct = (RecyclerView) findViewById(R.id.recyclerProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter( ProductActivity.this, list);
        recyclerProduct.setAdapter(adapter);
    }
}