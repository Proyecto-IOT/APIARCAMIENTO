package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.VehicleAdapter;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisVehicles extends AppCompatActivity {

    RecyclerView rvVehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vehicles);

        rvVehicle=findViewById(R.id.recycler);

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);

        Retrofit rf=new Retrofit.Builder()
                .baseUrl("http://192.168.1.115:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Vehicles vehiclesInterfaz=rf.create(Vehicles.class);
        Call<Vehicle> Call=vehiclesInterfaz.search(user_id);

        Call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if(response.isSuccessful()){
                    rvVehicle.setAdapter(new VehicleAdapter(response.body().getData()));
                    rvVehicle.setLayoutManager(new LinearLayoutManager(MisVehicles.this));
                    rvVehicle.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {

            }
        });
    }
}