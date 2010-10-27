package org.diediedie;


/**
 * An arrow fired by the Player.
 */ 
public class Arrow
{
    private float startX, startY, endX, endY;
    
    public Arrow(float xPos, float yPos)
    {
        this.startX = xPos;
        this.startY = yPos;
        
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
