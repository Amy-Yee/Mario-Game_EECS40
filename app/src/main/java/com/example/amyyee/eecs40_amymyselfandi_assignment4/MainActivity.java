package com.example.amyyee.eecs40_amymyselfandi_assignment4;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    Level1 lvl1;
    int level = 1;
    int lives = 3;

    boolean bobFire = true;
    boolean bobSuper = true;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    class GameView extends SurfaceView implements Runnable {
        //Modes of Bob
        boolean bobFire;
        boolean bobSuper;

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // Declare an object of type Bitmap
        Bitmap bitmapBob;
        Bitmap bitmapBobFire;
        Bitmap bitmapBobSuper;
        ItemCoin coin;
        ItemMushroom mushroom;
        ItemFlower flower;

        // Bob starts off not moving
        boolean isMoving = false;

        // He can walk at 150 pixels per second
        float walkSpeedPerSecond = 250;

        float bobXPosition = 2000;
        float bobYPosition = 1000;
        float itemXPosition = 0;
        float maxX = 1500;

        // New for the sprite sheet animation

        // These next two values can be anything you like
        // As long as the ratio doesn't distort the sprite too much
        private int frameWidth = 150;
        private int frameHeight = 200;

        private int frameCount = 5;
        private int currentFrame = 0;

        // What time was it when we last changed frames
        private long lastFrameChangeTime = 0;

        // How long should each frame last
        private int frameLengthInMilliseconds = 100;

        // A rectangle to define an area of the
        // sprite sheet that represents 1 frame
        private Rect frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);

        // A rect that defines an area of the screen
        // on which to draw
        RectF whereToDraw = new RectF(
                bobXPosition, bobYPosition,
                bobXPosition + frameWidth,
                bobYPosition + frameHeight);

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public GameView(Context context) {
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Load Bob from his .png file
            bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
            bitmapBobSuper = bitmapBob;
            bitmapBobFire = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob_fire);
            //coin = new ItemCoin(context, 300, 400);


            // Scale the bitmap to the correct size
            // We need to do this because Android automatically
            // scales bitmaps based on screen density
            bitmapBob = Bitmap.createScaledBitmap(bitmapBob,
                    frameWidth * frameCount,
                    (frameHeight*3)/4,
                    false);
            bitmapBobFire = Bitmap.createScaledBitmap(bitmapBobFire,
                    frameWidth * frameCount,
                    frameHeight,
                    false);
            bitmapBobSuper = Bitmap.createScaledBitmap(bitmapBob,
                    frameWidth * frameCount,
                    frameHeight,
                    false);

            // Set our boolean to true - game on!
            //playing = true;

            lvl1 = new Level1(context);
        }

        @Override
        public void run() {
            while (playing) {

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        // Everything that needs to be updated goes in here
        // In later projects we will have dozens (arrays) of objects.
        // We will also do other things like collision detection.

        public void update() {

            // If bob is moving (the player is touching the screen)
            // then move him to the right based on his target speed and the current fps.
            if(isMoving){
                if (bobXPosition > maxX) {
                    bobXPosition = bobXPosition - (walkSpeedPerSecond / fps);
                }
                itemXPosition += walkSpeedPerSecond/fps;


                }

            //Hitting coin1 in level 1
            if (200 + itemXPosition>bobXPosition && 1000 <= bobYPosition) {
                System.out.println("visited! ");
                lvl1.coin1.coin = Bitmap.createScaledBitmap(lvl1.coin1.coin,
                        1,
                        1,
                        false);
            }

            if (500 + itemXPosition>bobXPosition && 1000 <= bobYPosition) {
                lvl1.coin2.coin = Bitmap.createScaledBitmap(lvl1.coin2.coin,
                        1,
                        1,
                        false);
            }

            if (-300 + itemXPosition>bobXPosition && 750 <= bobYPosition) {
                lvl1.mushroom.mushroom = Bitmap.createScaledBitmap(lvl1.mushroom.mushroom,
                        1,
                        1,
                        false);
                bitmapBob = bitmapBobSuper;
            }

            if (-400 + itemXPosition>bobXPosition && 1000 <= bobYPosition) {
                lvl1.flower.flower = Bitmap.createScaledBitmap(lvl1.flower.flower,
                        1,
                        1,
                        false);
                bitmapBob = bitmapBobFire;
            }

        }

        public void getCurrentFrame(){

            long time  = System.currentTimeMillis();
            if(isMoving) {// Only animate if bob is moving
                if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                    lastFrameChangeTime = time;
                    currentFrame++;
                    if (currentFrame >= frameCount) {

                        currentFrame = 0;
                    }
                }
            }
            //update the left and right values of the source of
            //the next frame on the spritesheet
            frameToDraw.left = currentFrame * frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;

        }


        // Draw the newly updated scene
        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.argb(255,  26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(60);

                // Display the current fps on the screen
                //canvas.drawText("FPS:" + fps, 20, 40, paint);
                canvas.drawText("Score: " + score, 20, 50, paint);
                canvas.drawText("Lives: " + lives, 20, 115, paint);


                // Draw bob at bobXPosition, 200 pixels
                //canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);

                whereToDraw.set((int)bobXPosition,
                        bobYPosition,
                        (int)bobXPosition + frameWidth,
                        (int) bobYPosition + frameHeight);


                getCurrentFrame();

                canvas.drawBitmap(bitmapBob,
                        frameToDraw,
                        whereToDraw, paint);

                //DRAWING LEVEL 1
                switch(level) {
                    case 1:
                        canvas.drawBitmap(lvl1.coin1.coin,
                                lvl1.coin1.x + itemXPosition,
                                lvl1.coin1.y,
                                paint);
                        canvas.drawBitmap(lvl1.coin2.coin,
                                lvl1.coin2.x + itemXPosition,
                                lvl1.coin2.y,
                                paint);
                        canvas.drawBitmap(lvl1.mushroom.mushroom,
                                lvl1.mushroom.x + itemXPosition,
                                lvl1.mushroom.y,
                                paint);
                        canvas.drawBitmap(lvl1.flower.flower,
                                lvl1.flower.x + itemXPosition,
                                lvl1.flower.y,
                                paint);
                        lvl1.mushroom.mushroom = Bitmap.createScaledBitmap(lvl1.mushroom.mushroom,
                                100,
                                100,
                                false);
                        lvl1.flower.flower = Bitmap.createScaledBitmap(lvl1.flower.flower,
                                100,
                                100,
                                false);

                        break;
                }




                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started theb
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            if (bobYPosition < 500) {
                bobYPosition = bobYPosition - (walkSpeedPerSecond / fps);
            }

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:
                        // Set isMoving so Bob is moved in the update method

                        isMoving = true;

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    // Set isMoving so Bob does not move
                    bobYPosition = 1000;
                    update();

                    isMoving = false;

                    break;

                case MotionEvent.ACTION_MOVE:
                    bobYPosition -= 3*(walkSpeedPerSecond / fps);

            }
            return true;
        }

    }
    // This is the end of our GameView inner class

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }
}
