package com.example.apiarcamento.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.apiarcamento.R;
import com.example.apiarcamento.view.fragments.HomeFragment;
import com.example.apiarcamento.view.fragments.ProfileFragment;
import com.example.apiarcamento.view.fragments.RegFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, new HomeFragment());
        fragmentTransaction.commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (item.getItemId() == R.id.home) {
                    fragmentTransaction.replace(R.id.framelayout, new HomeFragment());
                } else if (item.getItemId() == R.id.registros) {
                    fragmentTransaction.replace(R.id.framelayout, new RegFragment());
                } else if (item.getItemId() == R.id.perfil) {
                    fragmentTransaction.replace(R.id.framelayout, new ProfileFragment());
                }

                fragmentTransaction.commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);

    }
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
