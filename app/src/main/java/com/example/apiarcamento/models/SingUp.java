package com.example.apiarcamento.models;


import android.util.Log;

import com.example.apiarcamento.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SingUp {
    @POST("api/v3/user/register")
    Call<User> enviarDatos(@Body User usuario);
    @POST("api/v3/user/login")
    Call<User>  login(@Body User usuario);
    @POST("api/v3/user/logout")
    Call<User> logout(@Header("Authorization") String token);

    @GET("api/v3/user/user-info")
    Call<User> edit(@Header("Authorization") String token);

    @PUT("api/v3/user/update")
    Call<User> editarDatos(@Body User usuario);
}
