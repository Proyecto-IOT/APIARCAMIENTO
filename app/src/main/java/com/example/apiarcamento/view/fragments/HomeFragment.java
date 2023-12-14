package com.example.apiarcamento.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.Estacionamiento;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.ArduinoInterface;
import com.example.apiarcamento.view.RegistroIncidentes;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    LinearLayout btnOpen, btnClose, btnPark, btnIncidents;
    TextView Quienlodiria;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        btnOpen=vista.findViewById(R.id.contenedordebtn1);
        btnClose=vista.findViewById(R.id.contenedordebtn2);
        btnPark=vista.findViewById(R.id.contenedordebtn3);
        btnIncidents=vista.findViewById(R.id.contenedordebtn4);
        Quienlodiria= vista.findViewById(R.id.textoBienvenida);


        Intent parking=new Intent(getContext(), Estacionamiento.class);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);

        User ser_id=new User();
        ser_id.setUserid(user_id);

        btnOpen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Checar", "OnLong"+ser_id.getUserid());
                consts ip=new consts();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ArduinoInterface arduino=retrofit.create(ArduinoInterface.class);
                Call<Vehicle> userCall=arduino.enter(ser_id);
                userCall.enqueue(new Callback<Vehicle>() {
                    @Override
                    public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                        Log.d("Checar", "Respondió");
                        if(response.isSuccessful()){
                            Log.d("Checar", "Abrió");
                        }else{
                            Log.d("Checar", "Mal"+response.code());
                        }

                    }
                    @Override
                    public void onFailure(Call<Vehicle> call, Throwable t) {
                        Log.d("Checar", "Falló retrofiiiiiiii");
                    }
                });

                return true;
            }
        });

        btnClose.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                consts ip=new consts();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ArduinoInterface arduino=retrofit.create(ArduinoInterface.class);
                Call<Vehicle> call=arduino.enter(ser_id);

                call.enqueue(new Callback<Vehicle>() {
                    @Override
                    public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                        if(response.isSuccessful()){

                        }
                    }

                    @Override
                    public void onFailure(Call<Vehicle> call, Throwable t) {

                    }
                });

                return true;
            }
        });

        btnPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(parking);
                getActivity().finish();
            }
        });
        btnIncidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegistroIncidentes.class);
                startActivity(intent);
            }
        });
        return vista;
    }
    public void onBackPressed() {

        //finishAffinity();

        // Termina el proceso
        System.exit(0);
    }

}