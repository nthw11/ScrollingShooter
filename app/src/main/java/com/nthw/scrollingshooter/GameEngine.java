package com.nthw.scrollingshooter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

class GameEngine extends SurfaceView implements Runnable, GameStarter{
    private Thread mThread = null;
    private long mFPS;

    private GameState mGameState;

    public GameEngine(Context context, Point size){
        super(context);

        mGameState = new GameState(this, context);
    }

    @Override
    public void run(){
        while(mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            if(!mGameState.getPaused()){
                // Update all the game objects here
                // in a new way
            }
        }

        // draw all the game objects here
        // in a new way

        // measure the frames per second in the usual way
        long timeThisFrame = System.currentTimeMillis() - frameStartTime;
        if(timeThisFrame >= 1){
            final int MILLIS_IN_SECOND = 1000;
            mFPS = MILLIS_IN_SECOND / timeThisFrame;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        // handle the player's input here
        // but in a new way

        return true;
    }

    public void stopThread(){
        // new code here soon
        mGameState.stopEverything();

        try{
            mThread.join();
        } catch (InterruptedException e){
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread(){
        // new code here soon
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();
    }

    public void deSpawnReSpawn(){
        // eventually this will despawn and then respawn all the game objects
    }
}
