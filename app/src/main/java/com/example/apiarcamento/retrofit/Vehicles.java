package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Vehicles {
    @GET("api/v3/vehicle/search/")
    Call<Vehicle> search(@Body int user_id);
}
