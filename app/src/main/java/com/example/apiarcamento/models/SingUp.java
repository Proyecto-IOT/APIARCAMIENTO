package com.example.apiarcamento.models;


import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SingUp {
    @GET("api/register/")
    Call<User> enviarDatos(@Body User usuario);
}
