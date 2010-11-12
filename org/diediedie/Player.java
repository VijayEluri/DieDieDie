/*
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */
 
package org.diediedie.actors;
import org.diediedie.Level;
import org.diediedie.actors.AnimCreator;
import org.diediedie.actors.Collider;
import org.diediedie.actors.Aligner;
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
 * ..where *YOU* are the HERO!
 */ 
public class Player implements Actor, InputProviderListener 
{    
    private boolean setUp = false, canJump = false;
    final boolean autoUpdate = true;
     
    private float accelX = 0f, bowCharge = 0, oldX, oldY, bowX, bowY, 
                  bowYCurrentOffset, bowXCurrentOffset;
                  
    public final float MAX_CHARGE = 19f, CHARGE_INCR = 0.3f, 
                       BOW_Y_OFFSET_NORMAL = -2f, 
                       BOW_Y_OFFSET_AIM_UP = -10, MAX_Y_SPEED = 20.5f,
                       MAX_X_SPEED = 2.5f, JUMP_SPEED = -5.5f,
                       BOW_Y_OFFSET_AIM_DOWN = 6, ARROW_Y_OFFSET = 15,
                       MOVE_SPEED = 0.9f, MAX_ACCEL = 4f, 
                       ACCEL_RATE = 0.03f;
                        
    private final int MAX_HEALTH = 20, BOW_AIM_UP_TRIGGER = 50,
                      BOW_AIM_DOWN_TRIGGER = 110, BOW_X_OFFSET = 5,
                      BOW_ANGLE_OFFSET = 90; 
                      
    private int health = MAX_HEALTH, arrowCount = 0, mouseX, mouseY,
                BOW_BUTTON = Input.MOUSE_LEFT_BUTTON;   
                    
    // Movement
    private Command jump;
    private Command left; 
    private Command right;    
    
    
    private Arrow currentArrow = null; 
    private List<Arrow> firedArrows = Collections.synchronizedList(new
                                                    ArrayList<Arrow>());
    
    private boolean leftMoveDown = false, rightMoveDown = false,
                    isChargingArrow = false, isFiringArrow = false;
    
    private float xPos, yPos, xSpeed, ySpeed;
    private Direction facing = Direction.LEFT, moving = Direction.LEFT; 
        
    // running: indicates the user is holding a directional button
    private boolean running = false;
        
    private final String bowLeftPath = "data/STICKMAN_BOW_LEFT.png";
    
    private final String[] leftWalkPaths = 
    {
        "data/STICKMAN_LEFT_WALK_1.png", 
        "data/STICKMAN_LEFT_WALK_2.png",
        "data/STICKMAN_LEFT_WALK_3.png", 
        "data/STICKMAN_LEFT_WALK_4.png",  
        "data/STICKMAN_LEFT_WALK_5.png", 
        "data/STICKMAN_LEFT_WALK_6.png"
    };
    
    private final String[] rightWalkPaths =
    {
        "data/STICKMAN_RIGHT_WALK_1.png", 
        "data/STICKMAN_RIGHT_WALK_2.png",
        "data/STICKMAN_RIGHT_WALK_3.png", 
        "data/STICKMAN_RIGHT_WALK_4.png",  
        "data/STICKMAN_RIGHT_WALK_5.png", 
        "data/STICKMAN_RIGHT_WALK_6.png"
    }; 
    
    private String[] leftStandPaths =
    {
        "data/STICKMAN_LEFT_STAND.png"        
    };
    
    private String[] rightStandPaths = 
    {
        "data/STICKMAN_RIGHT_STAND.png"        
    };
    private Animation leftWalk, rightWalk, leftStand, rightStand,
                      currentAnim;
    private Image bowLeft, bowRight, currentBow;
    
    // associated level for collision / item collection reference
    private Level level = null;
    
    /**
     * Constructs the Player at the given position.
     */ 
    public Player(Level l)
    {
        setLevel(l);
        setUpStartPosition();
        if(!setUp)
        {            
            initAnim();   
            initBow();
            setUp = true;
        }
    }    
    
    @Override
    public void setJump(boolean b)
    {
        canJump = b;
    }
    
    @Override
    public void setLevel(Level l)
    {
        level = l;
        level.associatePlayer(this);
    }

    /*
     * Places the Player at the designated start position according to
     * the Level associated with it. 
     */ 
    private void setUpStartPosition()
    {
        this.xPos = level.getPlayerTile().xPos;
        this.yPos = level.getPlayerTile().yPos - level.getPlayerTile()
                                                           .tileHeight;
        yPos--;
        
        System.out.println("Player is on level " + level + 
                           " at position x: " + xPos + ", y: " + yPos);              
    }
    
    private void initBow()
    {
          bowLeft = AnimCreator.loadImage(bowLeftPath);
          bowRight = bowLeft.getFlippedCopy(true, false);
          currentBow = bowLeft;
    }    
    
    @Override
    public float getMoveSpeed()
    {
        return MOVE_SPEED;
    }
    
