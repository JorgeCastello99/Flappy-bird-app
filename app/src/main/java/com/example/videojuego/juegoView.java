package com.example.videojuego;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.logging.LogRecord;

public class juegoView extends View{
Handler handler;
Runnable runnable;


//Pajaro flappy
Bitmap btmFl;
Integer milisegundos=30;
Integer y=800;
Integer gravedad=5;
Integer subida=0;
Integer x=5;

//Movimiento una tuberia
Integer numero,numero2=(int) (Math.random() * 750);
Integer totalanchoT=-140;
Integer totalanchoT2=1230;
Bitmap btmTubB;
Bitmap btmTubT;
Integer totalHTuberiaT;
 //Corazones y perdidas//Puntuacion

    int puntos=-1;

Bitmap btmCora;
Bitmap btmCora2;
Bitmap btmCora3;

Bitmap btmNCora;
Bitmap btmNCora2;
Bitmap btmNCora3;

int vidas=3;

//Colisiones
Integer xTubT;
Integer xTub;
Integer yTub;
boolean colision1,colision2;

boolean colisionT,colisionB;

    public juegoView(Context context) {
        super(context);
        runnable=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        handler=new Handler();

    }

    @Override
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);


        //fondo
        Bitmap bitmapimagen = resizeImage(getContext(), R.drawable.fondoenjuego, 720, 1280);
        canvas.drawBitmap(bitmapimagen,0,0,null);

        //flappy
        subida+=gravedad;
        y+=subida;
        if(y>=900){
            y=910;
        }

        if(y<=0){
            y=0;
        }



        btmFl= resizeImage(getContext(), R.drawable.flappyamarillo, 124, 124);
        canvas.drawBitmap(btmFl,x,y,null);


        //Tuberia abajo y arriba 1
       totalanchoT=totalanchoT-15;
       totalanchoT2=totalanchoT2-15;
       if(totalanchoT<-140){
           puntos++;
           totalanchoT=getWidth()+30;

           numero =(int) (Math.random() * 700);
       }

       if(totalanchoT2<-140){
           puntos++;
           totalanchoT2=canvas.getWidth()+30;
           numero2= (int) (Math.random() * 700);
       }


       colision1=DibujaTuberias(canvas,numero+100,totalanchoT);

       colision2=DibujaTuberias(canvas,numero2+100,totalanchoT2);





        //Puntuacion
        Paint txt=new Paint();
        txt.setTextSize(50);
        txt.setColor(Color.WHITE);
        txt.setTextSize(70);
        canvas.drawText(Integer.toString(puntos),350,60,txt);


        if(colision1==true||colision2==true){

            Intent abrirActividadFinal=new Intent(getContext(),FinalActivity.class);
            abrirActividadFinal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            abrirActividadFinal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getContext().startActivity(abrirActividadFinal);


        }










        /*
        //Vidas y perder vidas
        ArrayList <Bitmap>btmArray= new ArrayList<Bitmap>();
        btmCora=resizeImage(getContext(),R.drawable.vidas,70,70);
        btmCora2=resizeImage(getContext(),R.drawable.vidas,70,70);
        btmCora3=resizeImage(getContext(),R.drawable.vidas,70,70);

        btmNCora=resizeImage(getContext(),R.drawable.vidamenos,70,70);
        btmNCora2=resizeImage(getContext(),R.drawable.vidamenos,70,70);
        btmNCora3=resizeImage(getContext(),R.drawable.vidamenos,70,70);

        if(vidas==3) {
            btmArray.add(btmCora);
            btmArray.add(btmCora2);
            btmArray.add(btmCora3);
        }

        if(vidas==2){

            btmArray.add(btmCora);
            btmArray.add(btmCora2);
            btmArray.add(btmNCora);
        }
        if(vidas==1){
            btmArray.add(btmCora);
            btmArray.add(btmNCora2);
            btmArray.add(btmNCora);
        }

        if (vidas==0){
            btmArray.add(btmNCora3);
            btmArray.add(btmNCora2);
            btmArray.add(btmNCora);
        }


            int x=450;
        for(int i=0;i<=2;i++) {

            canvas.drawBitmap(btmArray.get(i),x, 15, null);
            x+=80;
        }
        */



        //Manejador
        handler.postDelayed(runnable,milisegundos);//Pintar

    }

    public void iniciarActivity(View view) {
        //Codigo necesario para iniciar el DatosActivity
        //Intent explicito


    }

    public boolean DibujaTuberias(Canvas canvas,int numero,int totalanchoT){



        btmTubB=resizeImage(getContext(), R.drawable.tuberiaabajo, 150, 800);
        totalHTuberiaT=(-numero) + btmTubB.getHeight()+350;// Para hacer la distancia de la tuberia de arriba a ala de abajo

        canvas.drawBitmap(btmTubB,totalanchoT,totalHTuberiaT,null);//Tuberia abajo

        //totalHTuberiaT es el 0:0 de la tuberia de abajo
        colisionB=colisionesTuberias(totalanchoT,totalHTuberiaT,y,x);


        btmTubT = resizeImage(getContext(), R.drawable.tuberiaarriba, 150, 800);
        canvas.drawBitmap(btmTubT, totalanchoT, -(numero), null);//Tuberia ariiba

        //-numero es el 0 de la tuberia de arriba
        colisionT=colisionesTuberias(totalanchoT,-numero,y,x);

        if(colisionB==true|| colisionT==true){
            return true;
        }else{
            return false;
        }


    }


    public boolean colisionesTuberias(int x,int y,int yFlapy,int xFlapy){
            boolean b=false;
        int TBXArD=x+btmTubB.getWidth();
        int TBYAbI=y+btmTubB.getHeight();

        int Flx=btmFl.getWidth();
        int Fly=btmFl.getHeight();

        int FlYAbI=yFlapy+Fly;//la x de la esquina de Abajo a la izquierda es:x
        int FLXArD=xFlapy+Flx;//la y de la esquina de Arriba a la derecha es:yFlapy


            if(FlYAbI > y  && FlYAbI < TBYAbI && FLXArD > x && FLXArD<TBXArD || FlYAbI >y-2 && FlYAbI < TBYAbI && xFlapy > x && xFlapy < TBXArD-3 ||yFlapy < TBYAbI && yFlapy > y && FLXArD > x && FLXArD < TBXArD ||yFlapy > y && yFlapy < TBYAbI && xFlapy > x && xFlapy < TBXArD){
                b=true;
            }


            return b;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int accion=event.getAction();
        if(accion==MotionEvent.ACTION_DOWN){
            subida=-40;
        }
        if(accion==MotionEvent.ACTION_UP){

        }
            return true;
    }




    //Funcion que vuelve a dimensionar la imagen
    public static Bitmap resizeImage(Context ctx, int resId, int w, int h) {

        // cargamos la imagen de origen
        Bitmap BitmapOrg = BitmapFactory.decodeResource(ctx.getResources(),
                resId);

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0,width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return resizedBitmap;
    }


}
