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

import org.diediedie.actors.Actor;
import org.diediedie.actors.Collider;


/**
 * Class used to move Actors around a Level. 
 */ 
public class Mover
{    
    public static void move(Actor a)
    {
        //System.out.println("moving " + a);
        final float oldX = a.getX();
        final float oldY = a.getY();
        
        // test new position
        // vertical 
        
        a.setY(a.getY() + a.getYSpeed());
        
        if(Collider.collides(a))
        {            
            if(a.getY() >= oldY)
            {
                // fell to earth
                a.setJump(true);
            }
            a.setYSpeed(0);
            a.setY(oldY);
            
            Aligner.alignToObstacle(a);
        } 
        else
        {
            a.setJump(false);
        }
        
        // horizontal 
        a.setX(a.getX() + a.getXSpeed());
        
        if(Collider.collides(a))
        {
            a.setX(oldX);
            a.setXSpeed(0);
            a.resetAccelX();
        }       
    }
}
