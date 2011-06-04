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
  * State entered by an Enemy when they have noticed the Player.
  */ 
public class Alert implements State
{
    private Action currentAction = null;
    private boolean running = false;
    private Enemy host = null;
    private Level level = null;
    private State nextState = null;
    
    /**
     * Associate this State with an Actor
     */ 
    public Alert(Enemy e)
    {
        host = e;
    }
    
    @Override
    public State getNextState()
    {
        return nextState;
    }
    
    
    @Override
    public Enemy getHost()
    {
        return host;
    }
    
    @Override
    public String toString()
    {
        return "Alert";        
    }
    
    @Override
    public void update()
    {
        
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
    public Class getCurrentActionType()
    {
        return currentAction.getClass();
    }
    
    @Override
    public Action getCurrentAction()
    {
        return currentAction;
    }
}
