package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ArduinoInterface {
    @POST("api/v3/arduino/enter/")
    Call<Vehicle> enter(@Body User user_id);
    @POST("api/v3/arduino/exit/")
    Call<Vehicle> exit(@Body User user_id);
    @POST("api/v3/arduino/park/")
    Call<Vehicle> park(@Body User user_id);

}
