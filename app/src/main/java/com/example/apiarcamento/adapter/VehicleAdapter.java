package com.example.apiarcamento.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Arduino;
import com.example.apiarcamento.models.Vehicle;
import com.example.apiarcamento.retrofit.ArduinoInterface;
import com.example.apiarcamento.retrofit.Vehicles;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {
    List<Vehicle.Result> vehiculo;

    public VehicleAdapter(List<Vehicle.Result> vehi){
        this.vehiculo=vehi;
    }

    @NonNull
    @Override
    public VehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.ViewHolder holder, int position) {
        Vehicle.Result vecle=vehiculo.get(position);
        holder.SetData(vecle);
        Context context= holder.itemView.getContext();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref =  context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int user_id = sharedPref.getInt("id", 0);

                Vehicle.Result usuario = new Vehicle.Result();
                usuario.setVehicleid(holder.vehicle_id);
                Log.e("DEBUG", "Onclckbien: " +usuario.getVehicleid());
                Log.e("DEBUG", "Onclckbien: " +holder.vehicle_id);
                consts ip=new consts();

                Retrofit rf=new Retrofit.Builder()
                        .baseUrl(ip.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Vehicles Interfaz=rf.create(Vehicles.class);
                Call<Vehicle.Result> Call=Interfaz.delete(usuario);
                Call.enqueue(new Callback<Vehicle.Result>() {
                    @Override
                    public void onResponse(Call<Vehicle.Result> call, Response<Vehicle.Result> response) {

                        if(response.isSuccessful()){
                            Log.e("DEBUG", "Onclckbien: " );
                            ((Activity)context).recreate();
                        }else{
                            Log.e("DEBUG", "Onclckmal: "+response.code() );
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Vehicle.Result> call, Throwable t) {
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
        TextView marca, modelo, matricula, color, delete;
        long vehicle_id;
        Vehicle.Result vh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.tvDelete);
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
