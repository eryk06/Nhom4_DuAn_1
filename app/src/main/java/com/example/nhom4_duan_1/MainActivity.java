package com.example.nhom4_duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nhom4_duan_1.adapters.ProductAdapter;
import com.example.nhom4_duan_1.managers.Manager;
import com.example.nhom4_duan_1.models.Products;
import com.example.nhom4_duan_1.views.CartActivity;
import com.example.nhom4_duan_1.views.ProductActivity;
import com.example.nhom4_duan_1.views.UsersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerProduct;
    AlertDialog alertDialog;
    EditText edtSeach;
    ArrayList<Products> list;
    FloatingActionButton floatingActionButton;
    LinearLayout ln5s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivFilter = (ImageView) findViewById(R.id.ivFilter);
        ImageView ivUser = (ImageView) findViewById(R.id.ivUser);
        ImageView ivSearch = findViewById(R.id.ivSearch);
        edtSeach = findViewById(R.id.edtSearch);
        floatingActionButton = findViewById(R.id.fltCart);
        ln5s = findViewById(R.id.linearNote);
        ImageView ivFood = findViewById(R.id.ivFood);
        ImageView ivDrink = findViewById(R.id.ivDrink);

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("type","Food");
                intent.putExtra("check",2);
                startActivity(intent);
            }
        });

        ivDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("type","Drink");
                intent.putExtra("check",3);
                startActivity(intent);
            }
        });

        CountDownTimer timer = new CountDownTimer(5000,5000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ln5s.setVisibility(View.GONE);
            }
        }.start();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        list = new ArrayList<>();

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_filter, null);
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData();
            }
        });

        getData();
    }

    public void searchData(){
        Manager manager = new Manager(list);
        if (edtSeach.getText().length() > 0){
            List<String> lst = manager.onSearch(edtSeach.getText().toString());
            System.out.println("edt: " + edtSeach.getText().toString());
            System.out.println("lst: " + lst);
            if (lst.isEmpty() == false){
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putStringArrayListExtra("list", (ArrayList<String>) lst);
                intent.putExtra("check",1);
                intent.putExtra("search",edtSeach.getText().toString());
                startActivity(intent);
            }else{
                Toast.makeText(this, "Không có sản phẩm bạn tìm", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getData(){
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
                            FillData(list);
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void FillData(ArrayList<Products> list){
        recyclerProduct = (RecyclerView) findViewById(R.id.recyclerMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter( MainActivity.this, list);
        recyclerProduct.setAdapter(adapter);
    }
}