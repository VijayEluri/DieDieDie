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
    static final int MAX_ITER = 4;
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
                p.setY(p.getY() - p.getGravityIncr());
                p.calculateEndPos();
                System.out.println("adjusting " + p);
            }
        }
        
        System.out.println("applyGravity(p), yTrav: " + yTrav);
        p.increaseGravityEffect(); 
    }
    
    
    /**
     * For publically moving Projectiles. :)
     */ 
    public static void move(Projectile p)
    { 
        if(!p.isFlying())
        {
            return;
        }
        
        final float xTrav = p.getXSpeed() * p.getAirRes();
        final float yTrav = p.getYSpeed();
                
        approximateMove(p, xTrav, yTrav);        
    }
    
    /*
     * Moves Projectile p as close as possible to the given co-ords
     * before colliding / reaching max iteration point.
     */ 
    private static void approximateMove(Projectile p, float xTrav, float yTrav)
    {
        // first test to see if we can move the whole way without colliding
        if(!doMove(p, xTrav, yTrav))
        {
            p.stop();   
        }
    }
    
    /*
     * Returns the new horizontal position of p after hypothetically 
     * applying horizontal distance xTrav based upon p's getAngle().
     */ 
    public static float getNewXPos(Projectile p, float xTrav)
    {
        return p.getX() + xTrav * (float)FastTrig.sin(
                            Math.toRadians(p.getAngle()));
    }
    
    /*
     * Returns the new vertical position of p after hypothetically 
     * applying vertical distance yTrav based upon p's getAngle().
     */
    public static float getNewYPos(Projectile p, float yTrav)
    {
       return p.getY() - yTrav * (float)FastTrig.cos(
                            Math.toRadians(p.getAngle()));
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
    private static boolean doMove(Projectile p, float xTrav, float yTrav)
    {
        final float oldX = p.getX();
        final float oldY = p.getY();
        
        p.setX(getNewXPos(p, xTrav));
        p.setY(getNewYPos(p, yTrav));        
        p.calculateEndPos();

        if(Collider.collidesLevel(p))
        {
            //p.setX(oldX);
            //p.setY(oldY);
            p.calculateEndPos();
            p.stop();
            return false;
        }
        return true;
    }
}
