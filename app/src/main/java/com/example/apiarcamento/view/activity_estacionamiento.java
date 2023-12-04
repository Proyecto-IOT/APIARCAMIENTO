package com.example.apiarcamento.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apiarcamento.R;

public class activity_estacionamiento extends AppCompatActivity {

    Button BtnFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacionamiento);

        BtnFragment=findViewById(R.id.fragment);

        BtnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment fragmentt=new fragment();
                fragmentt.show(getSupportFragmentManager(), fragmentt.getTag());
            }
        });
    }


}