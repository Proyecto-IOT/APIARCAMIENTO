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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class signup extends AppCompatActivity {

    EditText etName,etLastName,etMSurname, etGender, etEmail, etPassword;
    TextView iot;
    Button Registrar;
    Spinner spGender;
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
        spGender=findViewById(R.id.genderSpinner);
        iot=findViewById(R.id.textNoCuenta);

        Intent Login=new Intent(this, MainActivity.class);

        Intent nojala=new Intent(this, Home.class);


        List<String> items = new ArrayList<>();
        items.add("Selecciona:");
        items.add("Masculino");
        items.add("Femenino");
        items.add("39 tipos de gays");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(adapter);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String nombre = etName.getText().toString();
                String last_name = etLastName.getText().toString();
                String mothersurname = etMSurname.getText().toString();
                String gender = spGender.getSelectedItem().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();
                User usuario = new User();
                usuario.setName(nombre);
                usuario.setLastName(last_name);
                usuario.setMotherSurname(mothersurname);
                usuario.setGender(gender);
                usuario.setEmail(email);
                usuario.setPassword(pass);


                Log.e("DEBUG", "Nombre: " + usuario.getName());
                Log.e("DEBUG", "Apellido: " + usuario.getLastName());
                Log.e("DEBUG", "Mother: " + usuario.getMotherSurname());
                Log.e("DEBUG", "gender: " + usuario.getGender());
                Log.e("DEBUG", "Email: " + usuario.getEmail());
                Log.e("DEBUG", "Password: " + usuario.getPassword());

                consts ip=new consts();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SingUp singupinterface=retrofit.create(SingUp.class);
                Call<User> userCall=singupinterface.enviarDatos(usuario);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                            startActivity(Login);
                            finish();
                        }
                        else {
                            Toast.makeText(signup.this, "Sintaxis incorrecta", Toast.LENGTH_SHORT).show();
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
        iot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Login);
            }
        });

    }
    public void onBackPressed() {
        Log.e("DEBUG", "Bck: " );
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}