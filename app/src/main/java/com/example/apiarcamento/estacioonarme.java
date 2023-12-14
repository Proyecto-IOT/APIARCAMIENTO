package com.example.apiarcamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.adapter.AddAdapter;
import com.example.apiarcamento.adapter.VehicleAdapter;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;
import com.example.apiarcamento.view.MisVehicles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class estacioonarme extends AppCompatActivity {

    RecyclerView rvSpot;
    TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacioonarme);

        tvTitulo=findViewById(R.id.titulo);
        rvSpot=findViewById(R.id.recyclerSpot);

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);

        User usuario = new User();
        usuario.setUserid(user_id);
        Log.e("DEBUG", "successful: "+usuario.getUserid() );
        consts ip=new consts();
        Retrofit rf=new Retrofit.Builder()
                .baseUrl(ip.ip)
                //.baseUrl("http://192.168.116.78:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Vehicles vehiclesInterfaz=rf.create(Vehicles.class);
        Log.e("DEBUG", "successful: " );
        Call<Vehicle> Call=vehiclesInterfaz.search(usuario);
        Log.e("DEBUG", "successful: " );
        Call.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if(response.isSuccessful()){
                    Log.e("DEBUG", "megabien: " );
                    Vehicle json= response.body();
                    String msg=json.getMsg();
                    tvTitulo.setText(msg);
                    rvSpot.setAdapter(new AddAdapter(response.body().getData()));
                    rvSpot.setLayoutManager(new LinearLayoutManager(estacioonarme.this));
                    rvSpot.setHasFixedSize(true);
                }else{
                    Log.e("DEBUG", "malll: " );
                }
            }
//8717534211
//8715668371
            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Log.e("DEBUG", "megaMal " );
            }
        });
    }
    public void onBackPressed() {
        Log.e("DEBUG", "Bck: " );
        Intent intent = new Intent(this, Estacionamiento.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}