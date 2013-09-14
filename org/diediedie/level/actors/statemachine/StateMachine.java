package org.diediedie.level.actors.statemachine;
import org.diediedie.level.actors.statemachine.State;

public interface StateMachine 
{
	public State getCurrentState();
	public void start();
	public void stop();
	public boolean isRunning();
	public void update();
}
