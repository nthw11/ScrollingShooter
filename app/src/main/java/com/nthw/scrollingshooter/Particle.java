package com.nthw.scrollingshooter;

import android.graphics.Point;
import android.graphics.PointF;

class Particle {

    PointF mVelocity;
    PointF mPosition;

    Particle(PointF direction){
        mVelocity = new PointF();
        mPosition = new PointF();

        // determine the direction
        mVelocity.x = direction.x;
        mVelocity.y = direction.y;
    }

    void update(){
        // move the particle
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
    }

    void setPosition(PointF position){
        mPosition.x = position.x;
        mPosition.y = position.y;
    }

    PointF getPosition(){
        return mPosition;
    }
}
