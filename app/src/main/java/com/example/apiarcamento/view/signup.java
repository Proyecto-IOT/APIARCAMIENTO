package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class signup extends AppCompatActivity {

    EditText etName,etLastName,etMSurname, etGender, etEmail, etPassword;
    Button Registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etName=findViewById(R.id.nameField);
        etLastName=findViewById(R.id.usernameField);
        etMSurname=findViewById(R.id.MSurnameField);
        etEmail=findViewById(R.id.emailField);
        etPassword=findViewById(R.id.passwordField);
        Registrar=findViewById(R.id.btn_login);

        Intent Login=new Intent(this, MainActivity.class);

        Intent nojala=new Intent(this, Home.class);


        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String nombre = etName.getText().toString();
                String last_name = etLastName.getText().toString();
                String mothersurname = etMSurname.getText().toString();
                String gender = etName.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                User usuario = new User(nombre,last_name,mothersurname,gender, email,pass);
                usuario.setName(nombre);
                usuario.setLast_name(last_name);
                usuario.setMother_surname(mothersurname);
                usuario.setGender(gender);
                usuario.setEmail(email);
                usuario.setPassword(pass);


                //Log.e("DEBUG", "Nombre: " + usuario.getName());
                //Log.e("DEBUG", "Email: " + usuario.getEmail());
                //Log.e("DEBUG", "Password: " + usuario.getPassword());

                Retrofit retrofit = new Retrofit.Builder()
                        //.baseUrl("http://127.0.0.1:8000/")
                        .baseUrl("https://pg50s515-8000.usw3.devtunnels.ms/")
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
                        Log.e("RetrofitError", "Error en la llamada a la API", t);

                        //startActivity(nojala);

                    }
                });

            }
        });
    }
}