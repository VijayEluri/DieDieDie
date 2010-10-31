package org.diediedie.actors;

import org.newdawn.slick.Graphics;


interface Actor
{            
    /**
     * Returns the x position
     */ 
    float getX();// { return xPos; }
    /**
     * Returns the y position
     */ 
    float getY();// { return yPos; } 
    void update();
}  
