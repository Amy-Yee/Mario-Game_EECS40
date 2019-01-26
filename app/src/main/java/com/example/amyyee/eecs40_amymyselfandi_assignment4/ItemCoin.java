package com.example.amyyee.eecs40_amymyselfandi_assignment4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ItemCoin {
    Bitmap coin;
    float x;
    float y;
    int value = 200;

    public ItemCoin(Context c, int x, int y) {
        coin = BitmapFactory.decodeResource(c.getResources(), R.drawable.coin);
        this.x = x;
        this.y = y;
    }

    public boolean visit(float bobX, float bobY) {
        if (bobX<=x+200 || bobX>=x  || bobY<=y+150 || bobY>= y) {
            // && bobX>=x  || && bobY<=y+150 || bobY>= y
            return true;
        }
        return false;
    }


}
