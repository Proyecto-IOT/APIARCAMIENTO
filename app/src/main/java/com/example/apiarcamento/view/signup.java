package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signup extends AppCompatActivity {

    EditText etName,etLastName, etEmail, etPassword;
    Button Registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etName=findViewById(R.id.nameField);
        etLastName=findViewById(R.id.usernameField);
        etEmail=findViewById(R.id.emailField);
        etPassword=findViewById(R.id.passwordField);
        Registrar=findViewById(R.id.btn_login);

        Intent Login=new Intent(this, MainActivity.class);

        Button btnSignIn = findViewById(R.id.btn_iniciar);

        btnSignIn.setOnClickListener(view -> {
            Intent iniciarS = new Intent(this, MainActivity.class);
            startActivity(iniciarS);
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                User usuario = new User(nombre, email,pass);
                usuario.setName(nombre);
                usuario.setEmail(email);
                usuario.setPassword(pass);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://127.0.0.1:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SingUp singupinterface=retrofit.create(SingUp.class);
                Call<User> userCall=singupinterface.enviarDatos(usuario);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            startActivity(Login);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });
    }
}