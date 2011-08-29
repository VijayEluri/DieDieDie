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
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Projectile;
import org.newdawn.slick.geom.Shape;

/**
 * Used to discern whether or not a MovableObject is going to hit
 * something else (i.e. collision, another collidable....)
 */ 
public class Collider
{
    public static boolean collidesLevel(Actor m)
    {
        if(m.getLevel().collides(
           AnimCreator.getCurrentFrameRect(m)))
        {
            return true;
        }
        return false;
    }
    
    /*
     * Returns true if the arrow intersects with collision Tile on the
     * Level
     */ 
    public static boolean collidesLevel(Projectile p)
    {
        if(p.isFlying())
        {
        	if(p.getLevel().collides(p.getShape()))
	        {
	        	
	            /*System.out.println(
	            	p + " " + p.hashCode() + " " + "collides Level with speeds :");
	            System.out.println(
	                "\tx change : " + (p.getOldStartX() - p.getX()));
	        
	            System.out.println(
	                "\ty change : " + (p.getOldStartY() - p.getY()));*/
	            p.stop();
	            return true;
	        }
        	// Check for arrow bouncer 
        	ArrowBouncer ab = p.getLevel().collidesBouncer(p);
        	if(ab != null)
        	{
        		System.out.println(
        			"Arrow " + p.hashCode() + " hit bouncer");
        		Aligner.alignToBouncer(p, ab);	
        		p.bounce(ab);
        	}
        }
        return false;
    }
    
    /**
     * Function to get the current collision state of a Projectile.
     * 
     * If Projectile p is currently in a collision with an Enemy,
     * return that Enemy. Otherwise return null; 
     */
    public static Enemy collidesEnemy(Projectile p)
    {
        final Shape s = p.getShape();
        
        for(Enemy e : p.getLevel().getEnemies())
        {
            Shape sh = AnimCreator.getCurrentFrameRect(e);
            
            if(intersection(sh, s))
            {
                if(sh.contains(p.getEndX(), p.getEndY()))
                {
                    System.out.println(
                        p + " hit Enemy " + e + " with arrow tip"); 
                    return e;
                }
                System.out.println(
                    p + " hit Enemy " + e + " but not with the tip");
            }
        }
        return null;
    }
    
    /**
     * Returns true if two Shapes intersect.
     */ 
    public static boolean intersection(Shape p1, Shape p2)
    {
        return p1.intersects(p2);
    }
}
