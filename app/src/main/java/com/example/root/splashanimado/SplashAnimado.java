package com.example.root.splashanimado;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashAnimado extends AppCompatActivity {

    private ImageView imgv;
    private ProgressBar pbar;

    private Thread hilo;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //poner vertical (portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //quitar actionbar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_animado);

        //imagenview
        imgv =(ImageView)findViewById(R.id.imageView);
        //cargando imagenes
        imgv.setBackground(getResources().getDrawable(R.drawable.listaimg));
        //iniciar la animacion
        AnimationDrawable animd=(AnimationDrawable) imgv.getBackground();
        animd.start();

        //barra de progreso
        pbar=(ProgressBar)findViewById(R.id.progressBar);

        //Que hara mientras
        hilo=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<100;i+=20){
                    try{
                        Thread.sleep(1000);
                        pbar.setProgress(i);
                    }catch (Exception e){
                        //Toast.makeText(SplashAnimado.this,"Ocurrio un problema code: "+e.getMessage(),Toast.LENGTH_LONG).show();
                        //Log.e("Error hilo",e.getMessage());
                    }
                }
                cargaMenu();
                finish();
            }
        });
        hilo.start();

    }

    private void cargaMenu() {
        if(hilo!=null){
            startActivity(new Intent().setClass(SplashAnimado.this,MainActivity.class));
        }
    }

    @Override
    public void onBackPressed(){
        if(hilo.isAlive()){
            Toast.makeText(this,"Cerrando app",Toast.LENGTH_SHORT).show();
            hilo.interrupt();
            hilo=null;
        }
        finish();
    }
}
