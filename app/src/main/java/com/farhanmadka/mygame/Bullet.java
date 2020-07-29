package com.farhanmadka.mygame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.farhanmadka.mygame.GameViewer.screenRatioX;
import static com.farhanmadka.mygame.GameViewer.screenRatioY;


public class Bullet {
    int width, height, x=0,y=0;
    Bitmap bullet,bu_enemy;
    Bullet(Resources res){
        bullet = BitmapFactory.decodeResource(res,R.drawable.shoot1);
        bu_enemy = BitmapFactory.decodeResource(res,R.drawable.shoot2);
         width =  bullet.getWidth();
        height = bullet.getHeight();
        width = bu_enemy.getWidth();
        height=bu_enemy.getHeight();
//        width +=1;
//        height +=1;
        width /=1;
        height /=1;
        width *= screenRatioX;
        height *= screenRatioY;
        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);
        bu_enemy = Bitmap.createScaledBitmap(bu_enemy,width,height,false);
    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }

}


