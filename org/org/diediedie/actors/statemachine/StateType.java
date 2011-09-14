package org.diediedie.actors.statemachine;

public enum StateType
{
	// basic enemies
	PATROL, ALERT, COMBAT, 
	
	// any simple, 2-state object (such as a switch)
	IDLE, ACTIVE
}
