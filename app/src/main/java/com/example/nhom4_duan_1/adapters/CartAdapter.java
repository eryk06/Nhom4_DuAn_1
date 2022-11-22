package com.example.nhom4_duan_1.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.models.Bills;
import com.example.nhom4_duan_1.models.Cart;
import com.example.nhom4_duan_1.models.Products;
import com.example.nhom4_duan_1.views.CartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    private ArrayList<Cart> list;
    ArrayList<Products> listTemp ;
    CartActivity activity;



    public CartAdapter(Context context, ArrayList<Cart> listCart, ArrayList<Products> listTemp) {
        this.context = context;
        this.list = listCart;
        this.listTemp = listTemp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products products = getProduct(position);
        holder.tvNameProductCart.setText(products.getName());
        holder.tvNoteCart.setText( list.get(holder.getAdapterPosition()).getAmount() + " item - " + list.get(holder.getAdapterPosition()).getTotal()+"đ");
        Picasso.get()
                .load(products.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(holder.ivProductCart);

        holder.ivDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list.get(holder.getAdapterPosition()).getId();
                System.out.println("id delete : " + id);
                activity = (CartActivity) context;
                activity.deleteCart(list.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    public Products getProduct(int po){
        for (Products lst: listTemp){
            if (list.get(po).getId_Product().equals(lst.getId())){
                return lst;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProductCart, ivDeleteCart;
        TextView tvNameProductCart, tvNoteCart;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivProductCart = itemView.findViewById(R.id.ivProductCart);
            ivDeleteCart = itemView.findViewById(R.id.ivDeleteCart);
            tvNameProductCart = itemView.findViewById(R.id.tvNameProductCart);
            tvNoteCart = itemView.findViewById(R.id.tvNoteCart);
        }
    }
}