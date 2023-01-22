package com.nthw.scrollingshooter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

class GameEngine extends SurfaceView
        implements Runnable,
        GameStarter,
        GameEngineBroadcaster,
        PlayerLaserSpawner{
    private Thread mThread = null;
    private long mFPS;

    private ArrayList<InputObserver> inputObservers = new ArrayList();

    UIController mUIController;

    private GameState mGameState;
    private SoundEngine mSoundEngine;
    HUD mHUD;
    Renderer mRenderer;
    ParticleSystem mParticleSystem;
    PhysicsEngine mPhysicsEngine;
    Level mLevel;

    public GameEngine(Context context, Point size){
        super(context);

        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mPhysicsEngine = new PhysicsEngine();

        mParticleSystem = new ParticleSystem();
        mParticleSystem.init(1000);
        mLevel = new Level(context, new PointF(size.x, size.y), this);
    }

    // for the game engine broadcaster interface
    public void addObserver(InputObserver o){
        inputObservers.add(o);
    }

    @Override
    public void run(){
        while(mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            ArrayList<GameObject> objects = mLevel.getGameObjects();
            if(!mGameState.getPaused()){
                // Update all the game objects here
                // in a new way

                // this call to update will evolve with the project
                if(mPhysicsEngine.update(mFPS, objects, mGameState, mSoundEngine, mParticleSystem)){
                    // player hit
                    deSpawnReSpawn();
                }
            }

        // draw all the game objects here
        // in a new way
            mRenderer.draw(objects, mGameState, mHUD, mParticleSystem);

        // measure the frames per second in the usual way
        long timeThisFrame = System.currentTimeMillis() - frameStartTime;
        if(timeThisFrame >= 1) {
            final int MILLIS_IN_SECOND = 1000;
            mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }
//    void draw(ArrayList<GameObject> objects, GameState gs, HUD hud, ParticleSystem ps){
//
//    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        // handle the player's input here
        // but in a new way
        for(InputObserver o : inputObservers){
            o.handleInput(motionEvent, mGameState, mHUD.getControls());
        }

        // test a particle system temp code
//        mParticleSystem.emitParticles(new PointF(500, 500));

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
        ArrayList<GameObject> objects = mLevel.getGameObjects();
        mLevel.getGameObjects();
        for(GameObject o : objects){
            o.setInactive();
        }
        objects.get(Level.PLAYER_INDEX)
                .spawn(objects.get(Level.PLAYER_INDEX)
                        .getTransform());

        objects.get(Level.BACKGROUND_INDEX)
                .spawn(objects.get(Level.PLAYER_INDEX)
                        .getTransform());
    }

    @Override
    public boolean spawnPlayerLaser(Transform transform){
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        if(objects.get(Level.mNextPlayerLaser).spawn(transform)){
            Level.mNextPlayerLaser++;
            mSoundEngine.playShoot();
            if(Level.mNextPlayerLaser == Level.LAST_PLAYER_LASER +1){
                // just used the last laser
                Level.mNextPlayerLaser = Level.FIRST_PLAYER_LASER;
            }
        }
        return true;
    }
}
