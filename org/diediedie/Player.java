package org.diediedie;

import java.io.*;
import java.util.*;
import java.lang.Math.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

/**
 * ..where *YOU* are the HERO! (aka 'twat')
 */ 
public class Player implements InputProviderListener
{
    private boolean setUp = false, canJump = false;
    public String name;    
    private float accelX = 0f, ACCEL_RATE = 0.05f, maxYSpeed = 20.5f,
                  MAX_ACCEL = 4f, moveSpeed = 0.9f, jumpSpeed = -5.5f;
                  
    private int health, bowCharge = 0;
    private final int MAX_CHARGE = 50;
    
    public final float TERMINAL_VEL = 10;
    final float INCR = 0.01f;
    // vars indicating vertical and horizontal collisions
    boolean yCollision = false, xCollision = false;
    
    // Movement
    private Command jump;
    private Command left; 
    private Command right;    
    
    private Arrow currentArrow = null; 
    
    private boolean leftMoveDown = false, rightMoveDown = false,
                    arrowCharging = false;
    
    // current position vars 
    private float xPos, yPos, xSpeed, ySpeed;
    private Direction facing = Direction.LEFT; 
    
    private int mouseX, mouseY;
    
    // running: indicates the user is holding a directional button
    private boolean running = false;
    
    // animation vars
    public static final int ANIM_DURATION = 100; 
    
    // fire an arrow - which button?
    private int BOW_BUTTON = Input.MOUSE_LEFT_BUTTON;
    
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
     * Links the game's InputProvider to the Player obkect
     */ 
    protected void associateInputProvider(InputProvider prov, Input in)
    {
        prov.addListener(this); 
        
        jump = new BasicCommand("jump");
        left = new BasicCommand("left");
        right = new BasicCommand("right");
        
        prov.bindCommand(new KeyControl(Input.KEY_LEFT), left);
        prov.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
        prov.bindCommand(new KeyControl(Input.KEY_UP), jump);
        
        in.addMouseListener(new MouseListener()
        {
            public void	mousePressed(int button, int x, int y) 
            {
                if(button == BOW_BUTTON)
                {
                    //System.out.println("left mouse Pressed");    
                    readyArrow();
                }
            }
            public void	mouseReleased(int button, int x, int y)
            {
                if(button == BOW_BUTTON)
                {
                    //System.out.println("left mouse released");
                    releaseArrow(x, y);
                }
            }
            public boolean isAcceptingInput() 
            {
                return true;
            } 
            public void	mouseMoved(int oldx, int oldy, int newx, int newy) 
            {
                // keep track of the mouse position
                mouseX = newx;
                mouseY = newy;
    
            }
            public void	mouseWheelMoved(int change) {}            
            
            // boiler plate code
            public void	mouseClicked(int button, int x, int y, int clicks) {}
            public void	mouseDragged(int oldx, int oldy, int newx, int newy) {} 
            public void	inputEnded(){} 
            public void	inputStarted(){} 
            public void setInput(Input input) {} 
        });
        
    }
    
    /**
     * Defines the routines carried out for when each command is 
     * released.
     */ 
    public void controlReleased(Command com)
    {
        //System.out.println("released: " + com);
        
        if(com.equals(left))
        {
            leftMoveDown = false;
        }
        else if(com.equals(right))
        {
            rightMoveDown = false;
        }
        
        // both released now?
        if(!leftMoveDown && !rightMoveDown)
        {
            running = false;
        }
        /*else if(com.equals(pullArrow))
        {
            releaseArrow();
        }*/
    }
    
    
    
    /**
     * Defines the routines carried out for when each command is pressed.
     */ 
    public void controlPressed(Command com)
    {
        System.out.println("pressed: " + com);
        
        
        
        
        // left/right movement
        
        /*if(arrowCharging)
        {
            System.out.println("can't move, charging arrow");
            return;
        }*/
        
        if(com.equals(left))
        {
            leftMoveDown = true;
            setDirection(Direction.LEFT);
            running = true;
        }
        else if(com.equals(right))
        {
            rightMoveDown = true;
            setDirection(Direction.RIGHT);
            running = true;
        }
        // jumping
        else if(com.equals(jump))
        {
            jump();
        }
    }
    
