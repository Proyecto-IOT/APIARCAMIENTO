package com.example.apiarcamento.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.ArduinoInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    LinearLayout btnOpen, btnClose, btnPark;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);
        btnOpen=vista.findViewById(R.id.contenedordebtn1);
        btnClose=vista.findViewById(R.id.contenedordebtn2);
        btnPark=vista.findViewById(R.id.contenedordebtn3);


        //Intent parking=new Intent(getContext(), activity_estacionamiento.class);
        btnOpen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        //.baseUrl("http://192.168.1.115:8000/")
                        .baseUrl("http://192.168.116.78:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ArduinoInterface arduino=retrofit.create(ArduinoInterface.class);
                Call<Vehicle> userCall=arduino.enter();

                userCall.enqueue(new Callback<Vehicle>() {
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

        btnClose.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        //.baseUrl("http://192.168.1.115:8000/")
                        .baseUrl("http://192.168.116.78:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ArduinoInterface arduino=retrofit.create(ArduinoInterface.class);
                Call<Vehicle> call=arduino.exit();

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
                //startActivity(parking);
            }
        });
        return vista;
    }
}