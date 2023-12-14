package com.example.apiarcamento.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.Estacionamiento;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Arduino;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.ArduinoInterface;
import com.example.apiarcamento.retrofit.Vehicles;
import com.example.apiarcamento.view.MisVehicles;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    List<Vehicle.Result> vehiculo;

    public AddAdapter(List<Vehicle.Result> vehi){
        this.vehiculo=vehi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.view2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle.Result vecle=vehiculo.get(position);
        holder.SetData(vecle);
        Context context= holder.itemView.getContext();

        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("DEBUG", "Onclck: " );
                Intent intent=new Intent(context, Estacionamiento.class);
                SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPref.getInt("parking", 0);
                Log.e("DEBUG", "shared: "+id );
                Arduino ids=new Arduino();
                ids.setParking_id(id);
                ids.setVehicle_id(holder.vehicle_id);
                consts ip=new consts();

                Retrofit rf=new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ArduinoInterface ArduinoInterfaz=rf.create(ArduinoInterface.class);
                Call<Arduino> Call=ArduinoInterfaz.park(ids);
                Call.enqueue(new Callback<Arduino>() {
                    @Override
                    public void onResponse(Call<Arduino> call, Response<Arduino> response) {
                        Log.e("DEBUG", "Parking: "+ids.getParking_id() );
                        Log.e("DEBUG", "Vehicle: "+ids.getVehicle_id() );
                        Log.e("DEBUG", "Onclckmedio: " );
                        if(response.isSuccessful()){
                            Log.e("DEBUG", "Onclckbien: " );
                            context.startActivity(intent);

                        }else{
                            Log.e("DEBUG", "Onclckmal: "+response.code() );
                        }
                    }
                        @Override
                        public void onFailure(Call<Arduino> call, Throwable t) {
                            Log.e("DEBUG", "OnclckMal: " );
                        }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehiculo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView marca, modelo, matricula, color;
        ConstraintLayout cl;
        Vehicle.Result vh;
        long vehicle_id;
        int parking_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cl=itemView.findViewById(R.id.constraint);
            marca=itemView.findViewById(R.id.tvMarca);
            modelo=itemView.findViewById(R.id.tvModelo);
            matricula=itemView.findViewById(R.id.tvMatricula);
            color=itemView.findViewById(R.id.tvColor);
        }
        public void SetData(Vehicle.Result vehicc){
            this.vh=vehicc;
            vehicle_id=vehicc.getVehicleid();
            marca.setText(vehicc.getBrand());
            modelo.setText(vehicc.getModel());
            matricula.setText(vehicc.getLicensePlate());
            color.setText(vehicc.getColor());
        }
    }
}
