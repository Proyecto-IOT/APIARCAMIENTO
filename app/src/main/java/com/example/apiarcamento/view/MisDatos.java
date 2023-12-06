package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.apiarcamento.R;

public class MisDatos extends AppCompatActivity {

    EditText etName,etLastName, etEmail, etPassword;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        etName=findViewById(R.id.nameField);
        etLastName=findViewById(R.id.usernameField);
        etEmail=findViewById(R.id.emailField);
        etPassword=findViewById(R.id.passwordField);
        Save=findViewById(R.id.btn_login);


    }
}