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
import java.util.BitSet;
import java.util.List;

import org.diediedie.ArrowBouncer;
import org.diediedie.Tile;
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Projectile;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Used to discern whether or not a MovableObject is going to hit
 * something else (i.e. collision, another collidable....)
 */ 
public class Collider
{
	
    public static boolean collidesLevel(Actor m)
    {
    	final Shape actorRect = AnimCreator.getCurrentFrameRect(m);
    	List<Tile> tilesHit = m.getLevel().getTileCollisions(actorRect);
    	
    	if(!tilesHit.isEmpty())
        {
    		/*System.out.println(
    			"Actor hit " 
    			+ tilesHit.size() + " tiles");
    		*/
    		// create a shape of just the intersected area to check
    		// the actor's current image's collide mask against.
    		Tile firstTile = tilesHit.get(0);
    		Tile lastTile = tilesHit.get(tilesHit.size() - 1);
    		
    		final float rectX, rectY, rectWidth, rectHeight;
    		
    		/*
    		 * Work out minima / maximum for top-left point of the 
    		 * intersection rectangle.
    		 */
    		if(actorRect.getX() < firstTile.xPos)
    		{
    			/*
    			 * use tile xPos as rect start x : the Actor's xPos is 
    			 * further to the left than the left-most
    			 * tile, 
    			 */
    			rectX = firstTile.xPos;
    		}
    		else
    		{
    			rectX = actorRect.getX();
    		}
    		if(actorRect.getY() < firstTile.yPos)
    		{
    			/*
    			 * actor's yPos is higher than the start tile's, so
    			 * use the tile's y position
    			 */
    			rectY = firstTile.yPos;
    		}
    		else
    		{
    			rectY = actorRect.getY();
    		}
    		
    		/****************************************************************/
    		
    		/*
    		 * Work out width and height    		
    		 */
    		// W
    		if(actorRect.getMaxX() < lastTile.endX)
    		{
    			rectWidth = actorRect.getMaxX() - rectX;
    		}
    		else
    		{
    			rectWidth = lastTile.endX - rectX;
    		}
    		
    		// H
    		if(actorRect.getMaxY() < lastTile.endY)
    		{
    			rectHeight = actorRect.getMaxY() - rectY;
    		}
    		else
    		{
    			rectHeight = lastTile.endY - rectY;
    		}
    		
    		// find the rectangle pixels
    		boolean bits[] = m.getCollideMask().getRectBits(
    					Math.abs((int)rectX - (int)actorRect.getX()),
    					(int)rectWidth,
    					Math.abs((int)rectY - (int)actorRect.getY()),
    					(int)rectHeight);
    		
    		for(int i = 0; i < bits.length; ++ i)
    		{
    			if(bits[i] == true)
    			{
    				
    				return true;
    			}
    		}
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
        		/*System.out.println(
        			"Arrow " + p.hashCode() + " hit bouncer");*/
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
