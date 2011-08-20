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


import java.util.Date;
import java.util.List;

import org.diediedie.actors.Actor;
import org.diediedie.actors.Bluey;
import org.diediedie.actors.actions.Action;
import org.diediedie.actors.actions.Look;
import org.diediedie.actors.actions.StartMoving;
import org.diediedie.actors.actions.StopMoving;
import org.diediedie.actors.actions.TurnAround;
import org.diediedie.actors.tools.Direction;
import org.diediedie.actors.tools.GroundChecker;

/**
 * Handles the State and Actions for Bluey Enemies.
 */
public class BlueyFSM implements StateMachine
{
	private Bluey host;
	State patrol ;
	private State currentState, alert, combat;
	private boolean running;
	private long lastInfoCallTime;
	
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
		alert = new Alert();
		combat = new Combat();
		currentState = patrol;
		start();
	}
	
	public State getCurrentState()
	{
		return currentState;
	}

	@Override
	public void start()
	{
		running = true;
	}

	@Override
	public void stop() 
	{
		running = false;
	}

	@Override
	public boolean isRunning() 
	{
		return running;
	}
	
	private void printStateInfo(int milliseconds)
	{
		Date date = new Date();
		long now = date.getTime();

		if((now - lastInfoCallTime) >= milliseconds)
		{
			System.out.println(
				"\nBlueyFSM ::\n\tcurrent State " + currentState.getType());
			lastInfoCallTime = now;
		}
	}
	
	@Override
	public void update()
	{
		//printStateInfo(1500);
		/*
		 *  Query the Bluey instance and see if we need to change the
		 *  current State. What we look for in the instance will depend
		 *  on the current State.
		 */
		if(currentState.getType() == StateType.PATROL)
		{
			/*
			 * Patrol has one future State, Alert. In order to change to
			 * that State, we must work out if the host has 
			 * 
			 * a) seen the Player 
			 * 
			 * or
			 * 
			 * b) has seen Player's evidence. This equates to seeing 
			 * something that the Player has left (e.g. an arrow, a dead
			 * enemy...)
			 * 
			 * or
			 * 
			 * c) collides with the Player or is hit by the Player's weaponry.
			 */			
			if(host.hasSeenPlayer()
					||
			   host.hasSeenPlayerEvidence())
			{
				System.out.println(
					host + 	" changing current State PATROL to ALERT");
				currentState = alert;
			}
		}
		else if(currentState.getType() == StateType.ALERT)
		{
			/*
			 * Once out of PATROL State, the host will switch between
			 * ALERT and COMBAT States, depending on the proximity of the
			 * Player. The host never returns to PATROL State.
			 */
		}
		else if(currentState.getType() == StateType.COMBAT)
		{
			/*
			 * Stays in COMBAT State until X milliseconds have passed in
			 * which the Player is not visible, or has not attacked the
			 * host.
			 */
		}
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
		               currentAction;
        private Look look;
        private StartMoving startMoving;
        private StopMoving stopMoving;
		private TurnAround turnAround;
		
		/*
		 * Patrol has a slow walk speed
		 */
		private final float PATROL_SPEED = 1f;
		
		/*
		 * Initial State for a Bluey. As soon as the Player has been seen 
	     * or Player's items are seen, the host moves to the Alert State.
	     */ 
		public Patrol()
		{
			look = new Look(host);
			startMoving = new StartMoving();
			stopMoving = new StopMoving();
			
			/*
			 * Set the host's movement speed to Patrol speed.
			 */
			host.setMoveSpeed(PATROL_SPEED);
		}

		/*
		 * Updates the Patrol State. 
		 * 
		 * The current State is dictated by StateMachine.update().
		 * 
		 * No attempt is made to check that the State should be changed; 
		 * instead we assume the Player is invisible to the host and continue as 
		 * normal. 
		 * 
		 * @see org.diediedie.actors.statemachine.State#update()
		 */
		@Override
		public void update() 
		{
			// *Patrol* State
			if(host.canJump())
			{
				/* 
				 * If canJump(), host is on a walkable surface [assuming the 
				 * absence of bugs]
				 * 
				 * We are now in a position to query the horizontal speed of the 
				 * host (and so check to see if he
				 * has started moving)
				 */
				if(host.getXSpeed() != 0)
				{
					/*
					 * Host is moving on a walkable area
					 * 
					 * Check whether or not we should stop (i.e. if there is
					 * a sudden drop or obstacle ahead).
					 */
					Direction facing = host.getFacing();
					boolean cont = GroundChecker.canContinueMoving(
							host, facing);
					//System.out.println(
						//"\t--> canContinueMoving returned " + cont);
					if(cont == false)
					{
						/*
						 * We can't go this way; turn around!
						 */
						System.out.println("BlueyFSM :: Patrol");
						System.out.println("\tTurning around!");
						
						turnAround = new TurnAround();
						turnAround.setHost(host);
						currentAction = turnAround;
					}
					else
					{
						// Keep going in this Direction until we have a
						// reason to change. I can't think of one in this State
						// (yet...)
						currentAction = null;
					}
				}
				else
				{
					/*
					 * Host is stationary on a walkable area
					 * 
					 * Start moving in the current Direction.
					 */
					startMoving.reset();
					startMoving.setHost(host);
					startMoving.setDirection(host.getFacing());
					currentAction = startMoving;
				}
			}
			else
			{
				// Falling. Presumably we've taken this into account and
				// the host isn't about to fall to their death...
				System.out.println("BlueyFSM: " + host + " is falling!");
				currentAction = null;
			}
			
			/*
			 * Finally, check to see if we have an Action to perform, and 
			 * perform it.
			 */
			if(currentAction != null)
			{
				currentAction.perform();
			}
		}

		@Override
		public StateType getType() 
		{
			return StateType.PATROL;
		}
	}
	class Alert implements State 
	{
		/*
		 * Hunts down the Player -- knows of his existence but not his
		 * location. Seeing Projectiles fired by the Player will make him
		 * either enter Combat State and defend, or if the Projectiles 
		 * are not very close, move in the direction from whence
		 * they came.
		 */
		@Override
		public void update() 
		{
			
			
		}

		@Override
		public StateType getType() 
		{
			// TODO Auto-generated method stub
			return StateType.ALERT;
		}

	}
	
	class Combat implements State 
	{
		@Override
		public void update() 
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public StateType getType() 
		{
			// TODO Auto-generated method stub
			return StateType.ALERT;
		}
	}
}
