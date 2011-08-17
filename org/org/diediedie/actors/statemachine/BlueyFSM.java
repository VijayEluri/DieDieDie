package org.diediedie.actors.statemachine;

import org.diediedie.actors.Bluey;

/**
 * Handles the State and Actions for Bluey Enemies.
 * @author sean
 *
 */
public class BlueyFSM implements StateMachine
{
	private Bluey host;

	/* 
	 * States: Idle, Hunting, Attacking
	 * 
	 * Idle - Initial State for a Bluey. As soon as the Player has been seen 
	 * or Player's items are seen, moves to Alert State.
	 * 
	 * Hunting - Has knowledge of Player's existence, has some knowledge
	 * of Player's vague or exact whereabouts (or has just seen an arrow fly
	 * past) [Might need a 'Patrol Area' action?]. 
	 * 
	 * Attacking - Moves into this mode from Hunting when in suitably close
	 * range to the Player. Attempts to kill his ass.
	 * 
	 * States are implemented below. 
	 */
	public BlueyFSM(Bluey host)
	{
		host = host;
		currentState = new BlueyIdle();
	}
	private State currentState;
	
	public State getCurrentState()
	{
		return currentState;
	}

	@Override
	public void start()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
	
	public Bluey getHost()
	{
		return host;
	}
	
	class BlueyIdle implements State
	{

		@Override
		public String getName() 
		{
			return "BlueyIdle";
		}

		@Override
		public void update() 
		{
			// TODO Auto-generated method stub
			
		}
	}
}
