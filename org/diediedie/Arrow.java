package org.diediedie;

/**
 * An arrow fired by the Player.
 */ 
public class Arrow
{
    // x/y coordinates for the Arrow's point (start) and feather (end)
    private float startX, startY, endX, endY;
    
    
    
    public Arrow(float xPos, yPos)
    {
        this.startXPos = xPos;
        this.startYPos = yPos;
        
        calculateEndPos();
    }

    private void calculateEndPos()
    {
        
    }

    public float getStartX() { return startX; }
    public float getStartY() { return startY; }
    public float getEndX() { return endX; }
    public float getEndY() { return endY; }
    



}
