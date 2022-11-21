package com.example.nhom4_duan_1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Products;
import com.example.nhom4_duan_1.views.DetailProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Products> list;

    public ProductAdapter(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());

        Picasso.get()
                .load(list.get(position).getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(holder.ivSP);

        Products products = list.get(position);

        holder.linearItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProduct(products);
            }
        });

    }

    public void getProduct(Products products){
        Intent intent = new Intent(context, DetailProductActivity.class);
        intent.putExtra("id",products.getId());
        intent.putExtra("name",products.getName());
        intent.putExtra("price",products.getPrice());
        intent.putExtra("image",products.getImage());
        intent.putExtra("type",products.getType());
        context.startActivity(intent);
        System.out.println(products);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivSP;
        TextView tvName;
        LinearLayout linearItemProduct;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            linearItemProduct = itemView.findViewById(R.id.linearItemProduct);
            ivSP = itemView.findViewById(R.id.ivSP);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
