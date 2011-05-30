package org.diediedie.actors;

public interface MovableObject extends LevelObject
{
    void setX(float x);
    void setY(float y);   
    void setYSpeed(float y);
    void setXSpeed(float x);
    float getYSpeed();
    float getXSpeed();
    float getMaxFallSpeed();
    void resetAccelX();
    void resetAccelY();
}
