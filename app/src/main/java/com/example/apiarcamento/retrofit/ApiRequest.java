package com.example.apiarcamento.retrofit;

import com.example.apiarcamento.models.Activityresponse;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.models.Vehicle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("api/v3/user/activity")
    Call<Activityresponse> registros(@Body User user_id);
}
