package com.example.apiarcamento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.Humo;
import com.example.apiarcamento.view.RegistroIncidentes;

import java.util.List;

public class humoAdapter extends RecyclerView.Adapter<humoAdapter.ViewHolder> {
    private List<Humo.Result> dataHumo;

    public humoAdapter(List<Humo.Result> dataHumo, RegistroIncidentes registroIncidentes) {
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
        Humo.Result humo = dataHumo.get(position);
        holder.setData(humo);
    }

    @Override
    public int getItemCount() {
        return dataHumo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomIncidente, fechaIncidente;
        Humo.Result HumoH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomIncidente = itemView.findViewById(R.id.tipoIncidente);
            fechaIncidente = itemView.findViewById(R.id.fechaIncidente);
        }

        public void setData(Humo.Result humo) {
            this.HumoH = humo;
            nomIncidente.setText("Niveles de Humo detectados");
            fechaIncidente.setText(humo.getCreatedAt().toString());
        }
    }
}
