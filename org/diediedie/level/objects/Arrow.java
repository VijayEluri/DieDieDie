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
package org.diediedie.level.objects;

import java.util.Iterator;
import java.util.List;

import org.diediedie.level.Level;
import org.diediedie.level.actors.Enemy;
import org.diediedie.level.actors.tools.Collider;
import org.diediedie.level.actors.tools.Direction;
import org.diediedie.level.actors.tools.ObjectMover;
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
                        AIR_REST = 0.8f,
                        MAX_GRAVITY = 20f, 
                        GRAVITY_INCR = 0.1f, 
                        GRAVITY_LINE = 1f, 
                        MOVE_SPEED = .6f, 
                        MAX_Y_SPEED = 26.5f;
                        
    private float startX = 0, 
                  startY = 0, 
                  oldX = 0, 
                  oldY = 0, 
                  endX = 0, 
                  endY = 0, 
                  accelX = 0, 
                  accelY = 0, 
                  speedX = 0, speedY = 0,  
                  movementAngle = 90, 
                  // ^ initial angle when loading into the Bow
                  gravity = 0f, 
                  // ^ gravity effect
                  facingAngle = 0;
                                      
    private Level level = null;

    private boolean flying = false, 
                    collided = false;

	private boolean collidedWithEnemy = false;

	private Enemy enemyCollidedWith = null;

	private boolean outOfBounds = false;
    
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
    
 
   /*
    public float getMoveSpeed()
    {
    	return -1.0f;
    }*/
    
    /**
     * Convenience class method. Updates all of the
     * arrows in the list that are moving. 
     */
    public static void updateFiredArrows(List<Arrow> arrows)
    {
        
        Iterator<Arrow> it = arrows.iterator();
        
        while(it.hasNext())
        {
            Arrow a = it.next();
            if(a.isOutOfBounds() || a.getCollidedWithEnemy())
            {
                // damage already done in Enemy.doCollision
                // we can safely remove the arrow here
                System.out.println("removing arrow" + a);
                it.remove();
            }
            else if(a.isFlying())
            {
                a.update();
            }
        }
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
    public void setPosition(float x, float y)
    {
        startX = x;
        startY = y;
    }
    
    /**
     * Allows the Player to df the movementAngle (aim) of the arrow prior
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
    public void update()
    {
    	updateSpeed();
        updatePosition();
    }
    
   
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
    
    /*
     * Returns the damage caused by this arrow at its current
     * velocity.
     */
    @Override
    public float getDamage()
    {

        final float xChange = Math.abs(getOldStartX() - getX());
        final float yChange = Math.abs(getOldStartY() - getY());
        
        /*System.out.println("Speed changes (last frame, this frame):");
        System.out.println("--------------------------------------");
        System.out.println(
            "\t x : " + xChange + "\n" +
            "\t y : " + yChange + "\n");
        */
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
	private void updatePosition()
    {
        
        /*System.out.println("Arrow(" + hashCode() + ")updatePosition(): "
                            + "oldX, oldY: " + oldX + ", " + oldY
                            + "new: " + startX + ", " + startY);*/
        
        //oldX = startX;
        //oldY = startY;
        
        ObjectMover.move(this);
        
        Enemy e = Collider.collidesEnemy(this);
        
        if(e != null)
        {
        	collidedWithEnemy = true;
            //System.out.println(this + " collided with Enemy " + e);
            e.doCollision(this);
            collided = true;
        }
    } 
    
    @Override
    public boolean getCollidedWithEnemy()
    {
        return collidedWithEnemy ;
    }
    
    /*
     * Stops the movement of the Arrow
     */ 
    @Override
	public void stop()
    {
        System.out.println(
        	"stopping Arrow " 
        	+ hashCode()
        	+ ": speed " 
        	+ speedX + ", " + speedY);
        resetAccelX();
        resetAccelY();
        setXSpeed(0);
        setYSpeed(0);
        flying = false;
        collided = true;
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
     * 
     * startX/Y = TIP of the arrow
     * endX/Y = FEATHER of the arrow
     */ 
    @Override
    public void calculateEndPos()
    {
    	//System.out.println(
    		//"calculateEndPos() angle is " + movementAngle +  facingAngle);
    	
    	
    	if(!isFlying() && !getCollided())
    	{
	    	final float angle = movementAngle + facingAngle;
	        
	    	endX = (float)(startX + SIZE * FastTrig.sin(
	                    Math.toRadians(angle)));
	        endY = (float)(startY - SIZE * FastTrig.cos(
	                    Math.toRadians(angle)));
    	}
    	else if(!getCollided())
    	{
    		endX = (float)(startX - SIZE * FastTrig.sin(
    					Math.toRadians(facingAngle)));
    		endY = (float)(startY - SIZE * FastTrig.cos(
    					Math.toRadians(facingAngle)));
    	}
    }
    
    @Override
    public boolean getCollided()
    {
    	return collided;
    }
    
    @Override
    public void setCollided(boolean b)
    {
    	collided = b;
    	flying = false;
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
        g.drawGradientLine(
        	startX, startY, 
        	Color.black, 
        	endX, endY,
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

	@Override
	public void setFacingAngle(float a) 
	{
		facingAngle = a;
	}

	@Override
	public void setOldStartX(float x)
	{
		oldX = x;
		
	}

	@Override
	public void setOldStartY(float y) 
	{
		oldY = y;
		
	}

	@Override
	public void setCollidedWithEnemy(Enemy e) 
	{
		assert e != null;
		collided = true;
		collidedWithEnemy = true;
		enemyCollidedWith = e;
	}
	
	public Enemy getEnemyCollidedWith()
	{
		return enemyCollidedWith;
	}

	@Override
	public void bounce(ArrowBouncer ab) 
	{
		System.out.println("BOUNCE");
		// swap horizontal
		
		movementAngle = movementAngle + ab.getAngleChange();
		speedX += ab.getSpeedBoost();
	}
	
	@Override
	public boolean isOutOfBounds() 
	{
		return outOfBounds;
	}

	@Override
	public void setOutOfBounds(boolean b) 
	{
		outOfBounds = b;
	}

	@Override
	public String getName() 
	{
		return "Arrow : " + getX() + " , " + getY();
	}
}

