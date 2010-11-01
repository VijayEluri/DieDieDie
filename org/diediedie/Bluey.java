package org.diediedie.actors;

import org.diediedie.actors.AnimCreator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/*
 * Blue stick-enemy wielding 2 pistols. 
 */ 
public class Bluey implements Actor
{
    private float xPos, yPos;
    private boolean setUp = false;
    
    private final String leftStandPath = "data/bluey_standing_left.png";
    
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
                      rightStandAnim, currentAnim = null;
        
    public Bluey(float x, float y)
    {
        xPos = x;
        yPos = y;
        
        if(!setUp)
        {
            createAnimations();
            setUp = true;
        }
    }
    
    private void createAnimations()
    {
        Image leftStand1 = AnimCreator.loadImage(leftStandPath);
        Image rightStand1 = leftStand1.getFlippedCopy(true, false);        
        
        Image[] leftStandImages = null, rightStandImages = null;
        
        // put into arrays to use them as a 1-frame animations
        leftStandImages[0] = leftStand1;
        rightStandImages[0] = rightStand1;        
        
        leftStandAnim = new Animation(leftStandImages, 
                                      Actor.ANIM_DURATION,false);
        
        rightStandAnim = new Animation(rightStandImages,
                                      Actor.ANIM_DURATION,false);
    }
    
    public Rectangle getCurrentFrameRect()
    {
        return null;    
    }
     
    public void update()
    {
        updatePosition();
        updateProjectiles();
    }
    private void updatePosition()
    {
        
    }
    private void updateProjectiles()
    {
        
    }
    
    public void draw(Graphics g)
    {
        g.drawAnimation(currentAnim, getX(), getY());
        drawProjectiles(g);
    }
    
    private void drawProjectiles(Graphics g)
    {
        
    }
    
    public float getX(){ return xPos; }   
    public float getY(){ return yPos; } 
    
}
