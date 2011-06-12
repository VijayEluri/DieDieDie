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
import org.newdawn.slick.geom.Shape;

/**
 * Used to discern whether or not a MovableObject is going to hit
 * something else (i.e. collision, another collidable....)
 */ 
public class Collider
{
    public static boolean collidesLevel(Actor m)
    {
        if(m.getLevel().collides(AnimCreator.getCurrentFrameRect(m)))
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
        /*if(p.getLevel().isInCollisionTile(p.getX(), p.getY()) 
           ||
           p.getLevel().isInCollisionTile(p.getEndX(), p.getEndY()))
        {
            return true;
        }*/
        final Shape s = p.getShape();
        if(p.getLevel().collides(s))
        {
            return true;
        }
        return false;
    }
    
    public static Enemy collidesEnemy(Projectile p)
    {
        final Shape s = p.getShape();
        
        for(Enemy e : p.getLevel().getEnemies())
        {
            Shape sh = AnimCreator.getCurrentFrameRect(e);
            
            if(intersection(sh, s))
            {
                return e;
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
