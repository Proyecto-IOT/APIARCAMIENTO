package com.example.apiarcamento.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apiarcamento.R;
import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.view.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {
    TextView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_profile, container, false);

        logout=vista.findViewById(R.id.tvLogout);

        Intent Intentlogout=new Intent(getContext(), MainActivity.class);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.115:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SingUp singupinterface=retrofit.create(SingUp.class);
                Call<User> userCall=singupinterface.logout();

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                            SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                            // Edita las SharedPreferences
                            SharedPreferences.Editor editor = sharedPref.edit();

                            // Almacena el valor id
                            editor.putInt("id", 0);

                            // Aplica los cambios
                            editor.apply();

                            startActivity(Intentlogout);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("RetrofitError", "Error en la llamada a la API", t);

                        //startActivity(nojala);

                    }
                });
            }
        });
        return vista;

    }
}