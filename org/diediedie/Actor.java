package org.diediedie.actors;

import org.newdawn.slick.Graphics;


public abstract class Actor
{
    protected float xPos, yPos;
    //private String name;
    
    
        
    /**
     * Returns the x position
     */ 
    public float getX() { return xPos; }
    /**
     * Returns the y position
     */ 
    public float getY() { return yPos; } 
    public abstract void update();
}  
