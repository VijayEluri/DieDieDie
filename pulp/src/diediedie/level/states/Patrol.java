/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */
package diediedie.level.states;

import diediedie.level.Level;
import diediedie.level.states.State;
import diediedie.level.actions.Action;
import diediedie.level.actions.Look;
import diediedie.level.actions.StartMoving;
import diediedie.level.actions.StopMoving;
import diediedie.level.actors.Actor;
import pulpcore.CoreSystem;
import java.lang.Class;

/**
 * State class wherein an Actor(Actor) moves around the Level on
 * a repetitive set of paths, until interrupted due to seeing something 
 * interesting :)
 */ 
public class Patrol implements State
{
    private boolean running = false;
    private Actor host = null;
    private Level level = null;
    
    private Action currentAction;
    private Look look;
    private StartMoving startMoving;
    private StopMoving  stopMoving;
    private State nextState = null;
    
    /**
     * Associate this State with an Actor
     */ 
    public Patrol(Actor e)
    {
        host = e;
        startMoving = new StartMoving();
        stopMoving = new StopMoving();
        CoreSystem.print(this + "not creating Look");
        //look = new Look();
        currentAction = look;
    }  
    
    
    public Actor getHost()
    {
        return host;
    }
    
    
    public Action getCurrentAction()
    {
        return currentAction;
    }

    
    public State getNextState()
    {
        return nextState;
    }

     
    public void enter()
    {
        if(!running)
        {
            running = true;
            CoreSystem.print("\tstarted " + this);
            currentAction.perform();
        }
    }
    
    /**
     * Update / change the current Action.
     */ 
    
    public void update()
    {
        final Class cls = currentAction.getClass();
        //CoreSystem.print("Patrol.update(): currentAction: " currentAction);
        
        // NOT STARTED *OR* FINISHED
        if(!currentAction.hasStarted())
        {
            currentAction.perform();
            CoreSystem.print("Patrol.update(): started " + currentAction);
        }
        // STARTED BUT *NOT* FINISHED
        else if(!currentAction.hasFinished())
        {
            // update all started but non-finished actions
            currentAction.update();
        }
        // STARTED *AND* FINISHED
        else if(currentAction.hasFinished())
        {
            if(cls.equals(startMoving.getClass()))
            {
                CoreSystem.print("Patrol.update(): changing state");   
                currentAction = new Look();    
            }
        }
    }
    
    
    
    
    public Class getCurrentActionType()
    {
        return currentAction.getClass();
    }
    
    
    public void exit()
    {
        running = false;
    }
    
    
    public boolean isRunning()
    {
        return running;
    }
    
    
    public String toString()
    {
        return "Patrol";
    }
}
