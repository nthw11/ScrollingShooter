package com.nthw.scrollingshooter;

import android.graphics.PointF;

class LaserMovementComponent implements MovementComponent{
    @Override
    public boolean move(long fps, Transform t, Transform playerTransform){
        // laser can only travel two screen widths
        float range = t.getmScreenSize().x * 2;
        // where is the laser
        PointF location = t.getLocation();
        // how fast is it going
        float speed = t.getSpeed();
        if(t.headingLeft()){
            location.x += speed / fps;
        } else if(t.headingLeft()){
            location.x -= speed / fps;
        }
        // has the laser gone out of range
        if(location.x < - range || location.x > range){
            // disable the laser
            return false;
        }
        t.updateCollider();
        return true;
    }
}
