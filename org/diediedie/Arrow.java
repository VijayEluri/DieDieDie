package org.diediedie;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * An arrow fired by the Player.
 */ 
public class Arrow
{
    private float startX, startY, endX, endY, accelX = 0, speedX = 0, 
                  speedY = 0, maxYSpeed = 20.5f, mouseX, mouseY;
    
    private double angle;
    
    private final float size = 10;
    
    private final float ACCEL_RATE = 0.05f;
    
    private int charge;
    
    
    private boolean released = false;

    
    private Vector2f vec2f = null;
    
    /**
     * Creates a new arrow at the 
     */ 
    public Arrow(float xPos, float yPos)
    {
        this.startX = xPos;
        this.startY = yPos;
    }
    
    /**
     * Informs the Arrow object that it has been released by the player
     */ 
    public void release()
    {
        released = true;
    }
    
    public Vector2f getVec2f()
    {
        return vec2f;
    }
    
    /**
     * Allows the Player to update the angle (aim) of the arrow prior
     * to release, based upon the mouse's position. 
     */ 
    protected void updateAimAngle(float mouseX, float mouseY)
    {
        System.out.println("updateAimAngle: " + mouseX + ", " + mouseY);
        
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        
        
        
        calculateEndPos();
    }  
    
    private void calculateEndPos()
    {
        
    }
    
    protected void setCharge(int charge)
    {
        this.charge = charge;
    }
    
    
    
    public void draw(Graphics g)
    {
        
    }
    
   
    
    
    
    public float getStartX() { return startX; }
    public float getStartY() { return startY; }
    public float getEndX() { return endX; }
    public float getEndY() { return endY; }
}
