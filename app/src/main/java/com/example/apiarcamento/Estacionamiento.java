package com.example.apiarcamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiarcamento.models.SingUp;
import com.example.apiarcamento.models.Spot;
import com.example.apiarcamento.models.User;
import com.example.apiarcamento.retrofit.ArduinoInterface;
import com.example.apiarcamento.view.fragments.Ocupado;
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

    ImageView img1,img2,img3, img4,img5,img6,img7,img8,img9,img10,img11,img12;
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

        img1=findViewById(R.id.imgC1);
        img2=findViewById(R.id.imgC2);
        img3=findViewById(R.id.imgC3);
        img4=findViewById(R.id.imgC4);
        img5=findViewById(R.id.imgC5);
        img6=findViewById(R.id.imgC6);
        img7=findViewById(R.id.imgC7);
        img8=findViewById(R.id.imgC8);
        img9=findViewById(R.id.imgC9);
        img10=findViewById(R.id.imgC10);
        img11=findViewById(R.id.imgC11);
        img12=findViewById(R.id.imgC12);

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
                                img1.setVisibility(View.VISIBLE);
                                break;
                            case(2):
                                img2.setVisibility(View.VISIBLE);

                                break;
                            case(3):
                                img3.setVisibility(View.VISIBLE);

                                break;
                            case(4):
                                img4.setVisibility(View.VISIBLE);

                                break;
                            case(5):
                                img5.setVisibility(View.VISIBLE);

                                break;
                            case(6):
                                img6.setVisibility(View.VISIBLE);

                                break;
                            case(7):
                                img7.setVisibility(View.VISIBLE);

                                break;
                            case(8):
                                img8.setVisibility(View.VISIBLE);

                                break;
                            case(9):
                                img9.setVisibility(View.VISIBLE);

                                break;
                            case(10):
                                img10.setVisibility(View.VISIBLE);

                                break;
                            case(11):
                                img11.setVisibility(View.VISIBLE);

                                break;
                            case(12):
                                img12.setVisibility(View.VISIBLE);
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
        View.OnClickListener cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClick(view);
            }
        };

        cardView2.setOnClickListener(cardClickListener);
        cardView12.setOnClickListener(cardClickListener);

    }
    public void onCardClick(View view) {
        Log.e("RetrofitError", "Error en la llamada a la API");

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
        ImageView imageView=(ImageView) view;
        CardView cardView = (CardView) view;

        if(cardView.getVisibility()==View.VISIBLE){
            //Ocupado fragmentt=Ocupado.newInstance(id);
            //fragmentt.show(getSupportFragmentManager(), fragmentt.getTag());
        }else{
            //fragment fragmentt=fragment.newInstance(id);
            //fragmentt.show(getSupportFragmentManager(), fragmentt.getTag());
        }



    }
}