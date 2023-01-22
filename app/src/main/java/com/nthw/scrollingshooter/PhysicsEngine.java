package com.nthw.scrollingshooter;

import java.util.ArrayList;

class PhysicsEngine {

    // this signature will change later
    boolean update(long fps, ArrayList<GameObject> objects, GameState gs, SoundEngine se, ParticleSystem ps){
        // update all the GameObjects
        for(GameObject object : objects){
            if(object.checkActive()){
                object.update(fps, objects.get(Level.PLAYER_INDEX).getTransform());
            }
        }
        if(ps.mIsRunning){
            ps.update(fps);
        }
        return false;
    }

    // collision detection method will go here
}
