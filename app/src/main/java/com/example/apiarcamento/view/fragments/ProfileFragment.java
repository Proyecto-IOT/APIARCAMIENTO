package com.example.apiarcamento.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.view.Home;
import com.example.apiarcamento.view.MainActivity;
import com.example.apiarcamento.view.MisDatos;
import com.example.apiarcamento.view.MisVehicles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {
    LinearLayout datos_completo, logout, misve;
    TextView  username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);

        datos_completo=vista.findViewById(R.id.misDatos);
        misve=vista.findViewById(R.id.misVehiculos);
        logout=vista.findViewById(R.id.cerrarSesion);
        username=vista.findViewById(R.id.username);

        Intent Intentlogout=new Intent(getContext(), MainActivity.class);
        Intent misdatoss=new Intent(getContext(), MisDatos.class);
        Intent IntentMV=new Intent(getContext(), MisVehicles.class);

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
                    User userid = response.body();
                    String namejson= userid.getName();
                    Log.d("DEBUG", "User ID: " + namejson);
                    username.setText(namejson);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("RetrofitError", "Error en la llamada a la API", t);

                //startActivity(nojala);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TOKENN", token);
                consts ip=new consts();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SingUp singupinterface=retrofit.create(SingUp.class);
                Call<User> userCall=singupinterface.logout("Bearer "+token);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                            SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                            // Edita las SharedPreferences
                            SharedPreferences.Editor editor = sharedPref.edit();

                            // Almacena el valor id
                            editor.putInt("id", 0);

                            // Aplica los cambios
                            editor.apply();

                            startActivity(Intentlogout);
                            getActivity().finish();
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
        datos_completo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(misdatoss);
                //getActivity().finish();
            }
        });
        misve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentMV);
                //getActivity().finish();
            }
        });

        return vista;

    }

}