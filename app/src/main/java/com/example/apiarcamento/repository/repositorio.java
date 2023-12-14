package com.example.apiarcamento.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apiarcamento.models.Activityresponse;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.retrofit.ApiRequest;
import com.example.apiarcamento.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class repositorio {
    private ApiRequest apiRequest;

    public repositorio() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<Activityresponse> getregister(int id) {
        final MutableLiveData<Activityresponse> data = new MutableLiveData<>();
        User usuario=new User();
        usuario.setUserid(id);
        apiRequest.registros(usuario)
                .enqueue(new Callback<Activityresponse>() {
                    @Override
                    public void onResponse(Call<Activityresponse> call, Response<Activityresponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Activityresponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }



}
