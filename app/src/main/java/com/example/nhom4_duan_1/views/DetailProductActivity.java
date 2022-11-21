package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Cart;
import com.example.nhom4_duan_1.models.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    ImageView ivDetailProduct, ivBackDetailProduct;
    TextView tvNameDetailProduct, tvMinus, tvPlus, tvTotal, tvAddToCart;
    EditText edtAmount;
    Products products;
    int amount = 0;
    double total = 0;
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

        list = new ArrayList<>();

        Intent intent = getIntent();
        products = new Products();
        products.setId(intent.getStringExtra("id"));
        products.setName(intent.getStringExtra("name"));
        products.setPrice(intent.getDoubleExtra("price", 0));
        products.setImage(intent.getStringExtra("image"));
        products.setType(intent.getStringExtra("type"));

        setProduct();
        setOnClick();
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
                if (amount >= 1) {
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
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("Id_Product", products.getId());
            user.put("Amount", amount);
            user.put("Total", total);

            // Add a new document with a generated ID
            db.collection("Cart")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
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

    }
}