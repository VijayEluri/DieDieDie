package org.diediedie.level.actors.statemachine;

public interface State
{
	public void update();
	StateType getType();
}