package com.farhanmadka.mygame;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Developer extends AppCompatActivity  {
    long longPressClicked;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.developer);
        Intent svc=new Intent(this, MusicManager.class);
        startService(svc);

        FloatingActionButton fab =  findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instruction();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void instruction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Developer.this);
        // dialog alert
        builder.setTitle("Soomaali Helicopter");
        // dialog iconinfo
        builder.setIcon(R.drawable.defender);
        // setting message
        builder.setMessage("Insha Allah qaabkaan qaab kale oo ka qurxoon ayaan u badali " +
                "doonaa.\n Waad ku mahadsan tahay inaad game-kaan la soo dagto\nGaarsii " +
                "asxaabtaada kuu dhow si iyaguna u arkaan.\nHadduu ku qanciyey adigoo mahadsan " +
                "hoos fariin noogu reeb mahadsanid.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
