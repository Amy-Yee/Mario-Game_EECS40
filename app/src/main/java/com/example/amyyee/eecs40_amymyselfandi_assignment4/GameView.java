//package com.example.amyyee.eecs40_amymyselfandi_assignment4;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//public class GameView extends SurfaceView implements Runnable {
//
//    volatile boolean playing;
//    private Thread gameThread = null;
//
//    //adding the player to this class
//    private Player player;
//
//    //These objects will be used for drawing
//    private Paint paint;
//    private Canvas canvas;
//    private SurfaceHolder surfaceHolder;
//
//    public GameView(Context context, int screenX, int screenY) {
//        super(context);
//
//        //initializing player object
//        //this time also passing screen size to player constructor
//        player = new Player(context, screenX, screenY);
//
//        //initializing drawing objects
//        surfaceHolder = getHolder();
//        paint = new Paint();
//    }
//
//    @Override
//    public void run() {
//        while (playing) {
//            update();
//            draw();
//            control();
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_UP:
//                //stopping the boosting when screen is released
//                player.stopBoosting();
//                break;
//            case MotionEvent.ACTION_DOWN:
//                //boosting the space jet when screen is pressed
//                player.setBoosting();
//                break;
//        }
//        return true;
//    }
//
//    private void update() {
//        //updating player position
//        player.update();
//    }
//
//    private void draw() {
//        //checking if surface is valid
//        if (surfaceHolder.getSurface().isValid()) {
//            //locking the canvas
//            canvas = surfaceHolder.lockCanvas();
//            //drawing a background color for canvas
//            canvas.drawColor(Color.BLACK);
//            //Drawing the player
//            canvas.drawBitmap(
//                    player.getBitmap(),
//                    player.getX(),
//                    player.getY(),
//                    paint);
//            //Unlocking the canvas
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }
//    }
//
//    private void control() {
//        try {
//            gameThread.sleep(17);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void pause() {
//        playing = false;
//        try {
//            gameThread.join();
//        } catch (InterruptedException e) {
//        }
//    }
//
//    public void resume() {
//        playing = true;
//        gameThread = new Thread(this);
//        gameThread.start();
//    }
//}
