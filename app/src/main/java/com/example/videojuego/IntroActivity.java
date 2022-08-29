package com.example.videojuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        final Intent nuevaVentana=new Intent(this , MainActivity.class);

        Thread thread = new Thread () {
            @Override
            public void run() {
                try {Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                } finally {

                    startActivity(nuevaVentana,null);
                }

            }
        };

        thread.start();
    }
}

