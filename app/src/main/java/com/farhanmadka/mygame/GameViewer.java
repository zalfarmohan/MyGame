package com.farhanmadka.mygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameViewer extends SurfaceView implements Runnable {
    Custom custom;
    private boolean isPlaying, isgameOver = false;
    private Thread thread;
    private SharedPreferences prefrence;
    private int screenX, screenY, score = 0, missess = 0;

    // Paint LEvel
    private Paint paintLevel = new Paint();
    private int level;
    // Paint Life
    private Bitmap bitmapLife[] = new Bitmap[2];
    private int lifeCount;

    public static float screenRatioX, screenRatioY;
    private Background background1, background2;
    private Paint paint, paint2;
    Button btnFire;
    private List<Bullet> bullets, bullets2;
    private Flight flight;
    //private Attackers attacker;
    private Attackers[] attackers;
    private Random random;
    private int sound, sound2;
    private SoundPool soundPool, soundPool2, soundCrashed;
    GameActivity gameActivity;
    //
    public GameViewer(GameActivity gameActivity, int screenX, int screenY) {
        super(gameActivity);
        custom = new Custom();
        this.gameActivity = gameActivity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes1 = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            soundPool2 = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes1)
                    .setMaxStreams(50)
                    .build();

        } else
            soundPool2 = new SoundPool(5, AudioManager.STREAM_MUSIC, 2);
        sound2 = soundPool2.load(gameActivity, R.raw.sound_helicopter, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(gameActivity, R.raw.sound, 2);

        //sound = soundPool.load(gameActivity,)
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 2688f / screenX;
        screenRatioY = 1242f / screenY;

//        screenRatioX = 1920f / screenX;
//        screenRatioY = 1080f / screenY;
        //1242 x 2688
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.x = screenX;
        paint = new Paint();
        paint2 = new Paint();
        bullets = new ArrayList<>();
        bullets2 = new ArrayList<>();
        random = new Random();
        attackers = new Attackers[4];
        for (int i = 0; i < 4; i++) {
            Attackers attacker = new Attackers(getResources());
            attackers[i] = attacker;
        }

        // Level Paint
        paintLevel.setColor(Color.BLACK);
        paintLevel.setTextSize(32);
        paintLevel.setTypeface(Typeface.DEFAULT_BOLD);
        paintLevel.setAntiAlias(true);
        level=1;

        // Life Paint
        bitmapLife[0] = BitmapFactory.decodeResource(getResources(),R.drawable.star);
        bitmapLife[0] = BitmapFactory.decodeResource(getResources(),R.drawable.star);
        bitmapLife[0] = BitmapFactory.decodeResource(getResources(),R.drawable.star);

        bitmapLife[1] = BitmapFactory.decodeResource(getResources(),R.drawable.broken_heart);
        bitmapLife[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_life);
        bitmapLife[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_life);

        lifeCount=3;




        //attacker= new Attackers(getResources());
        flight = new Flight(this, screenY, getResources());
        paint2.setTextSize(50);
        paint2.setColor(Color.BLACK);
        // score
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
        prefrence = gameActivity.getSharedPreferences("game", Context.MODE_PRIVATE);
        btnFire = new Button(getContext());
    }


    @Override
    public void run() {
        soundPool2.play(sound2, 1, 1, 0, 10, 1);
        while (isPlaying) {

            update();
            sleep();
            draw();

        }

    }

    private void update() {
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }if (flight.isGoingUp)
            flight.y -= 5 * screenRatioY;

        else
            flight.y += 5 * screenRatioY;
        if (flight.y < 0)
            flight.y = 0;
        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;
        List<Bullet> trash = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;
            for (Attackers attack : attackers) {
                if (Rect.intersects(attack.getCollisionShape(), bullet.getCollisionShape())) {
                    score ++;
                    attack.x = -500;
                    bullet.x = screenX + 500;
                    attack.wasShoot = true;
                } else if(Rect.intersects(attack.getCollisionShape(),bullet.getCollisionShape())){

                    attack.wasShoot = true;
                }
            }
        }
        for (Bullet bullet : trash)
            bullets.remove(bullet);
        for (Attackers att : attackers) {
            att.x -= att.speed;
            //att.x -= att.speed;
            if (att.x + att.width < 0) {
                // haddi diyaaradda cadow-ga leh aadan xabbad ku dhufan oo ka gudubto waa dhamaday game-ka.
                if (!att.wasShoot) {
                    isgameOver = true;

                    return;
                }

                int bound = (int) (30 * screenRatioX);
                att.speed = random.nextInt(bound);
                if (att.speed < 10 * screenRatioX)
                    att.speed = (int) (10 * screenRatioX);
                att.x = screenX;
                att.y = random.nextInt(screenY - att.height);
                //att.y = random.nextInt(screenY - att.height1);
//                if (score==10){
//                    att.speed++;
//                }else
//                    att.speed +=2;
                att.wasShoot = true;
            }
            // haddii game-ka labada diyaarad midda cadow-ga leh kugu dhacdo waa dhamaaday game-ka
            if (Rect.intersects(flight.getCollisionShape(), att.getCollisionShape())) {
                    isgameOver=true;
                soundPool2.stop(sound2);

            }
        }

    }

    // muuqaalka guud sawirka game-ka kusoo baxaaayo.
    private void draw() {



        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            // Xisaabinta diyaaradaha aan xabadda lagu dhufan.

                // Level Paint
            //canvas.drawText("Lv. "+  (level++),canvas.getWidth() /2,60,paintLevel);

            // life counting
            /*for (int i=0; i<3; i++){
                int x= (int) (560 + bitmapLife[0].getWidth() * 1.5 * i);
                int y=30;
                if (i < lifeCount){
                    canvas.drawBitmap(bitmapLife[0],x,y,null);
                }
                else{
                    canvas.drawBitmap(bitmapLife[1],x,y,null);
                }

            }*/
            // bitmaplife score


            Paint paintMisses = new Paint();
            paintMisses.setColor(Color.RED);
            paintMisses.setTextSize(60);
            paintMisses.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            //canvas.drawText( "Weysay: "+missess, screenX /2f, 60, paintMisses);
            if (score==5){
//                canvas.drawText("Sifiican ayad u dheeleysaa",screenX/4f,60,paintMisses);
                //int x= (int) (560 + bitmapLife[0].getWidth() * 1.5 * 1);
                //int y=30;
                canvas.drawBitmap(bitmapLife[0],560,30,null);
                canvas.drawBitmap(bitmapLife[0],660,30,null);
                canvas.drawBitmap(bitmapLife[0],760,30,null);
            }else if (score==7){
                canvas.drawBitmap(bitmapLife[1],560,30,null);
                canvas.drawBitmap(bitmapLife[1],660,30,null);
                canvas.drawBitmap(bitmapLife[1],760,30,null);
            }else if (score==50){
                canvas.drawText("Isdifaac!!!!!!!!",screenX/4f,100,paintMisses);
            }else if (score==52){
                canvas.drawText("Duuliye Wanaaagsan!!",screenX/4f,60,paintMisses);
            }
            //  Xisaabinta diyaaraddaha xabbadda lagu dhuftay tiradooda.
            Paint myPaint = new Paint();
            myPaint.setColor(Color.BLACK);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            myPaint.setTextSize(60);
            myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText( "Dhibco: "+score, screenX /2f, 164, myPaint);


            for (Attackers att : attackers)
                canvas.drawBitmap(att.getAttacers(), att.x, att.y, paint);
            //canvas.drawText(score + "", screenX / 2f, 164, paint);

            if (isgameOver) {
                isPlaying = true;
                canvas.drawBitmap(flight.getDead(), flight.x, flight.y, paint);
                //canvas.drawBitmap(attacker.getDeadAtt(),attacker.x, attacker.y,paint);
                setIsgameOver(canvas);
                getHolder().unlockCanvasAndPost(canvas);
                saveMyScore();
                waitBeforeExisting();
                ///alertInfo(canvas);
                return;
            }

            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);
            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                canvas.save();



        }

    }

    // inta 3second game-ka ka bixista qaadanaayo.
    private void waitBeforeExisting() {
        try {

            Thread.sleep(3000);
            gameActivity.exitGame();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Keydinta Tirada guud isticmaalaha keenay.
    private void saveMyScore() {
        if (prefrence.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefrence.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
//        else if (prefrence.getInt("highscore",0)>100){
//            //soundPool2.play(sound2,1,1,1,2,1);
//        }
    }

    private void sleep() {
        try {
            Thread.sleep(12);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if(event.getX()<screenX) {
                    flight.isGoingUp = true;
                    flight.toShoot++;
                }
                else flight.toShoot++;

                break;
            case MotionEvent.ACTION_DOWN:
                flight.isGoingUp = false;
                if (event.getX() > screenY) {
                    // diyaaradda yey hoos udagin.
                    flight.isGoingUp = false;
                    flight.toShoot++;
                }
               //default: flight.toShoot++;
                break;

        }
        return true;
    }



    public void newBullet() {
        if (!prefrence.getBoolean("isMute", false))
            soundPool.play(sound, 1, 1, 0, 0, 1);
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + (flight.width / 2);
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
    public void setIsgameOver(Canvas canvas){
        Paint myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myPaint.setFilterBitmap(true);
        myPaint.ascent();
        myPaint.clearShadowLayer();
        myPaint.setTextSize(50);
       myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        //myPaint.setTypeface(Typeface.create(Typeface.MONOSPACE,Typeface.BOLD));
        canvas.drawText( "WAA LAGAA BADIYEY (GAME OVER)!", screenY /4f, 290, myPaint);
        //alertInfo(canvas);

    }
    public void alertInfo(Canvas canvas){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GUUL DARRO")
                .setMessage("Waad ku mahadsan tahay inaad dheesho game-kaan.")
                .setCancelable(false)
                .setPositiveButton("Gartay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Good Job.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
