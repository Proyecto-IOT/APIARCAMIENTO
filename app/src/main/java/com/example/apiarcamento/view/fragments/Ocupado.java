package com.example.apiarcamento.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;
import com.example.apiarcamento.view.signup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Ocupado extends BottomSheetDialogFragment {

    private static final String ARG_SPOT = null;

    TextView tvBrand, tvColor, tvModel, tvPlate;
    // Otros métodos y variables de tu fragmento

    public static Ocupado newInstance(int nombre) {
        Ocupado fragment = new Ocupado();
        Bundle args = new Bundle();
        args.putInt(ARG_SPOT, nombre);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ocupado, container, false);

        tvBrand=view.findViewById(R.id.tvMarca);
        tvColor=view.findViewById(R.id.tvColor);
        tvModel=view.findViewById(R.id.tvModelo);
        tvPlate=view.findViewById(R.id.tvMatricula);

        int valorEntero = getArguments().getInt(ARG_SPOT);
        Vehicle.Result id=new Vehicle.Result();

        id.setParking_id(valorEntero);
        consts ip=new consts();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ip.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Vehicles vehiclesInterface=retrofit.create(Vehicles.class);
        Call<Vehicle.Result> userCall=vehiclesInterface.ocupate(id);
        userCall.enqueue(new Callback<Vehicle.Result>() {
            @Override
            public void onResponse(Call<Vehicle.Result> call, Response<Vehicle.Result> response) {
                Log.d("TOKENN", ""+valorEntero);
                if(response.isSuccessful()&& response.body() != null){
                    Log.d("TOKENN", "BIENN");
                    Vehicle.Result body = response.body();
                    String platejson= body.getLicensePlate();
                    Log.d("TOKENN", "Plate JSON: " + body.getLicensePlate());
                    Log.d("TOKENN", "Color JSON: " + body.getColor());
                    Log.d("TOKENN", "Brand JSON: " + body.getBrand());
                    Log.d("TOKENN", "Model JSON: " + body.getModel());
                    tvPlate.setText(platejson);
                    String colorjson= body.getColor();
                    String brandjson= body.getBrand();
                    String modeljson= body.getModel();
                    tvBrand.setText(brandjson);
                    tvColor.setText(colorjson);
                    tvModel.setText(modeljson);
                }else{
                    Log.d("TOKENN", "MALLLLL");
                }


            }

            @Override
            public void onFailure(Call<Vehicle.Result> call, Throwable t) {
                Log.e("RetrofitError", "Error en la llamada a la API", t);

                //startActivity(nojala);

            }
        });

        return view;
    }
}