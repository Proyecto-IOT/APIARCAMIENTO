package com.example.apiarcamento.view;

import static com.example.apiarcamento.Const.consts.ip;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.VehicleAdapter;
import com.example.apiarcamento.adapter.humoAdapter;
import com.example.apiarcamento.adapter.sonidoAdapter;
import com.example.apiarcamento.models.Incidenteresponse;
import com.example.apiarcamento.models.Incidentresponsehumo;
import com.example.apiarcamento.retrofit.IncidentesInter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroIncidentes extends AppCompatActivity {
    private Retrofit retrofit;
    private RecyclerView recyclerViewSonido, recyclerViewHumo;
    private humoAdapter humoAdapter;
    private sonidoAdapter sonidoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_incidentes);
        recyclerViewSonido=findViewById(R.id.rvIncidentessound);
        recyclerViewHumo=findViewById(R.id.rvIncidentesgas);
        retrofit = new Retrofit.Builder()
                .baseUrl(ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IncidentesInter sonido=retrofit.create(IncidentesInter.class);
        Call<Incidenteresponse> llamado=sonido.tomarsonidos();
        llamado.enqueue(new Callback<Incidenteresponse>() {
            @Override
            public void onResponse(Call<Incidenteresponse> call, Response<Incidenteresponse> response) {
                if(response.isSuccessful()){
                    sonidoAdapter = new sonidoAdapter(response.body().getSonido());
                    recyclerViewSonido.setAdapter(sonidoAdapter);
                    recyclerViewSonido.setLayoutManager(new LinearLayoutManager(RegistroIncidentes.this));
                    recyclerViewSonido.setHasFixedSize(true);

                }

            }
            @Override
            public void onFailure(Call<Incidenteresponse> call, Throwable t) {
                Log.e("error", "No hay internet: " );
            }
        });

        IncidentesInter humo=retrofit.create(IncidentesInter.class);
        Call<Incidentresponsehumo> coll=humo.tomarhumo();
        coll.enqueue(new Callback<Incidentresponsehumo>() {
            @Override
            public void onResponse(Call<Incidentresponsehumo> call, Response<Incidentresponsehumo> response) {
                if(response.isSuccessful()){

                    humoAdapter = new humoAdapter(response.body().getHumo());
                    recyclerViewHumo.setAdapter(humoAdapter);
                    recyclerViewHumo.setLayoutManager(new LinearLayoutManager(RegistroIncidentes.this));
                    recyclerViewHumo.setHasFixedSize(true);
                }
            }
            @Override
            public void onFailure(Call<Incidentresponsehumo> call, Throwable t) {
                Log.e("error", "No hay internet: " );
            }
        });


    }
}

