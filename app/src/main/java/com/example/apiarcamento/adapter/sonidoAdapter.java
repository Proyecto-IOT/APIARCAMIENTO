package com.example.apiarcamento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Incidenteresponse;
import com.example.apiarcamento.view.RegistroIncidentes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class sonidoAdapter extends RecyclerView.Adapter<sonidoAdapter.ViewHolder> {
    private List<Incidenteresponse.Sonido> dataSonido;

    public sonidoAdapter(List<Incidenteresponse.Sonido> dataSonido) {
        this.dataSonido = dataSonido;
    }

    @NonNull
    @Override
    public sonidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View vs = layoutInflater.inflate(R.layout.incidentesitem, parent, false);
        return new ViewHolder(vs);
    }

    @Override
    public void onBindViewHolder(@NonNull sonidoAdapter.ViewHolder holder, int position) {
        Incidenteresponse.Sonido sonido = dataSonido.get(position);
        holder.setData(sonido);
    }

    @Override
    public int getItemCount() {
        return dataSonido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomIncidente, fechaIncidente;
        Incidenteresponse.Sonido SonidoH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomIncidente = itemView.findViewById(R.id.tipoIncidente);
            fechaIncidente = itemView.findViewById(R.id.fechaIncidente);
        }

        public void setData(Incidenteresponse.Sonido sonido) {
            this.SonidoH = sonido;
            DateTimeFormatter formatoFechaHora = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime fechaHora = LocalDateTime.parse(sonido.getCreatedAt(), formatoFechaHora);
            DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String resultado = fechaHora.format(formatoDeseado);
            nomIncidente.setText("Sonido fuerte detectado");
            fechaIncidente.setText(resultado);
        }
    }
}
