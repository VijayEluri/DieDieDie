package org.diediedie.actors;

import java.util.Set;

import org.diediedie.actors.tools.Direction;


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
	void setCanSeenPlayer(boolean b);
	void setTimeLastSawPlayer(long time);
	void addVisibleObject(LevelObject lo);
	boolean canSeePlayer();
    boolean hasSeenPlayer();
    boolean hasSeenPlayerEvidence();
    void setSeenPlayerEvidence(boolean b);
    void setHasSeenPlayer(boolean b);
    long getTimeLastSawPlayer();
}
