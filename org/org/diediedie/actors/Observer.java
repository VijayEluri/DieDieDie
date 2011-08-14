package org.diediedie.actors;

import java.util.List;

import org.diediedie.actors.tools.Direction;


/*
 * Defines the special methods for all non-player LevelObjects that use
 * Look Actions.
 */ 
public interface Observer extends LevelObject
{
    /* 
     * Returns the time in seconds that this Observer should spend
     * performing their Look Action. Note this may depend on the value of
     * their current State. 
     */
    int getLookSeconds();

	float getViewSize();

	float getEyePosX();
	
	float getEyePosY();
	
	Direction getFacing();
	
	void setCanSeenPlayer(boolean b);

	void setTimeLastSawPlayer(long time);

	void addVisibleObject(LevelObject lo);

	void setSeenPlayerEvidence(boolean b);
	
}
