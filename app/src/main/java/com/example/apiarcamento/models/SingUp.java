package com.example.apiarcamento.models;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SingUp {
    @POST("api/login/")
    Call<User> enviarDatos(@Body User usuario);
}
