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
package diediedie.actors.actions;

import diediedie.actors.actions.Action;
import diediedie.actors.Actor;
import pulpcore.image.CoreGraphics;

public class StopWalking implements Action
{  
    private boolean started, finished;
    private Actor host = null;
    
    /**
     * Create a new StopWalking Action
     */ 
    public StopWalking()
    {
        reset();
    }
    
    @Override
    public void draw(CoreGraphics g){} 
    
    public void reset()
    {
        started = false;
        finished = false;
    }  
    @Override
    public void perform(Actor e)
    {
        if(!started && !finished)
        {
            started = true;
            
            CoreSystem.print("\tperforming StopWalking for " 
                + new Throwable().fillInStackTrace()
                                .getStackTrace()[1].getClassName());
            host.setMoving(false);
            
            // this action doesn't require that any further steps be taken
            finished = true;
        }
        
    }
    
    @Override
    public void update(Actor host)
    {
        // this action doesn't require that any further steps be taken
    }
    
    @Override
    public boolean hasFinished() 
    {
        return started;
    }
    
    @Override
    public boolean hasStarted() 
    {
        return started;
    }
    
    @Override
    public String toString()
    {
        return "StopWalking";
    }
}
