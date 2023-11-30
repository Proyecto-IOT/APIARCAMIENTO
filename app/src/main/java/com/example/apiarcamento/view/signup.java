package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.apiarcamento.MainActivity;
import com.example.apiarcamento.R;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btnSignIn = findViewById(R.id.btn_iniciar);

        btnSignIn.setOnClickListener(view -> {
            Intent iniciarS = new Intent(this, MainActivity.class);
            startActivity(iniciarS);
        });
    }
}