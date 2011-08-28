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
import java.lang.Math;

import org.newdawn.slick.util.FastTrig;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Projectile;

/**
 * Class used to Actors and Projectile(s) around a Level. 
 */ 
public class ObjectMover
{    
    static final int INTERVAL = 10;
	private static long lastPrintTime = 0;
    
    /**
     * Attempts to move the Actor, a, according to its x / y speeds.
     * 
     * Returns true if moved *horizontally* since last position.
     */ 
    public static boolean move(final Actor a)
    {
    	final float oldX = a.getX();
        final float oldY = a.getY();
        // test new position
        
        // vertical 
        a.setY(a.getY() + a.getYSpeed());
        
        if(Collider.collidesLevel(a))
        {        
            if(a.getY() >= oldY)
            {
            	/*
            	 * Only set canJump=true if we hit the floor (not the 
            	 * ceiling)
            	 */
                a.setCanJump(true);
            }
            Aligner.alignToObstacle(a);
            a.setYSpeed(0);
            a.setY(oldY);            
        } 
        else
        {
            a.setCanJump(false);
        }
        
        // horizontal 
        a.setX(a.getX() + a.getXSpeed());
        
        if(Collider.collidesLevel(a))
        {
            a.setX(oldX);
			a.setXSpeed(0);
            a.resetAccelX();
         ;
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
        p.setY(p.getY() + p.getGravity());
        p.increaseGravityEffect(); 
        p.calculateEndPos();
        
        if(Collider.collidesLevel(p))
        {
            p.stop();
            while(Collider.collidesLevel(p))
            {
            	p.setCollided(true);
                //final float oldY = p.getY();
                p.setY(p.getY() - Aligner.INCR);
                p.calculateEndPos();
            }
        }
        
    }
    
    /**
     * Nice static function for moving Projectiles around a Level. 
     */ 
    public static void move(Projectile p)
    {   
    	//
    	p.setOldStartX(p.getX());
    	p.setOldStartY(p.getY());
    	
    	applyGravity(p);
    	
        float xTrav = p.getXSpeed() * p.getAirRes();
        float yTrav = p.getYSpeed();
       
        if(!doMove(p, xTrav, yTrav, p.getMovementAngle()))
        {
            float reverse = p.getMovementAngle() + 180;
            float xMove = xTrav / INTERVAL; 
            float yMove = xTrav / INTERVAL;
            while(!doMove(p, xMove, yMove, reverse));
        }
        // adjust facing angle
        int newX = (int)p.getX();
        int newY = (int)p.getY();
        
        final long now = System.currentTimeMillis();
        
        float angleActuallyMoved = 
        	(float)Math.toDegrees(
        			Math.atan2(p.getX() - p.getOldStartX(), 
        					   p.getY() - p.getOldStartY()));
        
        if(now - lastPrintTime  >= 200)
        {
        	System.out.println(
        			"Projectile moved (" 
        			+ p.getOldStartX() + ", " + p.getOldStartY() 
        			+ ") to ("
        			+ newX + ", " + newY + ")" 
        			+ ", angle -> " 
        			+ angleActuallyMoved);
        	lastPrintTime = now;
        }
    	p.setFacingAngle(angleActuallyMoved);
	    p.calculateEndPos();
    }
    
    
    /*
     * Returns the new horizontal position of p after hypothetically 
     * applying horizontal distance xTrav based upon p's getMovementAngle().
     */ 
    public static float getNewXPos(Projectile p, float xTrav, float angle)
    {
        return p.getX() + xTrav * (float)FastTrig.sin(
                            Math.toRadians(angle));
    }
    
    /*
     * Returns the new vertical position of p after hypothetically 
     * applying vertical distance yTrav based upon p's getMovementAngle().
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
