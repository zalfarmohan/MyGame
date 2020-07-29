package com.farhanmadka.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.farhanmadka.mygame.GameViewer.screenRatioX;
import static com.farhanmadka.mygame.GameViewer.screenRatioY;

public class Attackers {
    public boolean wasShoot=true;
    public int x=0;
    public int y;
    public int width;
    public int height;
    int attCounter=1;
    public int speed=20;
    int width1;
    public int height1;
    Bitmap att1,att2,att3,att4,deadAtt;
    public Attackers(Resources res){
        att1 = BitmapFactory.decodeResource(res,R.drawable.att1);
        att2 = BitmapFactory.decodeResource(res,R.drawable.att1);
        att3 = BitmapFactory.decodeResource(res,R.drawable.att1);
        att4 = BitmapFactory.decodeResource(res,R.drawable.att1);
        //deadAtt = Bitmap.createScaledBitmap(deadAtt,width,height,false);

       // att5 = BitmapFactory.decodeResource(res,R.drawable.att5);

        // Reducing the helicopter.
        width = att1.getWidth();
        height = att1.getHeight();

        width /=7;
        height /=6;

        width=(int)(width*screenRatioX);
        height=(int)(height*screenRatioY);
//        width  *= screenRatioX;
//        height *= screenRatioY;

        att1 = Bitmap.createScaledBitmap(att1,width,height,false);
        att2 = Bitmap.createScaledBitmap(att2,width,height,false);
        att3 = Bitmap.createScaledBitmap(att3,width,height,false);
        att4 = Bitmap.createScaledBitmap(att4,width,height,false);
        //deadAtt = Bitmap.createScaledBitmap(deadAtt,width,height,false);

        y =- height;
        y =- height1;
       // getAttacers();
    }
    public Bitmap getAttacers(){
        if (attCounter ==1){
            attCounter++;
            return att1;
        }
        if (attCounter==2){
            attCounter ++;
            return att2;
        }
        if (attCounter==3){
            attCounter++;
            return att3;
        } if (attCounter==4){
            attCounter++;

            return att4;
        }
        attCounter =1;
        return att4;

    }

    public Rect getCollisionShape(){
        return new Rect(x,y,x + width,y+height);
    }
//    Bitmap getDeadAtt(){
//        return deadAtt;
//    }
}
