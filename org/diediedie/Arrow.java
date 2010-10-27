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
                  moveSpeed = 2.3f;
                  
    private final float SIZE = 20f, AIR_REST = 0.3f, ACCEL_RATE = 0.05f,
                        MAX_Y_SPEED = 10.5f, MAX_Y_ACCEL = 3.4f;
    private Level level = null;
    private Color color = Color.red;
    private boolean isFlying = false;
    private Vector2f vec2f = new Vector2f();
    
    /**
     * Creates a new arrow at the given position.
     */ 
    public Arrow(float xPos, float yPos, Level lev)
    {
        level = lev;
        setPosition(xPos, yPos);
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release(int power)
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
    
    // calculate the angle 
    private void setAngle(float x, float y)
    {
        angle = (float)Math.toDegrees(Math.atan2(x - startX,
                                                 startY - y)); 
        //System.out.println("angle: " + angle);
    }
    
    /**
     * Updates the speed of a release arrow
     */ 
    protected void updateSpeed()
    {        
        accelX -= ACCEL_RATE;
        if(accelX < 0)
        {
            accelX = 0;
        }
        
        speedX = (moveSpeed + accelX);
        
        speedY = (moveSpeed + accelY);
        
        
        
        
        System.out.println("Arrow.updateSpeed(): speedX: " + speedX);
        
        
    }
    
    /**
     * Updates the position of the Arrow on the screen after it has left
     * the Player.
     */
    protected void updatePosition()
    {
        // calculate horizontal distance travelled
        float xTravelled = speedX * AIR_REST;
        float yTravelled = speedY ;
        
        startX = (float)(startX + xTravelled * Math.sin(Math.toRadians(angle)));
        startY = (float)(startY - xTravelled * Math.cos(Math.toRadians(angle)));
        
        // gravity
        if(speedY < MAX_Y_SPEED)
        {
            startY += level.gravity;
        }
        
        System.out.println("Arrow.updatePosition(): startX: " + startX);
        System.out.println("Arrow.updatePosition(): startY: " + startY);
        
        calculateEndPos();
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
