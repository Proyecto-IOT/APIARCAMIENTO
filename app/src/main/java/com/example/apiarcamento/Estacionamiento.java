package com.example.apiarcamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.Spot;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.retrofit.ArduinoInterface;
import com.example.apiarcamento.view.fragments.fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Estacionamiento extends AppCompatActivity {

    CardView cardView, cardView2, cardView3,cardView4,cardView5,cardView6,cardView7,
    cardView8,cardView9,cardView10,cardView11,cardView12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacionamiento);

        cardView=findViewById(R.id.cardview1);
        cardView2=findViewById(R.id.cardview2);
        cardView3=findViewById(R.id.cardview3);
        cardView4=findViewById(R.id.cardview4);
        cardView5=findViewById(R.id.cardview5);
        cardView6=findViewById(R.id.cardview6);
        cardView7=findViewById(R.id.cardview7);
        cardView8=findViewById(R.id.cardview8);
        cardView9=findViewById(R.id.cardview9);
        cardView10=findViewById(R.id.cardview10);
        cardView11=findViewById(R.id.cardview11);
        cardView12=findViewById(R.id.cardview12);

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://192.168.1.115:8000/")
                .baseUrl("http://192.168.116.78:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArduinoInterface Arduinoointerface=retrofit.create(ArduinoInterface.class);
        Call<Spot> call=Arduinoointerface.refresh();
        call.enqueue(new Callback<Spot>() {
            @Override
            public void onResponse(Call<Spot> call, Response<Spot> response) {
                if(response.isSuccessful()){
                    Spot spotResponse = response.body();
                    List<Spot.Result> spotList = spotResponse.getData();

                    for (Spot.Result spot:spotList) {
                        int idjson=spot.getParking_id();

                        switch (idjson){
                            case(1):
                                cardView.setVisibility(View.VISIBLE);
                                break;
                            case(2):
                                cardView2.setVisibility(View.VISIBLE);

                                break;
                            case(3):
                                cardView3.setVisibility(View.VISIBLE);

                                break;
                            case(4):
                                cardView4.setVisibility(View.VISIBLE);

                                break;
                            case(5):
                                cardView5.setVisibility(View.VISIBLE);

                                break;
                            case(6):
                                cardView6.setVisibility(View.VISIBLE);

                                break;
                            case(7):
                                cardView7.setVisibility(View.VISIBLE);

                                break;
                            case(8):
                                cardView8.setVisibility(View.VISIBLE);

                                break;
                            case(9):
                                cardView9.setVisibility(View.VISIBLE);

                                break;
                            case(10):
                                cardView10.setVisibility(View.VISIBLE);

                                break;
                            case(11):
                                cardView11.setVisibility(View.VISIBLE);

                                break;
                            case(12):
                                cardView12.setVisibility(View.VISIBLE);
                                break;

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Spot> call, Throwable t) {
                Log.e("RetrofitError", "Error en la llamada a la API", t);

                //startActivity(nojala);

            }
        });

    }
    public void onCardClick(View view) {
        int cardViewId = view.getId();

        int id=0;
        if(cardViewId==R.id.cardview1){
            id=1;
        }
        else if (cardViewId==R.id.cardview2) {
            id=2;
        }
        else if (cardViewId==R.id.cardview3) {
            id=3;
        }
        else if (cardViewId==R.id.cardview4) {
            id=4;
        }
        else if (cardViewId==R.id.cardview5) {
            id=5;
        }
        else if (cardViewId==R.id.cardview6) {
            id=6;
        }
        else if (cardViewId==R.id.cardview7) {
            id=7;
        }
        else if (cardViewId==R.id.cardview8) {
            id = 8;
        }
        else if (cardViewId==R.id.cardview9) {
            id=9;
        }
        else if (cardViewId==R.id.cardview10) {
            id=10;
        }else if (cardViewId==R.id.cardview11) {
            id=11;
        }
        else if (cardViewId==R.id.cardview12) {
            id=12;
        }
        CardView cardView = (CardView) view;

        if(cardView.getVisibility()==View.VISIBLE){
            fragment fragmentt=new fragment();
            fragmentt.show(getSupportFragmentManager(), fragmentt.getTag());

        }else{

        }



    }
}