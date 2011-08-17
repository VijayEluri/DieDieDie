package org.diediedie.actors.statemachine;

public interface State
{
	public StateType getType();
	public void update();
}
