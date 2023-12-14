package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.Incidenteresponse;
import com.example.apiarcamento.models.Incidentresponsehumo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IncidentesInter {
    @GET("api/v3/arduino/humo")
        Call<Incidentresponsehumo> tomarhumo();

    @GET("api/v3/arduino/sonido")
    Call<Incidenteresponse> tomarsonidos();
}
