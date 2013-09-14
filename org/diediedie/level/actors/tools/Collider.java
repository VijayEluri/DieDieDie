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
package org.diediedie.level.actors.tools;
import java.util.ArrayList;
import java.util.List;

import org.diediedie.level.Tile;
import org.diediedie.level.actors.Actor;
import org.diediedie.level.actors.Enemy;
import org.diediedie.level.actors.tools.AnimCreator;
import org.diediedie.level.objects.ArrowBouncer;
import org.diediedie.level.objects.Projectile;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Used to discern whether or not a MovableObject is going to hit
 * something else (i.e. collision, another collidable....)
 */ 
public class Collider
{
    // number of pixels from the player's current position to check a
    // tile for possible collision
    public static final int TILE_COLLISION_CHECK_DIST = 20;
    
    public static boolean collidesLevel(Actor m)
    {
    	List<Tile> tilesHit = getTileCollisions(
    	       m.getCollisionBox(),  
    	       m.getLevel().getCollisionLayer().getTiles());
    	
    	if(!tilesHit.isEmpty())
        {
            return true;
        }
        return false;
    }
    
    /*
     * Return all Tiles that a Rectangle is intersecting.
     */
    public static List<Tile> getTileCollisions(Rectangle rect, List<Tile> tiles) 
    {
        List<Tile> colls = new ArrayList<Tile>();
        
        for(Tile t : tiles)
        {
            boolean closeX = false;
            boolean closeY = false;
            
            if(t.yPos < rect.getY())
            {
                // tile start is above the actor
                if(rect.getY() - t.endY <= TILE_COLLISION_CHECK_DIST)
                {
                    closeY = true;
                }
            }
            else if(t.yPos > rect.getY())
            {
                // tile start is below the actor
                if(t.yPos - rect.getMaxY() <= TILE_COLLISION_CHECK_DIST)
                {
                    closeY = true;
                }
            }
            else
            {
                // same vertical position
                closeY = true;
            }
            if(t.xPos < rect.getX())
            {
                // tile start is to the left of the actor
                if(rect.getX() - t.endX <= TILE_COLLISION_CHECK_DIST)
                {
                    closeX = true;
                }
            }
            else if(t.xPos > rect.getX())
            {
                // tile start is to the right of the actor
                if(t.xPos - rect.getMaxX() <= TILE_COLLISION_CHECK_DIST)
                {
                    closeX = true;
                }
            }
            else
            {
                // same horizontal position
                closeX = true;
            }
            if(closeX && closeY)
            {
                if(rect.intersects(t.getRect()))
                {
                    colls.add(t);
                }
            }
        }
        return colls;
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
	            p.stop();
	            return true;
	        }
        	// Check for arrow bouncer 
        	ArrowBouncer ab = p.getLevel().collidesBouncer(p);
        	/*
        	 * this collision should be refactored as some levels
        	 * might not have bouncers...
        	 */
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
