package org.diediedie;

import java.io.*;
import java.util.*;
import java.lang.Math.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

/**
 * ..where *YOU* are the HERO! (aka 'twat')
 */ 
public class Player
{
    private boolean setUp = false, canJump = false;
    public String name;    
    private float accelX = 0f, ACCEL_RATE = 0.04f, maxYSpeed = 8.5f,
                  MAX_ACCEL = 4f, jumpSpeed = -8.5f, moveSpeed = 0.9f;
    private int health;
    // separate vars indicating vertical and horizontal collisions
    boolean yCollision = false, xCollision = false;

    
    // current position vars 
    private float xPos, yPos, xSpeed, ySpeed;
    private Direction facing = Direction.LEFT; 
    
    // animation vars
    public static final int ANIM_DURATION = 100; 
    private String[] leftWalkPaths = 
    {
        "data/STICKMAN_LEFT_WALK_1.png", 
        "data/STICKMAN_LEFT_WALK_2.png",
        "data/STICKMAN_LEFT_WALK_3.png", 
        "data/STICKMAN_LEFT_WALK_4.png",  
        "data/STICKMAN_LEFT_WALK_5.png", 
        "data/STICKMAN_LEFT_WALK_6.png"
    };
    private String[] rightWalkPaths =
    {
        "data/STICKMAN_RIGHT_WALK_1.png", 
        "data/STICKMAN_RIGHT_WALK_2.png",
        "data/STICKMAN_RIGHT_WALK_3.png", 
        "data/STICKMAN_RIGHT_WALK_4.png",  
        "data/STICKMAN_RIGHT_WALK_5.png", 
        "data/STICKMAN_RIGHT_WALK_6.png"
    }; 
    private String[] leftStandPaths = {
        "data/STICKMAN_LEFT_STAND.png"        
    };
    private String[] rightStandPaths = {
        "data/STICKMAN_RIGHT_STAND.png"        
    };
    private Animation leftWalk, rightWalk, leftStand, rightStand,
                      currentAnimation;
                          
    // associated level for collision / item collection reference
    private Level level = null;
            
    /**
     * Initialises a new Player at the given position.
     */ 
    public Player(Level level)
    {
        this.level = level;
        this.xPos = level.playerX;
        this.yPos = level.playerY;
        
        System.out.println("Player is on level " + level);
        
        if(!setUp)
        {            
            initAnim();   
            setUp = true;
        }
    }
    
    
    
    /**
     * Returns the x position
     */ 
    public float getX() { return xPos; }
    /**
     * Returns the y position
     */ 
    public float getY() { return yPos; } 
    
    
    /**
     * Sets the Player's current Animation var to 'standing'. 
     */ 
    public void setStandingAnim()
    {
        if(facing.equals(Direction.RIGHT))
        {
            currentAnimation = rightStand;
        }
        else if(facing.equals(Direction.LEFT))
        {
            currentAnimation = leftStand;   
        }
        else 
        {
            throw new IllegalStateException();
        }
    }
    
      
    
    /**
     * Adjusts the player's speed
     */ 
    public boolean move(Direction dir)
    {   
        if(facing != dir)
        {
            accelX = 0;  
            facing = dir;   
        }
        // player directional movements     
        if(dir.equals(Direction.RIGHT))
        {
            if(accelX < MAX_ACCEL)
            {
                accelX += ACCEL_RATE;
            }
            currentAnimation = rightWalk;
                        
            xSpeed = moveSpeed + accelX;
        }
        else if(dir.equals(Direction.LEFT))
        {
            if(accelX < MAX_ACCEL)
            {
                accelX += ACCEL_RATE;
            }
            currentAnimation = leftWalk;
            xSpeed = -(moveSpeed + accelX);
        }
        
        return true;
    }
    
    public void decreaseAcceleration()
    {
        if(accelX > 0)
        {
            accelX -= ACCEL_RATE;
        }
    }
    
