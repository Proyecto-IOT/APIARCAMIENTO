    package com.example.apiarcamento.view.fragments;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;

    import com.example.apiarcamento.Const.consts;
    import com.example.apiarcamento.R;
    import com.example.apiarcamento.adapter.AddAdapter;
    import com.example.apiarcamento.adapter.VehicleAdapter;
    import com.example.apiarcamento.models.User;
    import com.example.apiarcamento.models.Vehicle;
    import com.example.apiarcamento.retrofit.Vehicles;
    import com.example.apiarcamento.view.Add_vehicle;
    import com.example.apiarcamento.view.MisVehicles;
    import com.example.apiarcamento.view.signup;
    import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;


    public class fragment extends BottomSheetDialogFragment {

        private static final String ARG_SPOT = null;
        RecyclerView rvSpot;
        TextView tvTitulo;

        // Otros métodos y variables de tu fragmento

        public static fragment newInstance(int nombre) {
            fragment fragment = new fragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SPOT, nombre);
            fragment.setArguments(args);
            return fragment;
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment, container, false);

            tvTitulo=view.findViewById(R.id.titulo);
            rvSpot=view.findViewById(R.id.recyclerSpot);

            SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int user_id = sharedPref.getInt("id", 0);

            User usuario = new User();
            usuario.setUserid(user_id);
            Log.e("DEBUG", "successful: "+usuario.getUserid() );
            consts ip=new consts();
            Retrofit rf=new Retrofit.Builder()
                    .baseUrl(ip.ip)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Vehicles vehiclesInterfaz=rf.create(Vehicles.class);
            Log.e("DEBUG", "successful: " );
            Call<Vehicle> Call=vehiclesInterfaz.search(usuario);
            Log.e("DEBUG", "successful: " );
            Call.enqueue(new Callback<Vehicle>() {
                @Override
                public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                    if(response.isSuccessful()){
                        Log.e("DEBUG", "megabien: " );
                        Vehicle json= response.body();
                        String msg=json.getMsg();
                        tvTitulo.setText(msg);
                        rvSpot.setAdapter(new VehicleAdapter(response.body().getData()));
                        rvSpot.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvSpot.setHasFixedSize(true);
                    }else{
                        Log.e("DEBUG", "malll: " );
                    }
                }

                @Override
                public void onFailure(Call<Vehicle> call, Throwable t) {
                    Log.e("DEBUG", "megaMal " );
                }
            });

            return view;
        }
    }