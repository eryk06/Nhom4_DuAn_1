package com.example.nhom4_duan_1.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nhom4_duan_1.models.Bills;
import com.example.nhom4_duan_1.models.Products;
import com.example.nhom4_duan_1.models.Users;
import com.example.nhom4_duan_1.models.Vouchers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BillDAO {
    public ArrayList<Bills> GetAllDSBill(){
        ArrayList<Bills> list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bills")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(">>>TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return list;
    }
    public ArrayList<Products> GetAllDSProduct(){
        ArrayList<Products> list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(">>>TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return list;

    }
    public ArrayList<Users> GetAllDSUser(){
        ArrayList<Users> list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(">>>TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return list;

    }
    public ArrayList<Vouchers> GetAllDSVoucher(){
        ArrayList<Vouchers> list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Vouchers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(">>>TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
        return list;

    }
}