    private void applyFriction()
    {
        xSpeed *= level.groundFric;
    }
    public void update()
    {
        
        
        //save old coordinates in case the new positions == collision
        final float oldX = xPos;
        final float oldY = yPos;
        //
        // test player at new position
        //
        
        // test vertical change
        
        yPos += ySpeed;
        
        if(level.collides(getCurrentFrameRect()))
        {
            //System.out.println("vertical collision");
            yCollision = true;
            
            if(yPos >= oldY)
            {
                canJump = true;
            }
            yPos = oldY;
            ySpeed = 0;
        } 
        else
        {
            yCollision = false;
            canJump = false;
        }
        
        // test horizontal 
        
        xPos += xSpeed;
        
        if(level.collides(getCurrentFrameRect()))
        {
            xPos = oldX;
            xSpeed = 0;
            accelX = 0;
        }
        
        applyGravity();
        applyFriction();
        
        /*System.out.println("accelX==" + accelX + ", " + "xSpeed==" 
                            + xSpeed + ", xPos==" + xPos);*/
    }
    
   
    
    public void jump()
    {
        if(canJump)
        {
            ySpeed = jumpSpeed;
            canJump = false;
        }
    }
    
    
    
    /**
     * Applies gravity to the player's position.
     */ 
    public void applyGravity()
    {
        if(ySpeed < maxYSpeed)
        {
            ySpeed += level.gravity;
        }
    }
    /**
     * Draw method. Public due to implementation requirement.
     */ 
    public void draw(Graphics g)
    {
        g.drawAnimation(currentAnim(), getX(), getY());
        //g.draw(getCurrentFrameRect());
    }
    
    public void stopAnim()
    {
        currentAnimation.stop();
    }
    
    public void startAnim()
    {
        currentAnimation.start();
    }
    
  
    
    /**
     * Returns the currently set animation.
     */ 
    public Animation currentAnim()
    {
        return currentAnimation;
    }
        
    /**
     * Returns the current animation frame's rectangular bounding box.
     */ 
    public Rectangle getCurrentFrameRect()
    {
        Image img = currentAnim().getCurrentFrame();
        
        return new Rectangle(getX(), getY(), img.getWidth(), 
                             img.getHeight());
    }
    
    
    
    /*
     * Loads and inits the Player's Animations. 
     */ 
    private void initAnim()
    {
        //final boolean autoUpdate = false;
        final boolean autoUpdate = true;
        leftWalk = createAnim(ANIM_DURATION, autoUpdate, 
                              leftWalkPaths);
        rightWalk = createAnim(ANIM_DURATION, autoUpdate, 
                               rightWalkPaths);           
        rightStand = createAnim(ANIM_DURATION, autoUpdate,
                                rightStandPaths);
        leftStand = createAnim(ANIM_DURATION, autoUpdate,
                               leftStandPaths);                
        currentAnimation = leftStand;
        //facing = Direction.LEFT;
        
        // get initial direction from the level
        facing = level.playerFacing;
    }
    
    /*
     * Utility function that creates and returns an animation in which
     * each frame lasts the amount set in 'duration', based upon 
     * the list of file paths given. If flip is true, flip the images 
     * on load. 
     */ 
    private Animation createAnim(int duration, boolean autoUpdate, 
                                 String... paths)
    {
        List<Image> images = getImagesFromPaths(paths);
        Image[] imgArr = new Image[images.size()];
        images.toArray(imgArr);
        return new Animation(imgArr, duration, autoUpdate);
    }    
    
    /*
     * Returns an array of Images from a List of paths to image files. 
     */
    private List<Image> getImagesFromPaths(String... paths)
                                                 
    {
        List<Image> images = new ArrayList<Image>();
        for(String p : paths)
        {
            try
            {
                images.add(new Image(new FileInputStream(
                                            new File(p)), p, false));               
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch(SlickException e)
            {
                e.printStackTrace();
            }
        }
        return images;
    }



}
