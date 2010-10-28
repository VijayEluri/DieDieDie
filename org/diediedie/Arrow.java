package org.diediedie;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * An arrow fired by the Player.
 */ 
 /*
  *  good angle stuff here: http://www.zahniser.net/~russell/computer/index.php?title=Angle%20and%20Coordinates
  */ 
public class Arrow
{
    private float startX, startY, endX, endY, accelX = 0, accelY = 0, 
                  speedX = 0, speedY = 0, mouseX, mouseY, angle, 
                  moveSpeed = 0.4f;
                  
    private final float SIZE = 20f, AIR_REST = 0.15f, ACCEL_RATE = 0.1f;
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
        this.setAngle(mouseX, mouseY);
        calculateEndPos();
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release(float power)
    {
        accelX = power;
        accelY = power;
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
     * Allows the Player to update the angle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    protected void updateAiming(float mouseX, float mouseY)
    {       
        setAngle(mouseX, mouseY);
        calculateEndPos();
    }  
    
    /* 
     * calculate the angle based upon destination x/y
     */
    private void setAngle(float x, float y)
    {
        angle = (float)Math.toDegrees(Math.atan2(x - startX,
                                                 startY - y));                                                                       
    }
    
    /**
     * Updates the speed of a release arrow
     */ 
    protected void updateSpeed()
    {        
        decelerate();    
        speedX = (moveSpeed * accelX);
        speedY = (moveSpeed * accelY);
    }
    
    
    private void decelerate()
    {
        /*accelX -= ACCEL_RATE;
        if(accelX < 0)
        {
            accelX = 0;
        }*/
    }
    
    /**
     * Updates the position of the Arrow on the screen after it has left
     * the Player.
     */
    protected void updatePosition()
    {
        float xTravelled = speedX;
        float yTravelled = speedY;
             
        /*System.out.println("arrow: " + angle + " degrees");
        System.out.println("\t" + "speedX: " + speedX + ", speedY: "
                           + speedY);*/
        
        startX = (float)(startX + xTravelled * 
                            Math.sin(Math.toRadians(angle)));
        startY = (float)(startY - yTravelled * 
                            Math.cos(Math.toRadians(angle)));   
        //applyGravity()       
        calculateEndPos();
    }

    /*
     * Applies 'gravity' to the angle and yPos
     */ 
    private void applyGravity()
    {
        startY += speedY * level.gravity;
        
    }
    
    /*
     * Works out the end point of the arrow based on its SIZE at
     * angle.
     */ 
    private void calculateEndPos()
    {
        endX = (float)(startX + SIZE * Math.sin(Math.toRadians(angle)));
        endY = (float)(startY - SIZE * Math.cos(Math.toRadians(angle)));
    }
    

    /*
     * Subtracts from speed to simulate air resistance. 
     */ 
    private void applyAirRest()
    {
        
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
     * Return the y-axis value of the end point
     */ 
    public float getEndY()
    {
        return endY; 
    }
}
