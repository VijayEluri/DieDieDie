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
package org.diediedie.actors;
import org.diediedie.Level;
import org.diediedie.actors.State;
import org.diediedie.actors.actions.*;
import java.lang.Class;

 /**
  * State class wherein an Enemy(Actor) moves around the Level on
  * a repetitive set of paths, until interrupted due to seeing something 
  * interesting :)
  */ 
public class Patrol implements State
{
    private boolean running = false;
    private Enemy host = null;
    private Level level = null;
    
    private Action currentAction;
    private Look look;
    private StartWalking startWalking;
    private StopWalking  stopWalking;
    private State nextState = null;
    
    /**
     * Associate this State with an Actor
     */ 
    public Patrol(Enemy e)
    {
        host = e;
        startWalking = new StartWalking();
        stopWalking = new StopWalking();
        look = new Look();
        currentAction = look;
    }  
    
    @Override
    public Enemy getHost()
    {
        return host;
    }
    
    @Override
    public Action getCurrentAction()
    {
        return currentAction;
    }

    @Override
    public State getNextState()
    {
        return nextState;
    }

    @Override 
    public void enter()
    {
        if(!running)
        {
            running = true;
            CoreSystem.print("\tstarted " + this);
            currentAction.perform(host);
        }
    }
    
    /**
     * Update / change the current Action.
     */ 
    @Override
    public void update()
    {
        final Class cls = currentAction.getClass();
        //CoreSystem.print("Patrol.update(): currentAction: " currentAction);
        
        // NOT STARTED *OR* FINISHED
        if(!currentAction.hasStarted())
        {
            currentAction.perform(host);
            CoreSystem.print("Patrol.update(): started " + currentAction);
        }
        // STARTED BUT *NOT* FINISHED
        else if(!currentAction.hasFinished())
        {
            // update all started but non-finished actions
            currentAction.update(host);
        }
        // STARTED *AND* FINISHED
        else if(currentAction.hasFinished())
        {
            if(cls.equals(startWalking.getClass()))
            {
                CoreSystem.print("Patrol.update(): changing state");   
                currentAction = new Look();    
            }
        }
    }
    
    
    
    @Override
    public Class getCurrentActionType()
    {
        return currentAction.getClass();
    }
    
    @Override
    public void exit()
    {
        running = false;
    }
    
    @Override
    public boolean isRunning()
    {
        return running;
    }
    
    @Override
    public String toString()
    {
        return "Patrol";
    }
}