    /**
     * Links the game's InputProvider to the Player obkect
     */ 
    public void associateInputProvider(InputProvider prov, Input in)
    {
        prov.addListener(this); 
        
        jump = new BasicCommand("jump");
        left = new BasicCommand("left");
        right = new BasicCommand("right");
        
        prov.bindCommand(new KeyControl(Input.KEY_A), left);
        prov.bindCommand(new KeyControl(Input.KEY_D), right);
        prov.bindCommand(new KeyControl(Input.KEY_W), jump);
        
        in.addMouseListener(new MouseListener()
        {
            public void	mousePressed(int button, int x, int y) 
            {
                if(button == BOW_BUTTON)
                {
                    mouseX = x;
                    mouseY = y;  
                    readyArrow();
                }
            }
            public void	mouseReleased(int button, int x, int y)
            {
                if(button == BOW_BUTTON)
                {
                    //System.out.println("left mouse released");
                    releaseArrow();
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
            
            // boiler plate code. yeah. sorry.
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
            //leftMoveDown = false;
            if(moving.equals(Direction.LEFT))
            {
                running = false;   
            }
        }
        else if(com.equals(right))
        {
            //rightMoveDown = false;
            if(moving.equals(Direction.RIGHT))
            {
                running = false;   
            }
        }
    }
    
    /**
     * Defines the routines carried out for when each command is pressed.
     */ 
    public void controlPressed(Command com)
    {
        //System.out.println("pressed: " + com);
                
        if(com.equals(left))
        {
            //leftMoveDown = true;
            setMovingDir(Direction.LEFT);
            running = true;
        }
        else if(com.equals(right))
        {
            //rightMoveDown = true; 
            setMovingDir(Direction.RIGHT);
            running = true;
        }
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
        currentArrow = new Arrow(xPos, yPos, level, mouseX, mouseY);
        isChargingArrow = true;
    }
    
    /*
     * Charges the arrow and updates the Direction the player faces.
     */ 
    private void chargeArrow()
    {
        if(bowCharge < MAX_CHARGE)
        {
            bowCharge += CHARGE_INCR;
        }
        //System.out.println("bowCharge " + bowCharge);
        currentArrow.updateAiming(mouseX, mouseY);

        if(currentArrow.getAngle() >= 0)
        {
            currentBow = bowRight;   
            setFacingDir(Direction.RIGHT);
            bowX = xPos + BOW_X_OFFSET + (getCurrentFrameWidth() / 2);
            currentBow.setRotation(currentArrow.getAngle() 
                                   - BOW_ANGLE_OFFSET);
        }
        else
        {
            setFacingDir(Direction.LEFT);
            currentBow = bowLeft;   
            bowX = xPos - BOW_X_OFFSET;
            currentBow.setRotation(currentArrow.getAngle() 
                                    + BOW_ANGLE_OFFSET);
        }
        updateBowPosition();
        currentArrow.setPosition(getHoldingArrowX(), getHoldingArrowY());
    }
    
    @Override
    public int getHealth()
    {
        return health;
    }
    
    @Override
    public float getJumpSpeed()
    {
        return JUMP_SPEED;
    }
    
    @Override
    public void die()
    {
        System.out.println("Player is dead!");
    }
    
    private float getMiddleXPos()
    {
        final float midX = xPos + (getCurrentFrameWidth() / 2);
        //System.out.println("xPos: " + xPos + ", midX: " + midX);
        return midX;
    }
    
    private void updateBowPosition()
    {
        final float ANGLE = Math.abs(currentArrow.getAngle());
        
        // set the bow's offset depending on the angle
        if(ANGLE < BOW_AIM_UP_TRIGGER)
        {
            bowYCurrentOffset = BOW_Y_OFFSET_AIM_UP;           
        }
        else if(ANGLE < BOW_AIM_DOWN_TRIGGER)
        {
            bowYCurrentOffset = BOW_Y_OFFSET_NORMAL;   
        }
        else
        {
            bowYCurrentOffset = BOW_Y_OFFSET_AIM_DOWN;
        }
        bowY = yPos + bowYCurrentOffset;   
    }
    
    // returns the x position of the arrow when being held by the player
    private float getHoldingArrowX()
    {
        return xPos + getCurrentFrameWidth() / 2;
    } 
    
    public float getCurrentFrameWidth()
    {
        return AnimCreator.getCurrentFrameRect(this).getWidth();
    }
    
    /*
     * Returns the y position of the arrow when being held by the player
     */
    private float getHoldingArrowY()
    {
        return yPos + ARROW_Y_OFFSET;
    } 
    
    /*
     * Fires an Arrow from the Player's position towards the X / Y
     * coordinates.
     */ 
    private void releaseArrow()
    {
        isChargingArrow = false;
        isFiringArrow = true;
        
        currentArrow.release(bowCharge);
        firedArrows.add(currentArrow);
        currentArrow = null;
        
        System.out.println("released arrow, power " + bowCharge);        
        bowCharge = 0;
    }
    
    /*
     * Sets the direction the Player is facing
     */ 
    private void setFacingDir(Direction dir)
    {
        facing = dir;
        if(!running)
        {
            setStandingAnim();
        }
    }
    
    /*
     * Sets the direction the Player is moving
     */ 
    private void setMovingDir(Direction dir)
    {
        if(moving != dir)
        {
            resetAccelX();
        }
        moving = dir;
        setFacingDir(dir);
        //System.out.println("setMovingDir: " + dir);
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
    private void setStandingAnim()
    {
        if(facing.equals(Direction.RIGHT))
        {
            currentAnim = rightStand;
        }
        else if(facing.equals(Direction.LEFT))
        {
            currentAnim = leftStand;   
        }
        else throw new IllegalStateException(
                            "standing dir neither left or right");
    }
    
    @Override
    public void applySpeed(Direction dir)
    {
        if(dir.equals(Direction.RIGHT))
        {
            accelerate();
            currentAnim = rightWalk;
            xSpeed = (MOVE_SPEED + accelX);
        }
        else if(dir.equals(Direction.LEFT))
        {
            accelerate();
            currentAnim = leftWalk;
            xSpeed = -(MOVE_SPEED + accelX);
        }          
    }
    
    /*
     * Increases walk speed.
     */ 
    private void accelerate()
    {
        if(accelX < MAX_ACCEL)
        {
            accelX += ACCEL_RATE;
        }
    }
    
    /*
     * Updates the position / existence of previously fired Arrows. 
     */ 
    private void updateFiredArrows()
    {
        Iterator<Arrow> it = firedArrows.iterator();
        
        while(it.hasNext())
        {
            Arrow a = it.next();
            
            a.updateSpeed();
            a.updatePosition();
            
            /*if(!a.isFlying())
            {
                it.remove();
            }*/
        }
    }
    
    /*
     * Applies friction to the player
     */ 
    private void applyFriction()
    {
        xSpeed *= level.FRICTION;
    }
    
    @Override
    public void update()
    {
        Mover.applyGravity(this);
        applyFriction();
                
        if(running)
        {
            applySpeed(moving);
        }
        else
        {
            decelerate();
        }
        
        // Update Arrow information depending on state
        if(isChargingArrow)
        {
            chargeArrow();
        }
        updateFiredArrows();
        Mover.move(this);
    }
    
    @Override
    public Level getLevel()
    {
        return level;
    }
    
    @Override
    public Animation getCurrentAnim()
    {
        return currentAnim;
    }

    /*
     * Closely aligns the Player to an object following a collision.
     * This is done to stop it looking like the player is 'hovering'
     * before landing due to having a high falling speed.
     */ 
    
    @Override
    public boolean canJump()
    {
        return canJump;
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
    
    @Override
    public void jump()
    {
        if(canJump())
        {
            ySpeed = JUMP_SPEED;
            canJump = false;
        }
    }
    
    @Override
    public void setX(float x)
    {
        xPos = x;
    }
    
    @Override
    public void setY(float y)
    {
        yPos = y;
    }
    
    private void decelerate()
    {
        accelX -= ACCEL_RATE;
        if(accelX < 0)
        {
            resetAccelX();
        }
    }
    
    @Override
    public void resetAccelY()
    {
        // do nothing for now
    }
    
    @Override
    public void resetAccelX()
    {
        accelX = 0;
        setStandingAnim();
    }
    
    @Override
    public Direction getFacing()
    {
        return facing;
    }

    
    
    @Override
    public float getYSpeed()
    {
        return ySpeed;
    }
    
    @Override
    public float getXSpeed()
    {
        return xSpeed;
    }
    
    @Override
    public void setYSpeed(float f)
    {
        ySpeed = f;
    }
    
    @Override
    public void setXSpeed(float f)
    {
        xSpeed = f;
    }
    
    @Override
    public float getMaxFallSpeed()
    {
        return MAX_Y_SPEED;
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.drawAnimation(currentAnim, getX(), getY());
        drawArrows(g);
        
        if(isChargingArrow)
        {
            g.drawImage(currentBow, bowX, bowY);
        }        
    }
    
    /*
     * Draw all the arrows that still exist 
     */
    private void drawArrows(Graphics g)
    {
        if(currentArrow != null)
        {
            currentArrow.draw(g);    
        }
        for(Arrow a : firedArrows)
        {
            a.draw(g);
        }
    }  
    
    /*
     * Loads and inits the Player's Animations. 
     */ 
    private void initAnim()
    {
        
        leftWalk = AnimCreator.createAnimFromPaths(Actor.ANIM_DURATION, 
                                    autoUpdate, leftWalkPaths);
        rightWalk = AnimCreator.createAnimFromPaths(Actor.ANIM_DURATION, 
                                    autoUpdate, rightWalkPaths);           
        rightStand = AnimCreator.createAnimFromPaths(Actor.ANIM_DURATION, 
                                    autoUpdate, rightStandPaths);
        leftStand = AnimCreator.createAnimFromPaths(Actor.ANIM_DURATION, 
                                    autoUpdate, leftStandPaths);                
        currentAnim = leftStand;
        
        // get initial direction from the level
        facing = level.playerFacing;
    }    
}
