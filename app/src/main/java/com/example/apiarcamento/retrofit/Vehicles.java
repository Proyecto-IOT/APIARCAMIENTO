package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.Spot;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Vehicles {
    @POST("api/v3/vehicle/search")
    Call<Vehicle> search(@Body User user_id);

    @POST("api/v3/vehicle/register")
    Call<Vehicle> register(@Body Vehicle.Result vehicle);
    @POST("api/v3/vehicle/ocupate")
    Call<Vehicle.Result> ocupate(@Body Vehicle.Result spot);
    @POST("api/v3/vehicle/delete")
    Call<Vehicle.Result> delete(@Body Vehicle.Result vehicle);

}
