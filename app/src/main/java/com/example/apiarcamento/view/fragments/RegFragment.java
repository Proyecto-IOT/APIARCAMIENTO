package com.example.apiarcamento.view.fragments;

import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.registeradapter;
import com.example.apiarcamento.models.Activityresponse;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;
import com.example.apiarcamento.view.MisVehicles;
import com.example.apiarcamento.viewmodel.regviewmodel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegFragment extends Fragment {

    private RecyclerView Rvregister;
    private registeradapter adaptador;
    regviewmodel Regviewmodel;
    private ArrayList<Activityresponse.Datum> regis=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_reg, container, false);
        Rvregister=vista.findViewById(R.id.RVREGISTER);
        Rvregister.setLayoutManager(new LinearLayoutManager(requireContext()));
        Rvregister.setHasFixedSize(true);
        adaptador=new registeradapter(regis);
        Rvregister.setAdapter(adaptador);

        Regviewmodel = new ViewModelProvider(this).get(regviewmodel.class);
        Regviewmodel.getRegisterresponselivedata().observe(getViewLifecycleOwner(), new Observer<Activityresponse>() {
            @Override
            public void onChanged(Activityresponse activityresponse) {
            if (activityresponse!=null){
                List<Activityresponse.Datum> registers = activityresponse.getData();
                regis.addAll(registers);
                adaptador.notifyDataSetChanged();
            }



            }
        });
        return vista;


    }



}