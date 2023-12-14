package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.apiarcamento.R;
import com.example.apiarcamento.view.Home;
import com.example.apiarcamento.view.MainActivity;

public class Sart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sart);

        Intent main= new Intent(this, MainActivity.class);
        Intent home= new Intent(this, Home.class);

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPref.getInt("id", 0);

                if (id != 0) {
                    startActivity(home);
                } else {
                    startActivity(main);
                }
                finish();

            }
        }.start();

    }
}