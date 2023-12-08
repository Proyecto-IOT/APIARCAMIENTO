package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisDatos extends AppCompatActivity {

    EditText etName,etLastName, etMotherSurname, etEmail, etPassword;
    Button Save;
    Spinner spGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        etName=findViewById(R.id.nameField);
        etLastName=findViewById(R.id.usernameField);
        etEmail=findViewById(R.id.emailField);
        etPassword=findViewById(R.id.passwordField);
        Save=findViewById(R.id.btn_login);
        etMotherSurname=findViewById(R.id.MSurnameField);
        spGender=findViewById(R.id.genderSpinner);
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://127.0.0.1:8000/")
                .baseUrl("https://pg50s515-8000.usw3.devtunnels.ms/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SingUp singupinterface=retrofit.create(SingUp.class);
        Call<User> userCall=singupinterface.edit();

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    String respuesta = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(respuesta);
                        String namejson = jsonObject.getString("name");
                        String appajson = jsonObject.getString("last_name");
                        String apmajson = jsonObject.getString("mother_surname");
                        String genderjson = jsonObject.getString("gender");
                        String emailjson = jsonObject.getString("email");

                        etName.setText(namejson);
                        etLastName.setText(appajson);
                        etMotherSurname.setText(apmajson);
                        //spGender.set
                        etEmail.setText(emailjson);
                    }catch(JSONException e) {
                        //startActivity(Intentlogout);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("RetrofitError", "Error en la llamada a la API", t);

                //startActivity(nojala);

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}