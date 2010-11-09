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
import org.diediedie.actors.Direction;
import org.diediedie.actors.MovableObject;
import org.diediedie.actors.AnimCreator;

/**
 * Utility class for aligning an Actor against a Tile following a
 * collision
 */ 
public class Aligner
{
    public static final float INCR = 0.01f;
    
    /**
     * Aligns a collided Actor to a non-colliding part of the Level.
     */ 
    public static void alignToObstacle(Actor m)
    {
        while(!Collider.collidesLevel(m))
        {
            // here 'canJump' is used to discern the direction of the
            // collision; i.e. a 'true' value indicates (hopefully) 
            // that the m *fell* into this collision rather than
            // headbutted it... 
            
            
            if(m.canJump())
            {
                m.setY(m.getY() + INCR);
            }
            else
            {
                m.setY(m.getY() - INCR);
            }
        }
        if(m.canJump())
        {   
            m.setY(m.getY() - INCR);
        }
        else
        {
            m.setY(m.getY() + INCR);
        }
    }    
    
}
