package com.example.apiarcamento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Vehicle;

import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    List<Vehicle.Result> vehiculo;

    public AddAdapter(List<Vehicle.Result> vehi){
        this.vehiculo=vehi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle.Result vecle=vehiculo.get(position);
        holder.SetData(vecle);
    }

    @Override
    public int getItemCount() {
        return vehiculo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView marca, modelo, matricula, color;
        Vehicle.Result vh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            marca=itemView.findViewById(R.id.tvMarca);
            modelo=itemView.findViewById(R.id.tvModelo);
            matricula=itemView.findViewById(R.id.tvMatricula);
            color=itemView.findViewById(R.id.tvColor);
        }
        public void SetData(Vehicle.Result vehicc){
            this.vh=vehicc;
            marca.setText(vehicc.getBrand());
            modelo.setText(vehicc.getModel());
            matricula.setText(vehicc.getLicensePlate());
            color.setText(vehicc.getColor());
        }
    }
}
