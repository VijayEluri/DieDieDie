package org.diediedie.actors;

import org.diediedie.actors.tools.Direction;

/**
 * Super-interface for all movable LevelObjects.
 */
public interface MovableObject extends LevelObject
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
