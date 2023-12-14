package com.example.apiarcamento.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apiarcamento.models.Activityresponse;
import com.example.apiarcamento.repository.repositorio;

public class regviewmodel extends AndroidViewModel {
   private repositorio regrepository;
   private LiveData<Activityresponse> registerresponse;
    public regviewmodel(@NonNull Application application) {
        super(application);
        SharedPreferences sharedPref = getApplication().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int user_id = sharedPref.getInt("id", 0);
    regrepository=new repositorio();
    this.registerresponse=regrepository.getregister(user_id);
    }

    public LiveData<Activityresponse> getRegisterresponselivedata(){
        return registerresponse;
    }

}
