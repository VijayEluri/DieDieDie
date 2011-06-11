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
package diediedie.level.actions;
import diediedie.level.actions.Action;
import diediedie.level.actors.Actor;
import diediedie.level.actors.Enemy;
import diediedie.level.LevelObject;
import diediedie.level.MovableObject;
import diediedie.util.Mover;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;


/**
 * Causes a movable object to *start* moving in its current 
 * direction.
 */ 
public class StartMoving implements Action
{   
    private boolean started, finished;
    private LevelObject host = null;
    
    /**
     * Walk!
     */ 
    public StartMoving()
    {
         reset();
    }
    
    public void reset()
    {
        started = false;
        finished = false;
    }  
    
    
    public void setHost(LevelObject lo)
    {
        host = lo;
    }
    
    
    public void perform()
    {
        if(!started && !finished)
        {
            started = true;
            
            CoreSystem.print("\tperforming StartMoving for " 
                + new Throwable().fillInStackTrace()
                                 .getStackTrace()[1].getClassName());
            
            if(host.getClass() == Enemy.class)
            {
                Enemy e = (Enemy)host;
                e.setMoving(true);
                e.setMoveSpeed(e.getWalkSpeed());
            }
            else 
            {
                throw new IllegalArgumentException(
                    "Unsupported type");
            }
            finished = true;
        }
    }
    
    
    public void update()
    {
        CoreSystem.print(host + "StartMoving >> update...");
    }
    
    
    public void draw(CoreGraphics g)
    {
        // boiler plate :(
    }
    
    
    public boolean hasFinished() 
    {
        return started;
    }
    
    
    public boolean hasStarted() 
    {
        return started;
    }
    
    
    public String toString()
    {
        return "StartMoving";
    }
}
