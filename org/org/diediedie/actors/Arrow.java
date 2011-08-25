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
import org.diediedie.actors.Projectile;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.tools.Collider;
import org.diediedie.actors.tools.Direction;
import org.diediedie.actors.tools.ObjectMover;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.util.FastTrig;
 
/**
 * An arrow fired by the Player.
 * 
 * good movement stuff here:
 
   http://www.zahniser.net/~russell/computer/index.php?title=movementAngle%20and%20Coordinates
 
 */ 
public class Arrow extends Object implements Projectile
{      
    // constants
    private final float SIZE = 18f, 
                        AIR_REST = 0.85f,
                        MAX_GRAVITY = 26f, 
                        GRAVITY_INCR = 0.115f, 
                        ANGLE_CHANGE_INCR = 0.15f,
                        MAX_ANGLE_CHANGE = 1.6f, 
                        GRAVITY_LINE = 1f, 
                        MOVE_SPEED = 0.6f, 
                        MAX_Y_SPEED = 24.5f,
                        FALLING_ANGLE_CHANGE = 0.440010f;
                        
    private float mouseX = 0, 
                  mouseY = 0,
                  startX = 0, 
                  startY = 0, 
                  oldX = 0, 
                  oldY = 0, 
                  endX = 0, 
                  endY = 0, 
                  accelX = 0, 
                  accelY = 0, 
                  speedX = 0, speedY = 0,  
                  movementAngle = 90, 
                  gravity = 0f, 
                  facingAngle = 0, angleChange = 0.01f;
                                      
    private Level level = null;

    private boolean flying = false, 
                    collided = false, 
                    goingDown = false;
    
    /**
     * Creates a new arrow at the given position.
     *
     */ 
    public Arrow(float xPos, float yPos, Level lev, int mouseX, 
                 int mouseY)
    {
        setLevel(lev);
        setPosition(xPos, yPos);
        setMovementAngle(mouseX, mouseY);
        calculateEndPos();
    }
    
    /**
     * Returns the previous x position of the start of the arrow
     */
    @Override
    public float getOldStartX()
    {
        return oldX;
    }
    
    @Override
    public float getMoveSpeed()
    {
    	return -1.0f;
    }
    
    
    /**
     * Returns the previous y position of the start of the arrow
     */
    @Override
    public float getOldStartY()
    {
        return oldY;  
    }
    
    @Override
    public void increaseGravityEffect()
    {
        if(gravity < MAX_GRAVITY)
        {
            gravity += GRAVITY_INCR;
        } 
        // System.out.println(this + "| grav: " + gravity);
    }  
    
    /** 
     * 
     */
    @Override
    public void setLevel(Level l)
    {
        level = l;
    }

    
    @Override
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
    @Override
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
    protected void setPosition(float x, float y)
    {
        startX = x;
        startY = y;
    }
    
    /**
     * Allows the Player to update the movementAngle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    @Override
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
    
   
    protected void updateSpeed()
    {          
        setXSpeed(MOVE_SPEED * accelX);
        setYSpeed(MOVE_SPEED * accelY);
    }
    
    @Override
    public Shape getShape()
    {
        return new Line(startX, startY, endX, endY);   
    }
    
    @Override
    public float getDamage()
    {

        final float xChange = Math.abs(getOldStartX() - getX());
        final float yChange = Math.abs(getOldStartY() - getY());
        /*
            System.out.println("\tARROW DAMAGE");
            System.out.println("\tSpeed :");
            System.out.println("\t\t" + "x change : " + xChange);
            System.out.println("\t\t" + "y change : " + yChange);
            
            System.out.println(
                "\t trying damage equation " +
                "d = abs(xChange+yChange) * 3");
            final float damage = Math.abs(xChange + yChange) * 3;
            System.out.print("== " + damage);
            return damage;
            
            System.out.println("\t damage == " + damage);
        */
        System.out.println("Speed changes (last frame, this frame):");
        System.out.println("--------------------------------------");
        System.out.println(
            "\t x : " + xChange + "\n" +
            "\t y : " + yChange + "\n");
        
        final float MULT = 3.3f;
        
        float bigger;
        if(xChange > yChange) 
        {
            bigger  = xChange;
        }
        else 
        {
            bigger = yChange;
        }
        System.out.println("\tUsing " + bigger);
        final float damage = bigger * MULT;
        System.out.println(
            "\t" + bigger + " * " + MULT + " == " + damage); 
        return damage;
    }
    
    @Override
    public void setX(float f)
    {
        startX = f;
    }
    
    @Override
    public void setY(float f)
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
     *
     * On collision, initiates damage on any Enemy it hits.
     */
    @Override
	public void updatePosition()
    {
        
        /*System.out.println("Arrow(" + hashCode() + ")updatePosition(): "
                            + "oldX, oldY: " + oldX + ", " + oldY
                            + "new: " + startX + ", " + startY);*/
        adjustFacingAngle();
        oldX = startX;
        oldY = startY;
        ObjectMover.applyGravity(this);
        ObjectMover.move(this);
        
        Enemy e = Collider.collidesEnemy(this);
        
        if(e != null)
        {
            //System.out.println(this + " collided with Enemy " + e);
            e.doCollision(this);
            collided = true;
        }
    } 
    
    @Override
    public boolean collidedWithEnemy()
    {
        return collided;
    }
    
    /*
     * Stops the movement of the Arrow
     */ 
    @Override
	public void stop()
    {
        /*System.out.println("stopping Arrow " + hashCode() + "; speed " +
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
    @Override
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
        /*System.out.println("arrow " + this.hashCode() + " facing: " + 
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
    @Override
	public boolean isGoingDown()
    {
        if(oldY < startY)
        {
            //System.out.println("going down");
            return true;
        }
        return false;
    }
    
    /*
     * Applies 'gravity' to the y-axis position once it has left the 
     * player
     */ 
    @Override
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
        endX = (float)(startX + SIZE * FastTrig.sin(
                        Math.toRadians(movementAngle + facingAngle)));
        endY = (float)(startY - SIZE * FastTrig.cos(
                        Math.toRadians(movementAngle + facingAngle)));
    }
    
    @Override
    public float getFacingAngle()
    {
        return facingAngle;
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
    public float getEndY()
    {
        return endY;
    }
    
    /**
     * Public method for receiving Graphics object from Slick.
     */ 
    @Override
    public void draw(Graphics g)
    {
        g.drawGradientLine(startX, startY, Color.black, endX, endY,
                           Color.red);
    }
    
    @Override
	public float getMovementAngle()
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
