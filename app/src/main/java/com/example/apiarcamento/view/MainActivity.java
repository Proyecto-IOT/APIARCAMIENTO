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
        Button btnLogIn = findViewById(R.id.btn_login);

        btnSignUp.setOnClickListener(view -> {
            Intent registrarse = new Intent(this, signup.class);
            startActivity(registrarse);
        });

        btnLogIn.setOnClickListener(view -> {
            Intent Iniciar = new Intent(this, Home.class);
            startActivity(Iniciar);
        });
    }
}