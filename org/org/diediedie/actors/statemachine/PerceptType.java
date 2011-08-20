package org.diediedie.actors.statemachine;

/**
 * An enumeration of Perceptions that Enemies / Actors can have.
 *  
 */
public enum PerceptType
{
	CAN_SEE_PLAYER, 
	CAN_SEE_PLAYER_ITEM,
	HIT_BY_PLAYER,
	INJURED_BY_PLAYER,
	NEARLY_HIT_BY_PLAYER,
	NEARLY_HIT_PLAYER,
	// ...?
}
