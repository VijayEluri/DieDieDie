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
package org.diediedie.actors.actions;
import org.diediedie.actors.actions.Action;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Direction;

/**
 * Look around the immediate area, in the current Direction.
 * 
 * If moving:
 *
 *  -
 * 
 * 
 * If not moving:
 * 
 * -
 * 
 */
public class Look implements Action
{
    private boolean started, finished;
    private float xViewStart, yViewStart;
    private View view;
    /*
     *  Look!
     */
    public Look()
    {
        started = false;
        finished = false;
    }
    
    @Override
    public void perform(Enemy e)
    {
        started = true;
        System.out.println("Look.performAction(): ");
        update(e);
    }
    
    /**
     * Constructs a view from a Enemy's facing direction and position 
     */ 
    
    
    @Override
    public void update(Enemy e)
    {
        
    }
     
    @Override
    public boolean hasStarted() 
    {
        return started;
    }
    
    @Override
    public boolean hasFinished() 
    {
        return finished;
    }
    
    /*
     * View - constructs the geometry describing the field-of-view from
     * the Enemy's eye position to the Level that s/he exists in. 
     * 
     * This View is used to calculate whether or not the Enemy can 
     * see an object, platform or Actor on the Level.  
     */ 
    class View
    {    
        private float xViewStart, yViewStart;
        
        View(Enemy e)
        {
            xViewStart = e.getEyePosX();
            yViewStart = e.getEyePosY();
            
            System.out.println("\n\tconstructView():");
            
            if(e.getFacing().equals(Direction.LEFT))
            {
                System.out.println(e + " left view");
            }
            else if(e.getFacing().equals(Direction.RIGHT))
            {
                System.out.println(e + " right view");
            }
        }       
    }
}
