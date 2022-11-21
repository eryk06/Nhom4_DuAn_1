package com.example.nhom4_duan_1.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.nhom4_duan_1.R;
import com.example.nhom4_duan_1.adapters.OderAdapter;
import com.example.nhom4_duan_1.adapters.VoucherAdapter;
import com.example.nhom4_duan_1.models.Bills;
import com.example.nhom4_duan_1.models.Vouchers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class VoucherActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerVoucher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        recyclerVoucher = (RecyclerView) findViewById(R.id.recyclerVoucher);
        getData();
    }
    public void getData(){
        ArrayList<Vouchers> list = new ArrayList<>();
        db.collection("Vouchers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                i+=1;
                                Map<String, Object> item = document.getData();
                                Vouchers vouchers = new Vouchers();
                                vouchers.setId(document.getId());
                                vouchers.setName(item.get("Name").toString());
                                vouchers.setPrice(Double.parseDouble(item.get("Price").toString()));
                                list.add(vouchers);
                                System.out.println(i + " ---" + list.get(list.size()-1));
                            }
                            FillData(list);
                        } else {
                            Log.w(">>>TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void FillData(ArrayList<Vouchers> list){
        recyclerVoucher = (RecyclerView) findViewById(R.id.recyclerVoucher);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VoucherActivity.this);
        recyclerVoucher.setLayoutManager(linearLayoutManager);
        VoucherAdapter adapter = new VoucherAdapter(VoucherActivity.this, list);
        recyclerVoucher.setAdapter(adapter);
    }
}