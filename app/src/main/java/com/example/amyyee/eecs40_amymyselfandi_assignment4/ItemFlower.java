package com.example.amyyee.eecs40_amymyselfandi_assignment4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ItemFlower {
    Bitmap flower;
    float x;
    float y;
    int value = 1000;

    public ItemFlower(Context c, int x, int y) {
        flower = BitmapFactory.decodeResource(c.getResources(), R.drawable.flower);
        this.x = x;
        this.y = y;
    }

}
