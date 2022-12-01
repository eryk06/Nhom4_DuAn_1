package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Cart;
import com.example.nhom4_duan_1.models.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    ImageView ivDetailProduct, ivBackDetailProduct;
    TextView tvNameDetailProduct, tvMinus, tvPlus, tvTotal, tvAddToCart;
    EditText edtAmount;
    Products products;
    int amount = 1;
    double total = 0;
    int check = 0;
    String IdUser;
    Cart cart;
    ArrayList<Cart> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        ivBackDetailProduct = findViewById(R.id.ivBackDetalProduct);
        ivDetailProduct = findViewById(R.id.ivDetailProduct);
        tvNameDetailProduct = findViewById(R.id.tvNameDetailProduct);
        tvMinus = findViewById(R.id.tvMinus);
        tvPlus = findViewById(R.id.tvPlus);
        tvTotal = findViewById(R.id.tvTotal);
        tvAddToCart = findViewById(R.id.tvAddToCart);
        edtAmount = findViewById(R.id.edtAmount);
        edtAmount.setText("1");

        list = new ArrayList<>();

        Intent intent = getIntent();
        products = new Products();
        IdUser = intent.getStringExtra("idUser");
        products.setId(intent.getStringExtra("id"));
        products.setName(intent.getStringExtra("name"));
        products.setPrice(intent.getDoubleExtra("price", 0));
        products.setImage(intent.getStringExtra("image"));
        products.setType(intent.getStringExtra("type"));

        setProduct();
        setOnClick();
        getDataCart();
        System.out.println(products);
    }

    public void setProduct() {
        Picasso.get()
                .load(products.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .fit()
                .into(ivDetailProduct);

        tvNameDetailProduct.setText(products.getName());
        tvTotal.setText(products.getPrice() * amount + "");
    }

    public void setOnClick() {
        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1) {
                    amount -= 1;
                    edtAmount.setText(amount + "");

                    total = products.getPrice() * amount;
                    tvTotal.setText(total + "");
                }
            }
        });

        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += 1;
                edtAmount.setText(amount + "");
                total = products.getPrice() * amount;
                tvTotal.setText(total + "");
            }
        });

        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        ivBackDetailProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void addToCart() {
        if (amount > 0) {
            if (!IdUser.equals("0")){
                for (Cart lst: list) {
//                if (products.getId().equals(lst.getId_Product())){
                    if (lst.getId_Product().equals(products.getId())){
                        check = -1;
                        cart = lst;
                        break;
                    }
                }
                if (check == -1){
                    updateCart();
                }else {
                    addCart();
                }
            }
            else {
                Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }

    public void addCart(){
        Map<String, Object> user = new HashMap<>();
        user.put("Id_Product", products.getId());
        user.put("Amount", amount);
        user.put("Total", total);

        db.collection("Cart")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(DetailProductActivity.this, "Successful ADD TO CART", Toast.LENGTH_SHORT).show();
                        finish();
                        System.out.println("Thêm cart thành công");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Lỗi thêm cart");
                    }
                });
    }

    public void updateCart(){
        Map<String, Object> user = new HashMap<>();
        user.put("Id_Product", products.getId());
        user.put("Amount", amount + cart.getAmount());
        user.put("Total", total + cart.getTotal());

        // Add a new document with a generated ID
        db.collection("Cart")
                .document(cart.getId())
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Sửa thành công");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Sửa không thành công");
                    }
                });
    }

    public void getDataCart(){
        list = new ArrayList<>();
        db.collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i+=1;
                                Map<String, Object> item = document.getData();
                                Cart cart = new Cart();
                                cart.setId(document.getId());
                                cart.setId_Product(item.get("Id_Product").toString());
                                cart.setAmount(Integer.parseInt(item.get("Amount").toString()));
                                cart.setTotal(Double.parseDouble(item.get("Total").toString()));
                                list.add(cart);
                                System.out.println(i + " Cart---" + list.get(list.size()-1));
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}