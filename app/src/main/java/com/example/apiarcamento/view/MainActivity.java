package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText etPass, etEmail;
    ConstraintLayout loading, padrino;
    LinearLayout linearr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSignUp = findViewById(R.id.btn_registrar);
        Button btnLogIn = findViewById(R.id.btn_login);

        padrino=findViewById(R.id.padrino);
        loading=findViewById(R.id.loadingLayout);
         etEmail= findViewById(R.id.emailField);
    etPass=findViewById(R.id.passwordField);

    linearr=findViewById(R.id.linear);


        btnSignUp.setOnClickListener(view -> {
            Intent registrarse = new Intent(this, signup.class);
            startActivity(registrarse);
        });

        btnLogIn.setOnClickListener(view -> {
            disableAllViews(linearr, false);
            int colorLoading = 0xB7000000;
            padrino.setBackgroundColor(colorLoading);
            loading.setVisibility(View.VISIBLE);

            Intent Iniciar = new Intent(this, Home.class);

            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            User usuario = new User();

            usuario.setEmail(email);
            usuario.setPassword(pass);
            Log.e("DEBUG", "Onclck: " );

            Retrofit retrofit = new Retrofit.Builder()
                    //.baseUrl("http://192.168.1.115:8000/")
                    .baseUrl("http://192.168.116.78:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SingUp singupinterface=retrofit.create(SingUp.class);
            Call<User> userCall=singupinterface.login(usuario);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        disableAllViews(linearr, true);
                        int colorLoading = 0xFFFFFFFF;
                        padrino.setBackgroundColor(colorLoading);
                        loading.setVisibility(View.INVISIBLE);
                        User userid = response.body();
                        int idjson= userid.getUserid();
                        String tokenjson= userid.getToken();
                        Log.d("DEBUG", "User ID: " + idjson);
                        Log.d("TOKENN", tokenjson);
                        //Log.d("DEBUG", "User ID: " +  response.code());

                        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                        // Edita las SharedPreferences
                        SharedPreferences.Editor editor = sharedPref.edit();

                        // Almacena el valor id
                        editor.putInt("id", idjson);
                        editor.putString("token", tokenjson);

                        // Aplica los cambios
                        editor.apply();

                        startActivity(Iniciar);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("RetrofitError", "Error en la llamada a la API", t);
                    disableAllViews(linearr, true);
                    loading.setVisibility(View.INVISIBLE);
                    int colorLoading = 0xFFFFFFFF;
                    padrino.setBackgroundColor(colorLoading);
                    //startActivity(nojala);

                }
            });


        });

    }
    private void disableAllViews(ViewGroup viewGroup, boolean bo) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            // Si el hijo es un contenedor, llámate a ti mismo recursivamente
            if (child instanceof ViewGroup) {
                disableAllViews((ViewGroup) child, bo);
            } else {
                // Deshabilita el hijo si es un botón o un TextView
                if (child instanceof Button || child instanceof EditText) {
                    child.setEnabled(bo);
                }
            }
        }
    }
}