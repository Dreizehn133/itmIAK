package com.example.dreizehn.mypopularmoviesproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.dreizehn.mypopularmoviesproject.utility.util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Splash extends AppCompatActivity {
    @BindView(R.id.prog)ProgressBar prg;
  //  @BindView(R.id.num)TextView tex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tesKoneksi();
    }
    private void StartAct(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    private void progres(){
        for (int i = 0; i <= 100; i+=10) {
            try {

                Thread.sleep(500);
                prg.setProgress(i);
      //          tex.setText(i+"%");


            }catch (Exception e){

            }
        }
        StartAct();
    }
    private void tesKoneksi(){
        if(!util.isConnected(this)) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("No Internet Connection");
            ad.setMessage("Pastikan Anda Terkoneksi di Internet!!").setCancelable(false)
                    .setPositiveButton("Repeat!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog al=ad.create();
            al.show();

            return;
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progres();
                }
            }).start();
        }
    }



}
