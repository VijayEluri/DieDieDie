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
package diediedie.level.actors; 
import diediedie.level.actors.Actor;
import diediedie.level.Point;
import pulpcore.image.CoreGraphics;

/**
 * Pathfinding construction for Actor AI.
 */
public class PathConstructor
{
    private boolean started, finished;
    private Point dest;
    
    public PathConstructor(Actor e, float xDest, float yDest)
    {
        this(e, new Point(xDest, yDest));
    }
    public PathConstructor(Actor e, Point dest)
    {
        reset();
    }
    
    public void perform(Actor e)
    {
        CoreSystem.print("ConstructPath: perform");
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }
    
    public void update(Actor e)
    {
        
    }
        
    public void draw(CoreGraphics g)
    {
        
    }
    
    public void reset()
    {
        dest = null;
        started = false;
        finished = false;
    }
}
