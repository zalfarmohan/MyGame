package com.farhanmadka.mygame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class dashboard extends AppCompatActivity {
    private boolean isMute;
    TextView highscore;
    ImageView easy,medium,hard;
    Custom custom;
    MusicManager musicManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        custom = new Custom();
        final Animation animTrans = AnimationUtils.loadAnimation(this, R.anim.translate);
        final Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        TextView im = findViewById(R.id.highScoreText);
        im.startAnimation(animTrans);
        easy.startAnimation(rotate);
        medium.startAnimation(rotate);
        hard.startAnimation(rotate);
        musicManager = new MusicManager();
//        Intent svc=new Intent(this, MusicManager.class);
//        startService(svc);
        findViewById(R.id.levelEasy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, GameActivity.class));
            }
        });
        findViewById(R.id.levelMedium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, GameActivity.class));
            }
        });
        findViewById(R.id.levelHard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, GameActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
        });
//        findViewById(R.id.aboutMe).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(dashboard.this, Developer.class);
//                startActivity(i);
//            }
//        });
        highscore = findViewById(R.id.highScoreText);
        //TextView last = findViewById(R.id.last);
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highscore.setText("Highest Score: " + prefs.getInt("highscore", 0));
        //last.setText("Highest Score: "+prefs.getInt("highscore",0));
        isMute = prefs.getBoolean("isMute", false);
        final ImageView volumeControl = findViewById(R.id.volumeControlUp);
        if (isMute)
            volumeControl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        else volumeControl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        volumeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMute = !isMute;
                if (isMute)
                    volumeControl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                else
                    volumeControl.setImageResource(R.drawable.ic_volume_up_black_24dp);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });
        ImageView img, rate;
        img = findViewById(R.id.shareLink);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Soo degso game-kaan, : " +
                        "https://play.google" +
                        ".com/store/apps/details?id=com.hormuuduniversity.game");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Somali Helicopter");
                startActivity(Intent.createChooser(intent, "Lawadaag Asxaabta"));
            }
        });
        rate = findViewById(R.id.updateLink);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));


                } catch (ActivityNotFoundException ac) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google" +
                            ".com/store/apps/details?id=" + getPackageName())));
                }
            }
        });
        ImageView my = findViewById(R.id.help);
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom.customDialog(dashboard.this, R.drawable.defender, "Somali Helicopter", "Sida " +
                                "loo dheelo Game-ka", "Somali Helicopter\n_________________\n" +
                                "\n1. Shaashadda Riix Labo (2x) Jeer si aad isaga difaacdo " +
                                "Diyaaradaha kaa soo horjeeda / Cadowgaaga.\n" +
                                "\n2. Si aad diyaaradda kor ugu kacdo suulka/farahan ku " +
                                "dheeleysid " +
                                "ka qaad.\n" +
                                "\n3. Haddii aad hoos dooneysid inaad soo aado shaashadda qabo " +
                                "ama " +
                                "suulka ku haay.\n" +
                                "\n4. Marwalbana xabadaha yeysan kaa istaagin, Waxaa macquul ah " +
                                "inaad weyso jaanis xabad ku riddo.\n " +
                                "\n5. Xawaaraha diyaaradaha qaar wey hooseeyaan, kuwa kuxiga " +
                                "xawaarahooda iskama difaaci kartid. Marwalbo xasuusnow lambarka " +
                                "koowaad.\n\n",
                        "Waxbadan dil, dhibco badan hel!!.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        GameActivity gameActivity;
        gameActivity=new GameActivity();
        gameActivity.exitGame();
        super.onBackPressed();
    }
}
