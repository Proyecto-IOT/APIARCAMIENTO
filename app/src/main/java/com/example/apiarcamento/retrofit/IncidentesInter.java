package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.Humo;
import com.example.apiarcamento.models.Sonido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IncidentesInter {
    @GET("api/v3/arduino/humo")
    Call<Humo> getDatosHumo();

    @GET("api/v3/arduino/sonido")
    Call<Sonido> getDatosSonido();
}
