package org.diediedie.actors;


import org.diediedie.Level;
import org.diediedie.actors.Direction;
import org.diediedie.actors.State;

import java.util.List;
import java.util.ArrayList;
import org.diediedie.Tile;
import org.diediedie.actors.AnimCreator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Graphics;

/*
 * Blue stick-enemy wielding 2 pistols. 
 */ 
public class Bluey implements Actor, Enemy
{
    private List<State> states = new ArrayList<State>();  
    
    
    
    private int health;
    private Direction facing = null;
    private Level level;
    private float xPos, yPos, tileHeight;
    private boolean setUp = false;
    private final String leftStandPath = "data/bluey_standing_left.png";
    private final float SCALE = 0.75f;
    
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
       
    /**
     * Constructor. The object is associated with a Level and is
     * positioned as near to Tile t on it as possible.
     */ 
    public Bluey(Level l, Tile t)
    {
        if(!setUp)
        {
            tileHeight = t.tileHeight;
            createAnimations();
            setUp = true;
        }
        level = l;
        xPos = t.xPos;
        yPos = t.yPos;
        yPos -= (AnimCreator.getCurrentFrameRect(this).getHeight() - 
                 t.tileHeight);
        yPos--;
        System.out.println("new Bluey enemy at " + xPos + ", " + yPos);
    }
    
    public int getHealth()
    {
        return health;
    }
    
    private void createAnimations()
    {
        System.out.println("bluey -> creating animations");
        
        Image leftStand1 = AnimCreator.loadImage(leftStandPath);
        Image rightStand1 = leftStand1.getFlippedCopy(true, false);        
        
        // standing anims
        Image[] leftStandImages = { leftStand1 };
        Image[] rightStandImages = { rightStand1 };  
              
        leftStandAnim = new Animation(leftStandImages, 
                                      Actor.ANIM_DURATION, false);
        rightStandAnim = new Animation(rightStandImages,
                                      Actor.ANIM_DURATION, false);          
        // walking anims
        Image[] leftWalkImages = AnimCreator.getImagesFromPaths(
                               leftWalkPaths).toArray(rightStandImages);
        Image[] rightWalkImages = AnimCreator
                            .getHorizontallyFlippedCopy(leftWalkImages)
                                            .toArray(rightStandImages);
        leftWalkAnim = new Animation(leftWalkImages, 
                                      Actor.ANIM_DURATION, false);
        rightWalkAnim = new Animation(rightWalkImages,
                                       Actor.ANIM_DURATION, false);
        facing = Direction.LEFT;
        currentAnim = leftStandAnim;
    }
    
    public float getTileHeight()
    {
        return tileHeight;
    }
     
    public void update()
    {
        //System.out.println("updating bluey");
        updatePosition();
        updateProjectiles();
    }
    private void updatePosition()
    {
        applyGravity();
    }
    
    public void applyGravity()
    {
        
    }
    
    
    @Override
    public boolean canSeePlayer(float playerX, float playerY)
    {
        return false;
    }
    
    public void flee()
    {
        
    }
    
    public void attack()
    {
        
    }
    
    public void die()
    {
        
    }

    private void updateProjectiles()
    {
        
    }
    
    public Animation getCurrentAnim()
    {
        return currentAnim;
    }
    
    public void draw(Graphics g)
    {
        //System.out.println("drawing Bluey: " + getX() + ", " + getY());
        g.drawAnimation(currentAnim, getX(), getY());
        //g.draw(AnimCreator.getCurrentFrameRect(this));
        drawProjectiles(g);
    }
    
    private void drawProjectiles(Graphics g)
    {
        
    }
    
    public float getX(){ return xPos; }   
    public float getY(){ return yPos; } 
}
