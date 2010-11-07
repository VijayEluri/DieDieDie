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

import org.diediedie.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.util.FastTrig;
 
/**
 * An arrow fired by the Player.
 * 
 * good movement stuff here:
 * http://www.zahniser.net/~russell/computer/index.php?title=movementAngle%20and%20Coordinates
 */ 
public class Arrow
{      
    private float startX, startY, endX, endY, accelX = 0, accelY = 0, 
                  speedX = 0, speedY = 0, mouseX, mouseY, 
                  movementAngle = 90, 
                  gravity = 0, oldX, oldY,
                  facingAngle = 0, angleChange = 0.1f, xTrav, yTrav;
                  
    private final float SIZE = 19f, ACCEL_RATE = 0.09f, AIR_REST = 0.6f,
                  MAX_GRAVITY = 25f, GRAVITY_INCR = 0.1f, 
                  ANGLE_CHANGE_INCR = 0.06f, MAX_ANGLE_CHANGE = 1.09f, 
                  GRAVITY_LINE = 2.8f, ALIGN_INCR = 0.009f,
                  MOVE_SPEED = 0.44f;
                  
    private final int REVERSE = 180;
    
    private Level level = null;

    private boolean flying = false, collided = false;
   
    
    /**
     * Creates a new arrow at the given position.
     */ 
    public Arrow(float xPos, float yPos, Level lev, int mouseX, 
                 int mouseY)
    {
        level = lev;
        setPosition(xPos, yPos);
        setMovementAngle(mouseX, mouseY);
        calculateEndPos();
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release(float power)
    {
        accelX = power;
        accelY = power;// * 0.9f;
        flying = true;
    }
    
    public boolean isFlying()
    {
        return flying;
    }
    
    /**
     * Sets the x/y coordinate position of the Arrow
     */ 
    protected void setPosition(float x, float y)
    {
        startX = x;
        startY = y;
    }
    
    /**
     * Allows the Player to update the movementAngle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    protected void updateAiming(float mouseX, float mouseY)
    {       
        setMovementAngle(mouseX, mouseY);
        calculateEndPos();
    }  
    
    /* 
     * calculate the movementAngle based upon destination x/y
     */
    private void setMovementAngle(float x, float y)
    {
        movementAngle = (float)Math.toDegrees(Math.atan2(x - startX, 
                                              startY - y));
    }
    
    /**
     * Updates the speed of a release arrow
     */ 
    protected void updateSpeed()
    {          
        speedX = (MOVE_SPEED * accelX);
        speedY = (MOVE_SPEED * accelY);
    }
    
    /**
     * Updates the position of the Arrow on the screen after it has left
     * the Player.
     */
    protected void updatePosition()
    {
        if(!flying)
        {
            return;
        }
        
        xTrav = speedX * AIR_REST;
        yTrav = speedY;
             
        // save old position 
        oldY = startY;
        oldX = startX;
        
        startX = (float)(startX + xTrav * 
                            FastTrig.sin(Math.toRadians(movementAngle)));
        startY = (float)(startY - yTrav * 
                            FastTrig.cos(Math.toRadians(movementAngle)));
        applyGravity();       
        adjustFacingAngle();
        calculateEndPos();
        
        if(collidesLevel())
        {
            stop();
        }
    }
    
  
    /*
     * Returns true if the arrow intersects with collision Tile on the
     * Level
     */ 
    private boolean collidesLevel()
    {
        if(level.collides(new Line(startX, startY, endX, endY)))
        {
            return true;
        }
        return false;
    }
        
    /*
     * Stops the movement of the Arrow
     */ 
    private void stop()
    {
        flying = false;
        accelX = 0;
        accelY = 0;
        speedX = 0;
        speedY = 0;
    }
    
    // applies 'gravity' to the arrow WHEN IT IS IN FLIGHT
    private void adjustFacingAngle()
    {
        if(gravity > GRAVITY_LINE)
        {
            if(movementAngle > 0)
            {
                facingAngle += angleChange;
            }
            else
            {
                facingAngle -= angleChange;
            }
            if(angleChange < MAX_ANGLE_CHANGE)
            {
                angleChange += ANGLE_CHANGE_INCR;
            }
        }
    }
    
    public boolean isGoingDown()
    {
        if(startY > oldY)
        {
            return true;
        }
        return false;
    }
    
    /*
     * Applies 'gravity' to the y-axis position once it has left the 
     * player
     */ 
    private void applyGravity()
    {
        startY += gravity;
        if(gravity < MAX_GRAVITY)
        {
            gravity += GRAVITY_INCR;
        }
        //System.out.println("gravity on arrow: " + gravity);
    }
    
    /*
     * Works out the end point of the arrow based on its SIZE at
     * movementAngle.
     */ 
    private void calculateEndPos()
    {
        endX = (float)(startX + SIZE * Math.sin(
                        Math.toRadians(movementAngle + facingAngle)));
        endY = (float)(startY - SIZE * Math.cos(
                        Math.toRadians(movementAngle + facingAngle)));
    }
    
    /**
     * Public method for receiving Graphics object from Slick.
     */ 
    public void draw(Graphics g)
    {
        if(collided)
        {
            return;
        }
        //g.setColor(this.color);
        g.drawGradientLine(startX, startY, Color.black, endX, endY,
                            Color.red);
    }
    
    /**
     * Return the x-axis value of the end point
     */ 
    public float getEndX()
    {
        return endX;
    } 
    /**
     * Return the x-axis value of the start point
     */ 
    public float getStartX()
    {
        return startX;
    } 
    /**
    /**
     * Return the y-axis value of the end point
     */ 
    public float getEndY()
    {
        return endY; 
    }
    
    public float getAngle()
    {
        return movementAngle;
    }
}
