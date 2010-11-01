package org.diediedie.actors;

import org.diediedie.actors.AnimCreator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/*
 * Blue stick-enemy wielding 2 pistols. 
 */ 
public class Bluey implements Actor
{
    private float xPos, yPos;
    
    // this character can use 
    private final String standLeftPath = "data/bluey_standing_left.png";
    
    private final String[] leftWalkPaths = 
    {
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_1.png"
    };
    
    private Animation leftWalkAnim, rightWalkAnim, leftStandAnim, 
                      rightStandAnim;
        
    public Bluey(float x, float y)
    {
        xPos = x;
        yPos = y;
        createAnimations();
    }
    
    private void createAnimations()
    {
        
    }
    
    public Rectangle getCurrentFrameRect()
    {
        return null;    
    }
    

    public void update()
    {
        
    }
    public float getX(){ return xPos; }   
    public float getY(){ return yPos; } 
    
}
