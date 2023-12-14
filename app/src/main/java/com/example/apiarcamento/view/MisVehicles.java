package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.VehicleAdapter;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;
import com.example.apiarcamento.view.fragments.HomeFragment;
import com.example.apiarcamento.view.fragments.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisVehicles extends AppCompatActivity {

    ConstraintLayout add;
    RecyclerView rvVehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_vehicles);

        add=findViewById(R.id.clAgregar);
        rvVehicle=findViewById(R.id.recycler);

        Intent IntentAdd=new Intent(this, Add_vehicle.class);

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);
        User usuario = new User();
        usuario.setUserid(user_id);
        consts ip=new consts();
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(ip.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Vehicles vehiclesInterfaz=rf.create(Vehicles.class);
        Call<Vehicle> Call=vehiclesInterfaz.search(usuario);
        Call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if(response.isSuccessful()){
                    Log.e("DEBUG", "successfulLLL: " );
                    rvVehicle.setAdapter(new VehicleAdapter(response.body().getData()));
                    rvVehicle.setLayoutManager(new LinearLayoutManager(MisVehicles.this));
                    rvVehicle.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentAdd);
                finish();
            }
        });
    }
}