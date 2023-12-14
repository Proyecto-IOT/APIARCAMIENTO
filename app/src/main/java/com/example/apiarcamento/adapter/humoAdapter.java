package com.example.apiarcamento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Incidenteresponse;
import com.example.apiarcamento.models.Incidentresponsehumo;
import com.example.apiarcamento.view.RegistroIncidentes;

import java.util.List;

public class humoAdapter extends RecyclerView.Adapter<humoAdapter.ViewHolder> {
    private List<Incidentresponsehumo.Humo> dataHumo;

    public humoAdapter(List<Incidentresponsehumo.Humo> dataHumo) {
        this.dataHumo = dataHumo;
    }

    @NonNull
    @Override
    public humoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View vh = layoutInflater.inflate(R.layout.incidentesitem, parent, false);
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull humoAdapter.ViewHolder holder, int position) {
     Incidentresponsehumo.Humo humo = dataHumo.get(position);
        holder.setData(humo);
    }

    @Override
    public int getItemCount() {
        return dataHumo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomIncidente, fechaIncidente;
        Incidentresponsehumo.Humo HumoH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomIncidente = itemView.findViewById(R.id.tipoIncidente);
            fechaIncidente = itemView.findViewById(R.id.fechaIncidente);
        }

        public void setData(Incidentresponsehumo.Humo humo) {
            this.HumoH = humo;
            DateTimeFormatter formatoFechaHora = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime fechaHora = LocalDateTime.parse(humo.getCreatedAt(), formatoFechaHora);
            DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String resultado = fechaHora.format(formatoDeseado);
            nomIncidente.setText("Niveles de Humo detectados");
            fechaIncidente.setText(resultado);
        }
    }
}
