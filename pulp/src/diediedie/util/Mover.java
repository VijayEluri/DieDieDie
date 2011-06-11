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

package diediedie.util;

import java.lang.Math;
import diediedie.level.actors.Actor;
import diediedie.level.MovableObject;
import diediedie.level.Projectile;
import diediedie.util.Collider;
import diediedie.util.Aligner;
import diediedie.level.NavMesh;

/**
 * Class used to Actors and Projectiles around a Level. 
 */ 
public class Mover
{    
    static final int INTERVAL = 10;
    
    /**
     * Set an Actor moving 
     */
    /*
        public static void setMoving(Actor a, boolean b)
        {
            a.setMoving(b);
        }
    */
    
    /**
     * Attempts to move the Actor, a, according to its x / y speeds.
     * 
     * Returns true if moved *horizontally* since last position.
     */ 
    public static boolean move(final Actor a)
    {
        //CoreSystem.print("moving " + a);
/*
        final float oldX = a.getX();
        final float oldY = a.getY();
*/
        final int oldX = a.getX();
        final int oldY = a.getY();
        
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
        System.out.println(a);
        a.setX(a.getX() + a.getXSpeed());
        
        if(Collider.collidesLevel(a))
        {
            a.setX(oldX);
            a.setXSpeed(0);
            a.resetAccelX();
        }       
        if(a.getX() == oldX)
        {
            return false;
        }
        return true;
    }
    
    public static void applyGravity(Actor m)
    {
        if(m.getYSpeed() < m.getMaxFallSpeed())
        {
            m.setYSpeed(m.getYSpeed() + m.getLevel().gravity);  
        }        
    }
    
    /*
     * Add gravity to Projectiles. This works differently to applying
     * g to Actors due to dealing with angles (i.e. vectors) more 
     */ 
    public static void applyGravity(Projectile p)
    {
        if(!p.isFlying())
        {
            return;
        }
        
        // calc out how much gravity we should try to apply
        final float MOST = p.getGravity();        
        float yTrav = MOST;
        p.setY(p.getY() + yTrav);
        p.calculateEndPos();
        
        if(Collider.collidesLevel(p))
        {
            p.stop();
            while(Collider.collidesLevel(p))
            {
                //final float oldY = p.getY();
                p.setY(p.getY() - Aligner.INCR);
                p.calculateEndPos();
            }
        }
        //CoreSystem.print("applyGravity(p), yTrav: " + yTrav);
        p.increaseGravityEffect(); 
    }
    
    /**
     * For publically moving Projectiles. :)
     */ 
    public static void move(Projectile p)
    {   
        float xTrav = p.getXSpeed() * p.getAirRes();
        float yTrav = p.getYSpeed();
        
        if(xTrav <= 0 && yTrav <= 0)
        {
            return;
        }    
        
        if(!doMove(p, xTrav, yTrav, p.getAngle()))
        {
            float reverse = p.getAngle() + 180;
            float xMove = xTrav / INTERVAL; 
            float yMove = xTrav / INTERVAL;
            while(!doMove(p, xMove, yMove, reverse));
        }
        
        /*final float oldX = p.getX();
        final float oldY = p.getY();

        float xMove = xTrav / INTERVAL;
        float yMove = yTrav / INTERVAL;
        
        int i = 0;
        
        while(i < INTERVAL && doMove(p, xMove, yMove, p.getAngle()))
        {
            ++i;
        }*/
        
    }
    
    
    /*
     * Returns the new horizontal position of p after hypothetically 
     * applying horizontal distance xTrav based upon p's getAngle().
     */ 
    public static float getNewXPos(Projectile p, float xTrav, float angle)
    {
        return p.getX() + xTrav * (float)FastTrig.sin(
                            Math.toRadians(angle));
    }
    
    /*
     * Returns the new vertical position of p after hypothetically 
     * applying vertical distance yTrav based upon p's getAngle().
     */
    public static float getNewYPos(Projectile p, float yTrav, float angle)
    {
       return p.getY() - yTrav * 
                (float)FastTrig.cos(Math.toRadians(angle));
    }
    
    /*
     * Attempt to move the Projectile p by calculating the sin/cos
     * results from the specified  x / y distance values.
     * 
     * If the result of moving p means that it collides with the level
     * then undo the move and return false.
     * 
     * Otherwise leave p at its new position and return true.
     */ 
    private static boolean doMove(Projectile p, float xTrav, float yTrav,    
                                  float angle)
    {
        p.setX(getNewXPos(p, xTrav, angle));
        p.setY(getNewYPos(p, yTrav, angle));        
        p.calculateEndPos();

        if(Collider.collidesLevel(p))
        {
            p.stop();
            return false;
        }
        return true;
    }
}
