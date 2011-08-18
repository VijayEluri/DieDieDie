/*
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

package org.diediedie.actors.statemachine;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.actors.Bluey;
import org.diediedie.actors.actions.Action;
import org.diediedie.actors.actions.Look;
import org.diediedie.actors.actions.StartMoving;

/**
 * Handles the State and Actions for Bluey Enemies.
 */
public class BlueyFSM implements StateMachine
{
	private Bluey host;
	State patrol ;
	private State currentState;
	
	/* 
	 * States: Idle, Alert, Combat
	 * 
	 * Idle - Initial State for a Bluey. As soon as the Player has been seen 
	 * or Player's items are seen, moves to Alert State.
	 * 
	 * --> Action Sequences: 'Patrol' ...
	 * 
	 * Alert - Has knowledge of Player's existence, has some knowledge
	 * of Player's whereabouts (or has just seen an arrow fly past) 
	 * Will begin to hunt down the player
	 * 
	 * --> Action Sequences: 'Hunting', ...
	 * 
	 * Fighting - Moves into this mode from Alert when in suitably close
	 * range to the Player. Attempts to kill his ass.
	 * 
	 * States are implemented below. 
	 */
	public BlueyFSM(Bluey host)
	{
		this.host = host;
		patrol = new Patrol();
		currentState = patrol;
	}
	
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
		// Query the Bluey instance and see if we need to change the
		// current State.
		
		
		// finally, update the current state
		currentState.update();
	}
	
	public Bluey getHost()
	{
		return host;
	}
	
	////////////////////////////////////////////////////////////////////////
	//	         			Bluey State Definitions                       //
	////////////////////////////////////////////////////////////////////////
	class Patrol implements State
	{
		private Action startAction,
		               currentAction,
					   look, 
					   startMoving, 
					   stopMoving; 
		
		/*
		 * Initial State for a Bluey. As soon as the Player has been seen 
	     * or Player's items are seen, the Bluey moves to the Alert State.
	     * 
	     * Idle has (so far) a simple action sequence, called Patrol
		 */
		public Patrol()
		{
			look = new Look(host);
			startMoving = new StartMoving();
			stopMoving = new StopMoving();
			startAction = look;
		}
		
		@Override
		public void update() 
		{
			// Patrolling
			
		}

		@Override
		public StateType stateType() 
		{
			return StateType.PATROL;
		}
	}
}
