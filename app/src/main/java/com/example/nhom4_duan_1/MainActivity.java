package com.example.nhom4_duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nhom4_duan_1.fragments.CartFragment;
import com.example.nhom4_duan_1.fragments.MainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new MainFragment();
        FragmentManager fra = getSupportFragmentManager();
        fra.beginTransaction().replace(R.id.drawerLayout,fragment).commit();
        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fra = getSupportFragmentManager();
                Fragment fragment = new MainFragment();
                int id = item.getItemId();
                System.out.println(id);
                if (id == R.id.home){
                    setTitle("Home");
                    fragment = new MainFragment();
                }
                else if (id == R.id.cart){
                    setTitle("Cart");
                    fragment = new CartFragment();
                }
//                switch (id){
//                    case R.id.home:{
//                        setTitle("Home");
//                        fragment = new MainFragment();
//                    }
//                    case R.id.cart: {
//                        setTitle("Cart");
//                        fragment = new CartFragment();
//                    }
//                }
                fra.beginTransaction().replace(R.id.drawerLayout,fragment).commit();
                return true;
            }
        });

    }
}