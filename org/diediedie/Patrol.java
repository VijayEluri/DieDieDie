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
  * State class wherein an Enemy(Actor) moves around the Level,
  * looking for the Player.
  */ 
public class Patrol implements State
{
    private Enemy host = null;
    private Level level = null;
    private boolean running = false;
    
    private Action currentAction;
    private StartWalking startWalking;
    
    
    
    /**
     * Associate this State with an Actor
     */ 
    public Patrol(Enemy e)
    {
        host = e;
        startWalking = new StartWalking();
        currentAction = startWalking;
    }  
    
    @Override
    public Enemy getHost()
    {
        return host;
    }
    
    public Action getCurrentAction()
    {
        return currentAction;
    }

    @Override
    public void enter()
    {
        if(!running)
        {
            running = true;
            System.out.println("\tstarted " + this);
            currentAction.perform(host);
        }
    }
    
    /**
     * Define the procedure for updating the Patrolling state based upon 
     * the currentAction. 
     */ 
    @Override
    public void update()
    {
        //System.out.println("Patrol.update(): currentAction " + currentAction);
        if(!currentAction.hasStarted())
        {
            currentAction.perform(host);
            System.out.println("Patrol.update(): started " + currentAction);
        }
        else if(currentAction.hasFinished())
        {
            final Class cls = currentAction.getClass();
            if(cls.equals(startWalking.getClass()))
            {
                //System.out.println("Patrol.update(): changing state");   
                currentAction = new Look();    
            }
        }
        else
        {
            currentAction.update(host);    
        }
    }
    
    
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
