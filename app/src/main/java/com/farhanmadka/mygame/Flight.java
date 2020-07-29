package com.farhanmadka.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.farhanmadka.mygame.GameViewer.screenRatioX;
public class Flight {
    public int toShoot=0;
    int x, y,width, height,winCounter=0,shootCounter=1;
    Bitmap flight1,flight2,shoot1,shoot2,shoot3,dead;
    public boolean isGoingUp=false, flight_move_forward=false;
    GameViewer gameView;
    Flight(GameViewer gameView, int screenY, Resources res){
        this.gameView = gameView;
        flight1 = BitmapFactory.decodeResource(res,R.drawable.defender);
        flight2 = BitmapFactory.decodeResource(res,R.drawable.defender);
        width = flight1.getWidth();
        height = flight1.getHeight();
        dead = BitmapFactory.decodeResource(res,R.drawable.bumb);
//        width /=5;
//        height /=4;
        width /=5;
        height /=4;
        width *=(int) screenRatioX;
        height *=(int) screenRatioX;

        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false);
        y = screenY / 2;
        x = (int) (64 * screenRatioX);
        // shoots
        shoot1 = BitmapFactory.decodeResource(res,R.drawable.defender);
        shoot2 = BitmapFactory.decodeResource(res,R.drawable.defender);
        shoot3 = BitmapFactory.decodeResource(res,R.drawable.defender);
        // resizing
        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        dead = Bitmap.createScaledBitmap(dead,width,height,false);
    }
    Bitmap getFlight(){
        if (toShoot!=0){
            if (shootCounter==1){
                shootCounter++;
                return shoot1;
            }
            if (shootCounter==2){
                shootCounter++;
                return shoot2;
            }
            shootCounter=1;
            toShoot--;
            gameView.newBullet();
            return shoot3;
        }
        if (winCounter==0){
            winCounter++;
            return flight1;
        }
        winCounter--;
        return flight2;
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x + width,y+height);
    }
     Bitmap getDead(){
        return dead;
     }
}
