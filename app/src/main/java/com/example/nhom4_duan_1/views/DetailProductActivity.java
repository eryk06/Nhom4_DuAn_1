package com.example.nhom4_duan_1.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Products;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    ImageView ivDetailProduct;
    TextView tvNameDetailProduct, tvMinus, tvPlus, tvTotal, tvAddToCart;
    EditText edtAmount;
    Products products;
    int amount = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        ivDetailProduct = findViewById(R.id.ivDetailProduct);
        tvNameDetailProduct = findViewById(R.id.tvNameDetailProduct);
        tvMinus = findViewById(R.id.tvMinus);
        tvPlus = findViewById(R.id.tvPlus);
        tvTotal = findViewById(R.id.tvTotal);
        tvAddToCart = findViewById(R.id.tvAddToCart);
        edtAmount = findViewById(R.id.edtAmount);

        Intent intent = getIntent();
        products = new Products();
        products.setId(intent.getStringExtra("id"));
        products.setName(intent.getStringExtra("name"));
        products.setPrice(intent.getDoubleExtra("price",0));
        products.setImage(intent.getStringExtra("image"));
        products.setType(intent.getStringExtra("type"));

        setProduct();
        changeAmount();
        System.out.println(products);
    }

    public void setProduct(){
        Picasso.get()
                .load(products.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .fit()
                .into(ivDetailProduct);

        tvNameDetailProduct.setText(products.getName());
        tvTotal.setText(products.getPrice() * amount + "");
    }

    public void changeAmount(){
        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount >=1 ){
                    amount -=  1;
                    edtAmount.setText(amount+"");
                    tvTotal.setText(products.getPrice() * amount + "");
                }
            }
        });

        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount += 1;
                edtAmount.setText(amount+"");
                tvTotal.setText(products.getPrice() * amount + "");
            }
        });
    }

    public void addToCart(){
        if (amount != 0){
//            db.document("Cart")
        }
    }

}