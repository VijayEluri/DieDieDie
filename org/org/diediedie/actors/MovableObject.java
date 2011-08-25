package org.diediedie.actors;

import org.newdawn.slick.util.pathfinding.Mover;


/**
 * Super-interface for all movable LevelObjects.
 */
public interface MovableObject extends LevelObject, Mover
{
    float getMoveSpeed();    
	float getXSpeed();
	float getYSpeed();
    void setX(float x);
    void setY(float y);   
    void setYSpeed(float y);
    void setXSpeed(float x);
    float getMaxFallSpeed();
    void resetAccelX();
    void resetAccelY();
}
