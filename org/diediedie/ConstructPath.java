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
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Point;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.util.pathfinding.*;

/**
 * 
 */
public class ConstructPath implements Action
{
    private boolean started, finished;
    private Point dest;
    
    public ConstructPath(Enemy e, float xDest, float yDest)
    {
        this(e, new Point(xDest, yDest));
    }
    public ConstructPath(Enemy e, Point dest)
    {
        reset();
    }
    
    public void perform(Enemy e)
    {
        System.out.println("ConstructPath: perform");
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }
    
    public void update(Enemy e)
    {
        
    }
        
    public void draw(Graphics g)
    {
        
    }
    
    public void reset()
    {
        dest = null;
        started = false;
        finished = false;
    }
}
