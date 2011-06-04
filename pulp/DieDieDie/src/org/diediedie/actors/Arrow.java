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
import pulpcore.math.CoreMath;
import pulpcore.image.CoreGraphics;
import pulpcore.image.Colors;
import pulpcore.animation.Color;
 
/**
 * An arrow fired by the Player.
 * 
 * good movement stuff here:
 
   http://www.zahniser.net/~russell/computer/index.php?title=movementAngle%20and%20Coordinates
 
 */ 
public class Arrow implements Projectile
{      
    // constants
    private final float AIR_REST = 0.85f,
                        MAX_GRAVITY = 26f, 
                        GRAVITY_INCR = 0.115f, 
                        ANGLE_CHANGE_INCR = 0.15f,
                        MAX_ANGLE_CHANGE = 1.6f, 
                        GRAVITY_LINE = 1f, 
                        MOVE_SPEED = 0.6f, 
                        MAX_Y_SPEED = 24.5f,
                        FALLING_ANGLE_CHANGE = 0.44f;
                  
    private int mouseX = 0, 
                mouseY = 0;
                
                
    public static final int SIZE = 18;
    
    private float accelX = 0, 
                  accelY = 0, 
                  speedX = 0, 
                  speedY = 0,  
                  movementAngle = 90f, 
                  gravity = 0f, 
                  facingAngle = 0f, 
                  angleChange = 0.01f,
                  startX = 0, 
                  startY = 0, 
                  oldX = 0, 
                  oldY = 0, 
                  endX = 0, 
                  endY = 0;
                                      
    private Level level = null;

    private boolean flying = false, 
                    collided = false, 
                    goingDown = false;
    
    /**
     * Creates a new arrow at the given position.
     */ 
    public Arrow(int xPos, int yPos, Level lev, int mouseX, 
                 int mouseY)
    {
        setLevel(lev);
        setPosition(xPos, yPos);
        setMovementAngle(mouseX, mouseY);
        calculateEndPos();
    }
    
    
    @Override
    public void increaseGravityEffect()
    {
        if(gravity < MAX_GRAVITY)
        {
            gravity += GRAVITY_INCR;
        } 
        // CoreSystem.print(this + "| grav: " + gravity);
    }  

    @Override
    public void setLevel(Level l)
    {
        level = l;
    }

    
    public final float getMaxGravity()
    {
        return MAX_GRAVITY;
    }

    @Override
    public Level getLevel()
    {
        return level;
    }
    
    @Override
    public float getAirRes()
    {
        return AIR_REST;
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release(float power)
    {
        accelX = power * AIR_REST;
        accelY = power * AIR_REST;
        flying = true;
    }
    
    @Override
    public boolean isFlying()
    {
        return flying;
    }
    
    /**
     * Sets the x/y coordinate position of the Arrow
     */ 
    protected void setPosition(int x, int y)
    {
        startX = x;
        startY = y;
    }
    
    /**
     * Allows the Player to update the movementAngle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    public void updateAiming(float mouseX, float mouseY)
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
    
    @Override // boilerplate ignorable
    public void update(){ }
    
    /**
     * Updates the speed of a release arrow
     */ 
    protected void updateSpeed()
    {          
        setXSpeed(MOVE_SPEED * accelX);
        setYSpeed(MOVE_SPEED * accelY);
    }
    
    
    @Override
    public void setX(int f)
    {
        startX = f;
    }
    
    @Override
    public void setY(int f)
    {
        startY = f;
    }
    
    @Override
    public void setYSpeed(float f)
    {
        speedY = f;
    }
    
    @Override
    public void setXSpeed(float f)
    {
        speedX = f;
    }
    
    @Override
    public float getMaxFallSpeed()
    {
        return MAX_Y_SPEED;
    }
    
    /**
     * Updates the position of the Arrow on the screen after it has left
     * the Player.
     */
    public void updatePosition()
    {
        
        /*CoreSystem.print("Arrow(" + hashCode() + ")updatePosition(): "
                            + "oldX, oldY: " + oldX + ", " + oldY
                            + "new: " + startX + ", " + startY);*/
        adjustFacingAngle();
        oldX = startX;
        oldY = startY;
        Mover.move(this);
        Mover.applyGravity(this);
        
    }
        
        
    /*
     * Stops the movement of the Arrow
     */ 
    public void stop()
    {
        /*CoreSystem.print("stopping Arrow " + hashCode() + "; speed " +
                        speedX + ", " + speedY);*/
        resetAccelX();
        resetAccelY();
        setXSpeed(0);
        setYSpeed(0);
        flying = false;
    }
    
    @Override
    public float getYSpeed()
    {
        return speedY;
    }
    
    @Override
    public float getXSpeed()
    {
        return speedX;
    }
    
    // applies 'gravity' to the arrow WHEN IT IS IN FLIGHT
    public void adjustFacingAngle()
    {
        if(!isFlying())
        {
            return;
        }
        
        if(!isGoingDown() && gravity > GRAVITY_LINE)// && gravity < MAX_GRAVITY)
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
        else if(isGoingDown())
        {
            if(movementAngle > 0)
            {
                facingAngle += (angleChange * FALLING_ANGLE_CHANGE);
            }
            else
            {
                facingAngle -= (angleChange * FALLING_ANGLE_CHANGE);
            }
        }
        /*CoreSystem.print("arrow " + this.hashCode() + " facing: " + 
                           facingAngle);*/
    }

    @Override
    public float getGravityLine()
    {
        return GRAVITY_LINE;
    }
    
    /**
     * Guess.
     */ 
    public boolean isGoingDown()
    {
        if(oldY < startY)
        {
            //CoreSystem.print("going down");
            return true;
        }
        return false;
    }
    
    /*
     * Applies 'gravity' to the y-axis position once it has left the 
     * player
     */ 
    public float getGravity()
    {
        return gravity;
    }
    


    
    /*
     * Works out the end point of the arrow based on its SIZE at
     * movementAngle.
     */ 
    @Override
    public void calculateEndPos()
    {
        /*
                endX = startX + SIZE * FastTrig.sin(
                                Math.toRadians(movementAngle + facingAngle)));
                endY = startY - SIZE * FastTrig.cos(
                                Math.toRadians(movementAngle + facingAngle)));
        */
        endX = (float)startX + SIZE * (float)Math.sin(Math.toRadians(
            (float)movementAngle + (float)facingAngle));
        endY = (float)startY - SIZE * (float)Math.cos(Math.toRadians(
            (float)movementAngle + (float)facingAngle));   
    }
    
    
    @Override
    public float getX()
    {
        return startX;
    }
    
    @Override
    public float getY()
    {
        return startY;
    }
    
    @Override
    public float getEndX()
    {
        return endX;
    }
    
    @Override
    public int getEndY()
    {
        return endY;
    }
    
    /**
     * Public method for receiving CoreGraphics object from Slick.
     */ 
    @Override
    public void draw(CoreGraphics g)
    {
        int oldColor = g.getColor();
        g.setColor(Color.red);
        g.drawLine(startX, startY, endX, endY);
        g.setColor(oldColor);
    }
    
    public float getAngle()
    {
        return movementAngle;
    }
    
     @Override
    public void resetAccelX()
    {
        accelX = 0;
    }
    
    @Override
    public void resetAccelY()
    {
        accelY = 0;
    }
}
