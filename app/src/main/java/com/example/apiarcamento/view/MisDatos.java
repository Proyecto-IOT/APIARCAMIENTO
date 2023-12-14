package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Context context = this;

        Intent home=new Intent(this, Home.class);


        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        Log.d("DEBUG", "User ID: " + token);

        consts ip=new consts();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ip.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SingUp singupinterface=retrofit.create(SingUp.class);
        Call<User> userCall=singupinterface.edit("Bearer "+token);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    User respuesta = response.body();

                    String namejson= respuesta.getName();
                    Log.d("DEBUG", "User ID: " + namejson);
                    String appajson= respuesta.getLastName();
                    String apmajson= respuesta.getMotherSurname();
                    String genderjson= respuesta.getGender();
                    String correojson= respuesta.getEmail();

                    etName.setText(namejson);
                    etLastName.setText(appajson);
                    etMotherSurname.setText(apmajson);
                    etEmail.setText(correojson);

                    List<String> generos = new ArrayList<>(Arrays.asList("Masculino", "Femenino", "39 tipos de gays"));
                    generos.remove(genderjson);
                    generos.add(0, genderjson);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, generos);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spGender.setAdapter(adapter);
                }
                else {
                    Toast.makeText(MisDatos.this, "Sintaxis incorrecta", Toast.LENGTH_SHORT).show();
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

                String nombre = etName.getText().toString();
                String last_name = etLastName.getText().toString();
                String mothersurname = etMotherSurname.getText().toString();
                String gender = spGender.getSelectedItem().toString();
                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPref.getInt("id", 0);

                User usuario = new User();
                usuario.setUserid(id);
                usuario.setName(nombre);
                usuario.setLastName(last_name);
                usuario.setMotherSurname(mothersurname);
                usuario.setGender(gender);
                usuario.setEmail(email);
                usuario.setPassword(pass);

                Log.e("DEBUG", "id: " + usuario.getUserid());
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
                Call<User> userCall=singupinterface.editarDatos(usuario);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                            startActivity(home);
                            finish();

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