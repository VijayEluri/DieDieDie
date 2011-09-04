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
 

package org.diediedie.actors.tools;

import org.diediedie.ArrowBouncer;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Projectile;
import org.newdawn.slick.geom.Shape;

/**
 * Utility class for aligning an Actor against a Tile following a
 * collision
 */ 
public class Aligner
{
    public static final float INCR = 1;//0.02f;//0.01f;
    
    /**
     * Aligns a collided Actor adjacently colliding part of the Level.
     */ /*
    public static void alignToObstacle(Actor m)
    {
        while(!Collider.collidesLevel(m))
        {
            // here 'canJump' is used to discern the direction of the
            // collision; i.e. a 'true' value indicates (hopefully) 
            // that the Actor fell rather than flew...
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
    }    */


	public static void alignToBouncer(Projectile p, ArrowBouncer b) 
	{
		final Shape rect  = b.getRect();
		final int i = 5;
		final float oldX = p.getX();
		final float oldY = p.getY();

		while(rect.contains(p.getX(), p.getY()))
		{
			if(b.getDirection() == Direction.RIGHT)
			{	
				p.setX(p.getX() + i);
			}
			else 
			{
				p.setX(p.getX() - i);
			}
			p.calculateEndPos();
		}
	
		System.out.println("alignToBouncer : " 
			+ oldX + ", " + oldY + " to "
			+ p.getX() + ", " + p.getY());
	}
}
