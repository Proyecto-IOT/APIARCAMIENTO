package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.view.signup;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etPass, etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignUp = findViewById(R.id.btn_registrar);
        Button btnLogIn = findViewById(R.id.btn_login);

         etEmail= findViewById(R.id.emailField);
    etPass=findViewById(R.id.passwordField);

        btnSignUp.setOnClickListener(view -> {
            Intent registrarse = new Intent(this, signup.class);
            startActivity(registrarse);
        });

        btnLogIn.setOnClickListener(view -> {

            Intent Iniciar = new Intent(this, Home.class);

            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            User usuario = new User(email,pass);
            usuario.setEmail(email);
            usuario.setPassword(pass);
            Log.e("DEBUG", "Onclck: " );

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.115:8000/api/v3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SingUp singupinterface=retrofit.create(SingUp.class);
            Call<User> userCall=singupinterface.login(usuario);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.e("DEBUG", "onresponse: " );
                    if(response.isSuccessful()){
                        Log.e("DEBUG", "successful: " );
                        String respuesta = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(respuesta);
                            String idjson = jsonObject.getString("id");

                            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                            // Edita las SharedPreferences
                            SharedPreferences.Editor editor = sharedPref.edit();

                            // Almacena el valor id
                            editor.putString("id", idjson);

                            // Aplica los cambios
                            editor.apply();


                        }catch (JSONException e){

                        }
                        startActivity(Iniciar);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("RetrofitError", "Error en la llamada a la API", t);

                    //startActivity(nojala);

                }
            });


        });
    }
}