package com.example.amyyee.eecs40_amymyselfandi_assignment4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ItemMushroom {
    Bitmap mushroom;
    float x;
    float y;
    int value = 1000;

    public ItemMushroom(Context c, int x, int y) {
        mushroom = BitmapFactory.decodeResource(c.getResources(), R.drawable.mushroom);
        this.x = x;
        this.y = y;
    }

}
