package org.diediedie.actors;

public interface MovableObject extends LevelObject
{
    void setX(int x);
    void setY(int y);   
    void setYSpeed(float y);
    void setXSpeed(float x);
    float getYSpeed();
    float getXSpeed();
    float getMaxFallSpeed();
    void resetAccelX();
    void resetAccelY();
}
