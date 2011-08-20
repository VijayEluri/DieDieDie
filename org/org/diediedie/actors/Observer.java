package org.diediedie.actors;

import java.util.Set;

import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.geom.Shape;


/*
 * Defines the special methods for all LevelObjects (including Enemies and other 
 * Actors) that use Look Actions.
 */ 
public interface Observer extends LevelObject
{
    /* 
     * getLookSeconds - Returns the time in seconds that this Observer should spend
     * performing their Look Action. Note this may depend on their current State. 
     */
	Set<LevelObject> getVisibleObjects();
    float getLookSeconds();
	float getViewSize();
	float getEyePosX();
	float getEyePosY();
	Direction getFacing();
	void setCanSeePlayer(boolean b);
	void setTimeLastSawPlayer(long time);
	void setAsVisibleObject(LevelObject lo);
	boolean canSeePlayer();
    boolean hasSeenPlayer();
    void setLocationLastSeenPlayer(Shape sh);
    Shape getLocationLastSeenPlayer();
    boolean hasSeenPlayerEvidence();
    void setSeenPlayerEvidence(boolean b);
    void setHasSeenPlayer(boolean b);
    long getTimeLastSawPlayer();
}
