package org.diediedie.actors.statemachine;

public interface State
{
	public void update();
	StateType getType();
}