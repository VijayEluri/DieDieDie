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
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;
import diediedie.level.LevelObject;
import diediedie.level.actors.Actor;
import diediedie.level.actors.Enemy;

public class StopMoving implements Action
{  
    private boolean started, finished;
    private LevelObject host = null;
    
    /**
     * Create a new StartMoving Action
     */ 
    public StopMoving()
    {
        reset();
    }
    
    @Override
    public void setHost(LevelObject lo)
    {
        host = lo;
    }
    
    @Override
    public void draw(CoreGraphics g){} 
    
    public void reset()
    {
        started = false;
        finished = false;
    }  
    @Override
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
                e.setMoveSpeed(e.getWalkSpeed());
                e.setMoving(false);
            }
            else 
            {
                throw new IllegalArgumentException(
                    "Unsupported type");
            }
            
            
            // this action doesn't require that any further steps be taken
            finished = true;
        }
        
    }
    
    @Override
    public void update()
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
        return "StartMoving";
    }
}
