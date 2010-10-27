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
    private float startX, startY, endX, endY, accelX = 0, speedX = 0, 
                  speedY = 0, maxYSpeed = 20.5f, mouseX, mouseY;
    private float angle;
    private final float size = 20f, airRest = 0.6f;
    
    private final float ACCEL_RATE = 0.05f;
    private int charge, delta;
    private Color color = Color.red;
    private boolean released = false;
    private Vector2f vec2f = new Vector2f();
    
    /**
     * Creates a new arrow at the given position.
     */ 
    public Arrow(float xPos, float yPos)
    {
        setPosition(xPos, yPos);
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release()
    {
        released = true;
    }
      
    /**
     * Sets the x/y coordinate position of the Arrow
     */ 
    protected void setPosition(float x, float y)
    {
        this.startX = x;
        this.startY = y;
    }
    
    /**
     * Allows the Player to update the angle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    protected void updateAiming(float mouseX, float mouseY)
    {
        //System.out.println("updateAimAngle: " + mouseX + ", " + mouseY);
        
        setAngle(mouseX, mouseY);
        calculateEndPos();
    }  
    
    // calculate the angle and use it to set the Vector
    private void setAngle(float mouseX, float mouseY)
    {
        angle = (float)Math.toDegrees(Math.atan2(mouseX - startX, 
                                          startY - mouseY)); 
        System.out.println("angle: " + angle);
    }
    
    /*
     * 
     */ 
    private void calculateEndPos()
    {
        endX = (float)(startX + size * Math.sin(Math.toRadians(angle)));
        endY = (float)(startY - size * Math.cos(Math.toRadians(angle)));
    }
    
    protected void setCharge(int charge)
    {
        this.charge = charge;
    }
    
    
    
    public void draw(Graphics g)
    {
        g.setColor(this.color);
        g.drawLine(startX, startY, endX, endY);
    }
    
    public float getEndX()
    {
        return endX;
    }
    public float getEndY()
    {
        return endY; 
    }
}
