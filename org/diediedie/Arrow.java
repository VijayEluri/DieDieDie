package org.diediedie;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * An arrow fired by the Player.
 */ 
 /*
  *  good movementAngle stuff here: http://www.zahniser.net/~russell/computer/index.php?title=movementAngle%20and%20Coordinates
  */ 
public class Arrow
{
    private float startX, startY, endX, endY, accelX = 0, accelY = 0, 
                  speedX = 0, speedY = 0, mouseX, mouseY, movementAngle, 
                  moveSpeed = 0.45f, gravity = 0, oldX, oldY,
                  facingAngle = 0, angleChange = 0.1f;
                  
    private final float SIZE = 20f, ACCEL_RATE = 0.09f, AIR_REST = 0.55f,
                  MAX_GRAVITY = 25f, GRAVITY_INCR = 0.1f, 
                  ANGLE_CHANGE_INCR = 0.02f, MAX_ANGLE_CHANGE = 1.11f, 
                  GRAVITY_LINE = 3.34f;
                  
    private Level level = null;
    private Color color = Color.red;
    private boolean isFlying = false;
   
    /**
     * Creates a new arrow at the given position.
     */ 
    public Arrow(float xPos, float yPos, Level lev, int mouseX, int mouseY)
    {
        level = lev;
        setPosition(xPos, yPos);
        this.setMovementAngle(mouseX, mouseY);
        calculateEndPos();
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release(float power)
    {
        accelX = power;
        accelY = power * 0.9f;
        isFlying = true;
    }
    
    public boolean isFlying()
    {
        return isFlying;
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
        movementAngle = (float)Math.toDegrees(Math.atan2(x - startX, startY - y));                                                                       
    }
    
    /**
     * Updates the speed of a release arrow
     */ 
    protected void updateSpeed()
    {          
        speedX = (moveSpeed * accelX);
        speedY = (moveSpeed * accelY);
    }
    
    /**
     * Updates the position of the Arrow on the screen after it has left
     * the Player.
     */
    protected void updatePosition()
    {
        float xTravelled = speedX * AIR_REST;
        float yTravelled = speedY;
             
        /*System.out.println("arrow: " + movementAngle + " degrees");
        System.out.println("\t" + "speedX: " + speedX + ", speedY: "
                           + speedY);*/
        
        // save old position for isGoingDownwards()
        //oldX = startX;
        oldY = startY;
        
        // move in the @movementAngle direction
        startX = (float)(startX + xTravelled * 
                            Math.sin(Math.toRadians(movementAngle)));
        startY = (float)(startY - yTravelled * 
                            Math.cos(Math.toRadians(movementAngle)));   
        applyGravity();       
        adjustFacingAngle();
        calculateEndPos();
        //System.out.println("arrow move angle: " + movementAngle);
        //System.out.println("arrow face angle: " + facingAngle);
    }
    
    private void adjustFacingAngle()
    {
        if(gravity > GRAVITY_LINE)
        {
            if(movementAngle > 0)
            {
                //movementAngle += angleChange;
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
    
    private boolean isGoingDownwards()
    {
        if(startY > oldY)
        {
            return true;
        }
        return false;
    }
    
    /*
     * Applies 'gravity' to the vertical position
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
        g.setColor(this.color);
        g.drawLine(startX, startY, endX, endY);
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
