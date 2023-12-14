package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.Arduino;
import com.example.apiarcamento.models.Spot;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArduinoInterface {
    @POST("api/v3/arduino/enter")
    Call<Vehicle> enter(@Body User user_id);
    @POST("api/v3/arduino/exit")
    Call<Vehicle> exit();
    @POST("api/v3/arduino/park")
    Call<Arduino> park(@Body Arduino ids);
    @GET("api/v3/arduino/refresh")
    Call<Spot> refresh();
}
