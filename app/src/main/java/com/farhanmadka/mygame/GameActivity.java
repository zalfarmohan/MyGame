package com.farhanmadka.mygame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    Dialog dialog, dialog1;
    ImageView imageView, imgxarunta;
    TextView txtTitle, txtMessage, txtxarunta, txtxarunttile;
    private GameViewer gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameViewer(this,point.x,point.y);
        setContentView(gameView);
    }


    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Laba jeer riix si aad uga baxdo", Toast.LENGTH_SHORT).show();
        //super.onPause();
        gameView.pause();
        System.exit(0);

    }


    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
    public void exitGame(){
        System.exit(0);
    }
}

