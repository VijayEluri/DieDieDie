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
import org.newdawn.slick.util.FastTrig;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Collider;


/**
 * Class used to move Actors around a Level. 
 */ 
public class Mover
{    
    static final int ITER = 6;
    /**
     * Attempts to move the Actor, a, according to its x / y speeds.
     * 
     * Returns true if moved *horizontally* since last position.
     */ 
    public static boolean move(final Actor a)
    {
        //System.out.println("moving " + a);
        final float oldX = a.getX();
        final float oldY = a.getY();
        
        // test new position
        // vertical 
        a.setY(a.getY() + a.getYSpeed());
        
        if(Collider.collidesLevel(a))
        {            
            if(a.getY() >= oldY)
            {
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
        
        if(Collider.collidesLevel(a))
        {
            a.setX(oldX);
            a.setXSpeed(0);
            a.resetAccelX();
        }       
        if(a.getX() == oldX)
        {
         /*   System.out.println("Mover.move(): " + a + "\n\t" + 
                                "did not move horizontally"); */
            return false;
        }
        return true;
    }
    
    /**
     * For moving Projectiles. :)
     */ 
    public static void move(Projectile p)
    { 
        if(!p.isFlying())
        {
            return;
        }
        
        // work out the distance to travel in this update assuming no
        // collisions 
        final float xTrav = p.getXSpeed() * p.getAirRes();
        final float yTrav = p.getYSpeed();
        
        /*float xMoved = 0f;
        float yMoved = 0f;*/
        
        p.adjustFacingAngle();
        
        
        // spit the travelling in half, testing for collision each time        
        
        for(int i = 0; i < ITER && p.isFlying(); ++i)
        {
            doMove(p, xTrav/ITER, yTrav/ITER);
        }
    }
    
    private static boolean doMove(Projectile p, float xTrav, float yTrav)
    {
        p.setX((float)(p.getX() + xTrav * 
                            FastTrig.sin(Math.toRadians(p.getAngle()))));
        p.setY((float)(p.getY() - yTrav * 
                            FastTrig.cos(Math.toRadians(p.getAngle()))));
        p.calculateEndPos();
        if(Collider.collidesLevel(p))
        {
            p.stop();
            return false;
        }
        return true;
    }
}
