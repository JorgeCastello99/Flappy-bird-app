package com.example.videojuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
    }


    public void onClickReiniciar(View view) {

        Intent abrirActividadFinal=new Intent(this,MainActivity.class);
        startActivity(abrirActividadFinal);
    }

    public void onClickSalir(View view) {


    }
}
