package com.example.apiarcamento.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Activityresponse;
import com.example.apiarcamento.models.Vehicle;

import java.util.List;

public class registeradapter extends RecyclerView.Adapter<registeradapter.ViewHolder> {

    List<Activityresponse.Datum> registro;
    public registeradapter(List<Activityresponse.Datum> registro){
        this.registro=registro;
    }
    @NonNull
    @Override
    public registeradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.itemregi, parent, false);
        return new registeradapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull registeradapter.ViewHolder holder, int position) {
        Activityresponse.Datum reg=registro.get(position);
        holder.setdata(reg);

    }

    @Override
    public int getItemCount() {
        return registro.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lugar,carro,color,fechaentrada,fechasalida,placas;
        Activityresponse.Datum RG;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            carro=itemView.findViewById(R.id.autocompleto);
            lugar=itemView.findViewById(R.id.lugar_de_estancia);
            color=itemView.findViewById(R.id.colorcoche);
            fechaentrada=itemView.findViewById(R.id.fech_entrada);
            fechasalida=itemView.findViewById(R.id.fech_salida);
            placas=itemView.findViewById(R.id.placasdelauto);
        }

        public void setdata(Activityresponse.Datum reg) {
            RG=reg;
            carro.setText("Auto: "+reg.getBrand()+" "+reg.getModel());
            lugar.setText("Lugar: "+reg.getSpot());
            color.setText("color: "+reg.getColor());
            placas.setText("placas: "+reg.getLicensePlate());
            fechaentrada.setText("Entrada: "+reg.getCreatedAt());
            fechasalida.setText("Salida: "+reg.getUpdatedAt());


        }
    }
}
