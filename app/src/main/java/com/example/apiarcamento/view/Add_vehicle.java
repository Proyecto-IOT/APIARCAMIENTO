package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.VehicleAdapter;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.Vehicles;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_vehicle extends AppCompatActivity {

    EditText plate, brand, model, color;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        plate=findViewById(R.id.plateField);
        brand=findViewById(R.id.brandField);
        model=findViewById(R.id.modelField);
        color=findViewById(R.id.colorField);
        btnAdd=findViewById(R.id.btn_add);

        Intent  IntentBack=new Intent(this, MisVehicles.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String plateString=plate.getText().toString();
                String brandString=brand.getText().toString();
                String modelString=model.getText().toString();
                String colorString=color.getText().toString();


                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int user_id = sharedPref.getInt("id", 0);

                Vehicle.Result vehicle=new Vehicle.Result();
                vehicle.setLicensePlate(plateString);
                vehicle.setBrand(brandString);
                vehicle.setModel(modelString);
                vehicle.setColor(colorString);
                vehicle.setUserid(user_id);

                Retrofit rf=new Retrofit.Builder()
                        //.baseUrl("http://192.168.1.115:8000/")
                        .baseUrl("http://192.168.116.78:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Vehicles vehiclesInterfaz=rf.create(Vehicles.class);
                Call<Vehicle> Call=vehiclesInterfaz.register(vehicle);
                Log.e("DEBUG", "successful: " );

                Call.enqueue(new Callback<Vehicle>() {
                    @Override
                    public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                        if(response.isSuccessful()){
                            startActivity(IntentBack);
                        }
                    }

                    @Override
                    public void onFailure(Call<Vehicle> call, Throwable t) {

                    }
                });
            }
        });
    }
}