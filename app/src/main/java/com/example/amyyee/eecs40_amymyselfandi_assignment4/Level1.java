package com.example.amyyee.eecs40_amymyselfandi_assignment4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Level1 {
    ItemCoin coin1;
    ItemCoin coin2;
    ItemMushroom mushroom;
    ItemFlower flower;

    public Level1 (Context context) {
        coin1 = new ItemCoin(context, 200, 1000);
        coin2 = new ItemCoin(context, 500, 1000);
        mushroom = new ItemMushroom(context, -300, 750);
        flower = new ItemFlower(context, -400, 1000);

    }
}
