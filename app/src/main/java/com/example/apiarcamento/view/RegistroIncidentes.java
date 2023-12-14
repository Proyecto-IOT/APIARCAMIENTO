package com.example.apiarcamento.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiarcamento.Const.consts;
import com.example.apiarcamento.R;
import com.example.apiarcamento.adapter.humoAdapter;
import com.example.apiarcamento.adapter.sonidoAdapter;
import com.example.apiarcamento.models.Humo;
import com.example.apiarcamento.models.Sonido;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.retrofit.IncidentesInter;

import java.util.ArrayList;

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

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);
        User usuario = new User();
        usuario.setUserid(user_id);
        consts ip = new consts();

        retrofit = new Retrofit.Builder()
                .baseUrl(ip.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
}

