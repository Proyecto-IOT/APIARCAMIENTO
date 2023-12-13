package com.example.apiarcamento.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_reg, container, false);

        Vehicle.Result id=new Vehicle.Result();

        id.setParking_id(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.115:8000/")
                // .baseUrl("http://192.168.116.78:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Vehicles vehiclesInterface=retrofit.create(Vehicles.class);
        Call<Vehicle.Result> userCall=vehiclesInterface.ocupate(id);
        userCall.enqueue(new Callback<Vehicle.Result>() {
            @Override
            public void onResponse(Call<Vehicle.Result> call, Response<Vehicle.Result> response) {
                if(response.isSuccessful()){
                    Log.d("TOKENN", "fsgsfdhsg");
                }


            }

            @Override
            public void onFailure(Call<Vehicle.Result> call, Throwable t) {
                Log.e("RetrofitError", "Error en la llamada a la API", t);

                //startActivity(nojala);

            }
        });

        return vista;
    }
}