    /*
     * Starts the player aiming an arrow towards the
     * Mouse Pointer
     */ 
    private void readyArrow()
    {
        System.out.println("Readying arrow");
        currentArrow = new Arrow(xPos, yPos);
        
        arrowCharging = true;
    }
    
    private void chargeArrow()
    {
        if(bowCharge < MAX_CHARGE)
        {
            bowCharge++;
        }
        currentArrow.updateAiming(mouseX, mouseY);
        currentArrow.setPosition(getHoldingArrowX(), getHoldingArrowY());
        //System.out.println("charge==" + bowCharge);
    }
    
    // returns the x position of the arrow when being held by the player
    private float getHoldingArrowX()
    {
        return xPos + 10;
    } 
    // returns the y position of the arrow when being held by the player
    private float getHoldingArrowY()
    {
        return yPos + 10;
    } 
    
    /*
     * Fires an Arrow from the Player's position towards the X / Y
     * coordinates.
     */ 
    private void releaseArrow(int mouseX, int mouseY)
    {
        arrowCharging = false;
        System.out.println("released arrow ");
        
        
        
        bowCharge = 0;
    }
    private void setDirection(Direction dir)
    {
        if(facing != dir)
        {
            resetAccelX();
            facing = dir;
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
     * Adjusts the player's walking speed
     */ 
    public void move(Direction dir)
    {   
        if(arrowCharging)
        {
            System.out.println("can't move, charging arrow");
            return;
        }
        // player directional movements     
        if(dir.equals(Direction.RIGHT))
        {
            accelerate();
            currentAnimation = rightWalk;
            xSpeed = moveSpeed + accelX;
        }
        else if(dir.equals(Direction.LEFT))
        {
            accelerate();
            currentAnimation = leftWalk;
            xSpeed = -(moveSpeed + accelX);
        }
    }
    
    private void accelerate()
    {
        accelX += ACCEL_RATE;
        if(accelX > MAX_ACCEL)
        {
            accelX = MAX_ACCEL;
        }
    }
    
    private void applyFriction()
    {
        xSpeed *= level.groundFric;
    }
    
    public void update()
    {
        applyGravity();
        applyFriction();
                
        if(running)
        {
            move(facing);
        }
        else
        {
            decelerate();
        }
        
        // arrow
        
        if(arrowCharging)
        {
            chargeArrow();
        }
        
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
                // fell 
                canJump = true;
            }
            ySpeed = 0;
            yPos = oldY;
            
            alignToObstacle();
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
            resetAccelX();
        }        
        
        if(accelX == 0)
        {
            setStandingAnim();
        }
       // printSpeed();
       // printPosition();
    }
    
    
    /*
     * Closely aligns the Player to an object following a collision.
     * This is done to stop it looking like the player is 'hovering'
     * before landing due to having a high falling speed.
     */ 
    private void alignToObstacle()
    {
        // finally, put the Player as close to the obstacle as possible
        while(!level.collides(getCurrentFrameRect()))
        {
            // here 'canJump' is used to discern the direction of the
            // collision; i.e. a 'true' value indicates (hopefully) 
            // that the player *fell* into this collision rather than
            // headbutted it... so to speak.
            
            if(canJump)
            {
                yPos += INCR;
            }
            else
            {
                yPos -= INCR;
            }
            //System.out.println("moving... " + yPos);
        }
        if(canJump)
        {
            yPos -= INCR;
        }
        else
        {
            yPos += INCR;
        }
    }
    
    public void printSpeed()
    {
        System.out.println("accelX==" + accelX + ", " + "xSpeed==" 
                            + xSpeed + ", ySpeed==" + ySpeed);
    }
    
    public void printPosition()
    {
        System.out.println("xPos==" + xPos + ", yPos==" + yPos); 
    }
    
    public void jump()
    {
        if(canJump)
        {
            System.out.println("jump!");
            ySpeed = jumpSpeed;
            //accelY = jumpSpeed;
            canJump = false;
        }
    }
    
    private void decelerate()
    {
        accelX -= ACCEL_RATE;
        
        if(accelX < 0)
        {
            resetAccelX();
        }
    }
    
    private void resetAccelX()
    {
        accelX = 0;
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
        // draw arrows
        
        if(currentArrow != null)
        {
            currentArrow.draw(g);    
        }
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
