package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.apiarcamento.R;
import com.example.apiarcamento.view.signup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignUp = findViewById(R.id.btn_registrar);
        //Intent estacionamiento = new Intent(this, activity_estacionamiento.class);
        //startActivity(estacionamiento);
        btnSignUp.setOnClickListener(view -> {
            Intent registrarse = new Intent(this, signup.class);
            startActivity(registrarse);
        });

    }
}