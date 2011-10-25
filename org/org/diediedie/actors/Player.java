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
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.CollideMask;
import org.diediedie.actors.tools.MaskedAnimation;
import org.diediedie.actors.tools.ObjectMover;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.diediedie.Level;
import org.diediedie.PlayerLayer;
import org.diediedie.Tile;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Arrow;
import org.diediedie.actors.tools.Direction;

/**
 * The main Player Character. 
 * 
 * Unlike most LevelObjects, (s)he exists on a separate
 * Tiled Layer (not an ObjectLayer)
 */ 
public class Player extends BaseLevelObject implements Actor, 
													   InputProviderListener 
{
	// The following constants are to be interpreted as
    // serving the purpose 'how much to <mult/div> X by' and
    // are used for player speed creation
	private static final float HEIGHT_WALK_DIVIDER = 4,
	                           ACCEL_WALK_SPEED_DIVIDER = 10,
	                           MAX_RUN_WALK_MULTIPLIER = 2,
	                           ACCEL_MAX_RATE_MULTIPLIER = 2,
					           INIT_JUMP_WALK_SPEED_MULTIPLIER = 0.2f,
					           MAX_JUMP_INIT_JUMP_MULTIPLIER = 2,
							   MAX_FALL_MAX_JUMP_MULTIPLIER = 2.5f,
							   MAX_CHARGE = 20.34f, 
		                       CHARGE_INCR = 0.4f, 
		                       BOW_Y_OFFSET_NORMAL = -2f, 
		                       BOW_Y_OFFSET_AIM_UP = -10, 
		                       BOW_Y_OFFSET_AIM_DOWN = 6, 
		                       ARROW_Y_OFFSET = 15;;
	
	private boolean setUp = false, 
			        canJump = false,
                    userHoldingLeftXOrRight = false,
	        	    isChargingArrow = false,
	        	    isJumping = false,
	    			jumpKeyDown = false,
	    			outOfBounds = false;
    
   /*
    * Player speed is calculated based upon the player's
    * size (and then adjusted during the game where necessary)
    */
    private float xSpeed, 
    			  ySpeed,
                  accelX = 0f, 
    		      bowCharge = 0, 
    		      bowX, 
    		      bowY, 
                  bowYCurrentOffset,
                  accelerationRate, 
				  maxAccelRate, 
				  initialJumpSpeed;

    private final int MAX_HEALTH = 20, 
                      BOW_AIM_UP_TRIGGER = 50,
                      BOW_AIM_DOWN_TRIGGER = 110, 
                      BOW_X_OFFSET = 5,
                      BOW_ANGLE_OFFSET = 90,
                      COLLISION_WIDTH_DIV = 4;
                      
    private int health = MAX_HEALTH, 
                mouseX, 
                mouseY,
                BOW_BUTTON = Input.MOUSE_LEFT_BUTTON,
                xLeftOffsetCollisionBox,
                height, 
                width, 
                minReleaseCharge = 7;
     
    private Command jump,
                    left,
                    right, 
                    playNote1, 
                    run;
    
    private Rectangle collisionBox;
    
    private Arrow currentArrow = null; 
    private List<Arrow> firedArrows = Collections.synchronizedList(new
                                                    ArrayList<Arrow>());

    // walk speed and max run speeds are calculated using the
    // character's height
    private float walkSpeed;
	private float maxRunSpeed;
    
    private Direction facing = Direction.LEFT, 
    		          moving = Direction.LEFT; 
        
        
    private final String bowLeftPath = "data/player_images/STICKMAN_BOW_LEFT.png";
    
    private final String[] leftWalkPaths = {
    	"data/player_images/1.png",	
    	"data/player_images/2.png",
    	"data/player_images/3.png",
    	"data/player_images/4.png",
    	"data/player_images/5.png",
    	"data/player_images/6.png",
    	"data/player_images/7.png",
    	"data/player_images/8.png",
    };
    
    
    private String[] leftStandPaths =
    {
  		"data/player_images/standing.png"        
    };
    
    private Animation leftWalk, 
    				  rightWalk,
    				  leftStand,
    				  rightStand,
    				  currentAnim;
    
    private Image bowLeft, 
    			  bowRight, 
    			  currentBow;
    
    private Tile startTile;
	private final int COLLISION_BOX_H_OFFSET = 1;
	private int collisionBoxHeight;
	
	private boolean hasInstrument;
    private Instrument currentInstrument = null;
	private float maxJumpSpeed, jumpIncr, maxFallSpeed;
	
	private boolean playNote1KeyDown = false,
					fastMoveMode = false;

        
    /**
     * Constructs the Player at the given position.
     */ 
    public Player(Level l, Tile t)
    {
    	startTile = t;
    	setLevel(l);
        setUpStartPosition();
        
        if(!setUp)
        {            
            initAnim();   
            setUpCollisionBox();
            initBow();
            setUpPlayerSpeed();
            setUp = true;
        }
        
    }    
    
    
    /*
     * The Player's size may change dynamically between or,
     * possibly during, a level. 
     * 
     * This method can be used to recalibrate the Player's 
     * possible top speed and movement rates after a speed
     * change.
     */
    private void setUpPlayerSpeed() 
    {
    	calculateMovementSpeeds();
    	
    	System.out.println("height : " + height);
    	System.out.println("\nsetupPlayerSpeed");
    	System.out.println("==============");
    	System.out.println("  walkSpeed        : " + walkSpeed);
    	System.out.println("  maxRunSpeed      : " + maxRunSpeed);
    	System.out.println("  accelerationRate : " + accelerationRate);
    	System.out.println("  maxAccelRate     : " + maxAccelRate);
    	System.out.println("  initialJumpSpeed : " + initialJumpSpeed);
    	System.out.println("  maxJumpSpeed     : " + maxJumpSpeed);
    	System.out.println("  jumpIncr         : " + jumpIncr);
    	System.out.println("  maxFallSpeed     : " + maxFallSpeed);
    	
    	//System.exit(-1);
	}

    private void calculateMovementSpeeds()
    {
    	walkSpeed = height / HEIGHT_WALK_DIVIDER;
    	maxRunSpeed = walkSpeed * MAX_RUN_WALK_MULTIPLIER;
    	accelerationRate = walkSpeed / ACCEL_WALK_SPEED_DIVIDER;
    	maxAccelRate = accelerationRate * ACCEL_MAX_RATE_MULTIPLIER;
    	initialJumpSpeed = -(walkSpeed * INIT_JUMP_WALK_SPEED_MULTIPLIER);
    	maxJumpSpeed = (initialJumpSpeed * MAX_JUMP_INIT_JUMP_MULTIPLIER);
    	jumpIncr = (maxJumpSpeed - initialJumpSpeed) / 10; 
    	maxFallSpeed = -maxJumpSpeed * MAX_FALL_MAX_JUMP_MULTIPLIER;
    }

    /*
     * Increases walk speed, if possible.
     */ 
    private void accelerate()
    {
        if(accelX < maxAccelRate)
        {
            accelX += accelerationRate;
        }
    }
    
	/*
     * Set the width and height and initial
     * values for the collision box. 
     * The only thing that will change
     */
    private void setUpCollisionBox()
    {
    	xLeftOffsetCollisionBox = width/COLLISION_WIDTH_DIV;
    	collisionBoxHeight = height - COLLISION_BOX_H_OFFSET;
 
    	collisionBox = new Rectangle(
				    		xPos, 
				    		yPos, 
				    		width / 2,
				    		collisionBoxHeight);
    }
    
    @Override
	public int getWidth()
	{
		return width;
	}
    
	@Override
	public int getHeight()
	{
		return height;
	}
    
	
	/*
	 * NOTE: This sets isJumping to the inverse of b. 
	 */
    @Override
    public void setCanJump(boolean b)
    {
        canJump = b;
        isJumping = !b;
    }

    /*
     * Places the Player at the designated start position according to
     * the Level associated with it. 
     */ 
    private void setUpStartPosition()
    {
    	this.xPos = startTile.xPos;
        this.yPos = startTile.yPos - startTile.tileHeight;
        yPos--;
        
        System.out.println("Player is on level " + level + 
                           " at position x: " + xPos + ", y: " + yPos);              
    }
    
    /*
     * Initialises the Player's Bow (if he has one)
     */
    private void initBow()
    {
        bowLeft = AnimCreator.loadImage(bowLeftPath);
        bowRight = bowLeft.getFlippedCopy(true, false);
        currentBow = bowLeft;
    }    
    
    public Shape getZone()
    {
        return getLevel().getActorZone(this);
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
        playNote1 = new BasicCommand("playNote1");
        run = new BasicCommand("run");
        
        // here we connect a keyboard key to each player command.
        prov.bindCommand(new KeyControl(Input.KEY_A), left);
        prov.bindCommand(new KeyControl(Input.KEY_D), right);
        prov.bindCommand(new KeyControl(Input.KEY_W), jump);
        prov.bindCommand(new KeyControl(Input.KEY_1), playNote1);
        prov.bindCommand(new KeyControl(Input.KEY_LSHIFT), run);
        
        in.addMouseListener(new MouseListener()
        {
            public void	mousePressed(int button, int x, int y) 
            {
                if(button == BOW_BUTTON)
                {       
                    Tile c = getLevel().getCollisionTileAt(x, y);
                    if(c != null)
                    {
                        System.out.println(
                        	"Mouse pressed Tile " 
                           + c.xCoord + ", " 
                           + c.yCoord);
                    }
                    mouseX = x;
                    mouseY = y;
                    readyArrow();
                }
                else if(button == Input.MOUSE_RIGHT_BUTTON)
                {
                	/*
                	 * For now this button is for creating a 'goto'
                	 * for the Enemy, as a way of testing path-finding
                	 *  algortithms.
                	 */
                	for(Enemy e : getLevel().getEnemies())
                	{
                		//e.setGoto(new Point(
                			//	(float)mouseX, (float)mouseY));
                		e.startJump();
                	}
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
            	userHoldingLeftXOrRight = false;   
            }
        }
        else if(com.equals(right))
        {
            //rightMoveDown = false;
            if(moving.equals(Direction.RIGHT))
            {
            	userHoldingLeftXOrRight = false;   
            }
        }
        else if(com.equals(jump))
        {
        	System.out.println("jump key released");
        	jumpKeyDown = false;
        }
        else if(com.equals(playNote1))
        {
        	System.out.println("playNote1 key released");
        	playNote1KeyDown = false;
        }
        else if(com.equals(run))
        {
        	System.out.println("-- Shift released");
        	fastMoveMode = false;
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
            setMovingDir(Direction.LEFT);
            userHoldingLeftXOrRight = true;
        }
        else if(com.equals(right))
        {
            setMovingDir(Direction.RIGHT);
            userHoldingLeftXOrRight = true;
        }
        else if(com.equals(jump))
        {
        	/*
        	 * If the jump key hasn't already been pressed 
        	 */
        	if(!jumpKeyDown)
        	{
        		startJump();
        	}
        }
        else if(com.equals(playNote1))
        {
        	System.out.println("playNote1 key pressed");
        	playNote1KeyDown = true;
        	
        	if(hasInstrument)
        	{
        		currentInstrument.play(0); 
        		// TODO
        	}
        }
        else if(com.equals(run))
        {
        	System.out.println("-- Shift is down");
        	fastMoveMode = true;
        }
    }
    
    private void pickUp(PlayerItem pi)
    {
    	// TODO
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

        
        if(currentArrow.getMovementAngle() >= 0)
        {
            currentBow = bowRight;   
            setFacing(Direction.RIGHT);
            bowX = xPos + BOW_X_OFFSET + (getCurrentFrameWidth() / 2);
            currentBow.setRotation(currentArrow.getMovementAngle() 
                                    - BOW_ANGLE_OFFSET);
        }
        else
        {
            setFacing(Direction.LEFT);
            currentBow = bowLeft;   
            bowX = xPos - BOW_X_OFFSET;
            currentBow.setRotation(currentArrow.getMovementAngle() 
                                    + BOW_ANGLE_OFFSET);
        }
        updateBowPosition();
        currentArrow.setPosition(getHoldingArrowX(), getHoldingArrowY());
    }
    
    @Override
    public float getHealth()
    {
        return health;
    }
    
   /* @Override
    public float getJumpSpeed()
    {
        return JUMP_SPEED;
    }*/
    
    @Override
    public void die()
    {
        System.out.println("Player is dead!");
    }
    
    /*private float getMiddleXPos()
    {
        final float midX = xPos + (getCurrentFrameWidth() / 2);
        //System.out.println("xPos: " + xPos + ", midX: " + midX);
        return midX;
    }*/
    
    private void updateBowPosition()
    {
        final float ANGLE = Math.abs(currentArrow.getMovementAngle());
        
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
    	if(bowCharge >= minReleaseCharge)
    	{
	        currentArrow.release(bowCharge);
	        firedArrows.add(currentArrow);
	        System.out.println("released arrow, power " + bowCharge
	        	+ " angle " + currentArrow.getMovementAngle());        
    	}
    	isChargingArrow = false;
    	currentArrow = null;
    	bowCharge = 0;
    }
    
    /**
     * Returns a list of the projectiles fired by this Player 
     */ 
    public List<Projectile> getFiredProjectiles()
    {
        return new ArrayList<Projectile>(firedArrows);
    }
    
    /*
     * Sets the direction the Player is facing
     */ 
    @Override
    public void setFacing(Direction dir)
    {
        facing = dir;
        if(!userHoldingLeftXOrRight)
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
        setFacing(dir);
        //System.out.println("setMovingDir: " + dir);
    }
    
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
    	accelerate();
    	
        if(dir.equals(Direction.RIGHT))
        {
            currentAnim = rightWalk;
            xSpeed = accelX;
        }
        else if(dir.equals(Direction.LEFT))
        {
            currentAnim = leftWalk;
            xSpeed = -accelX;
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
            if(a.isOutOfBounds() || a.getCollidedWithEnemy())
            {
				// damage already done in Enemy.doCollision
                // we can safely remove the arrow here
                System.out.println("removing arrow" + a);
                it.remove();
            }
            else if(a.isFlying())
            {
            	a.update();
            }
        }
    }
    
    /*
     * Applies friction to the player
     */ 
    private void applyFriction()
    {
        xSpeed *= Level.FRICTION;
    }
    
    @Override
    public void update()
    {
    	ObjectMover.applyGravity(this);
        applyFriction();
                
        if(userHoldingLeftXOrRight)
        {
            applySpeed(moving);
        }
        else
        {
            decelerate();
        }
        if(isJumping)
        {
        	updateJump();
        }
        
        // Update Arrow information depending on state
        if(isChargingArrow)
        {
            chargeArrow();
        }
        
        updateFiredArrows();
        ObjectMover.move(this);
        
       
        //System.out.printf("xSpeed : %.3f | accelX : %.3f\n", xSpeed, accelX);
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
    public void startJump()
    {
    	System.out.println("calling Player.jump()");
    	//assert canJump()
    	
        setYSpeed(initialJumpSpeed);
        /*canJump = false;
        isJumping = true;*/
        setCanJump(false);
    }
    
    /*
     * Updates the player's current jump action. That is,
     * if the player has previously pressed the jump
     * command and successfully started a jump, the jump
     * key is still pressed AND the jump hasn't reached maximum speed,
     * increment it.
     */
    private void updateJump()
    {
		if(!isJumping && jumpKeyDown)
		{
			if(ySpeed > maxJumpSpeed)
			{
	    		ySpeed -= jumpIncr;
	    		System.out.println("  Player.updateJump() : " + ySpeed);
			}
			else
			{
				System.out.println(
    				"updateJump() --> MAX_JUMP_SPEED reached : " + ySpeed);
				isJumping = false;
			}
		}
	}
    
    private void decelerate()
    {
        accelX -= accelerationRate;
        if(accelX < 0)
        {
            resetAccelX();
        }
    }
    
    @Override
    public void resetAccelY()
    {
        // does nothing for now
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
        return maxFallSpeed;
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
        //g.setColor(Color.black);
        //g.draw(getCollisionBox());
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
    	
        //leftWalk = AnimCreator.createAnimFromPaths(Actor.ANIM_DURATION, 
          //                          AUTO_UPDATE, leftWalkPaths);
        // get the images for leftWalk so we can flip them to use as right.#
    	Image[] leftWalkImgs = new Image[leftWalkPaths.length];
    	Image[] rightWalkImgs = new Image[leftWalkPaths.length];
    	Image[] leftStandImgs = new Image[leftStandPaths.length];
    	Image[] rightStandImgs = new Image[leftStandPaths.length];
    	
    	
    	
    	leftWalkImgs = AnimCreator.getImagesFromPaths(
    			leftWalkPaths).toArray(leftWalkImgs);
    	
    	height = leftWalkImgs[0].getHeight();
    	
    	rightWalkImgs = AnimCreator.getHorizontallyFlippedCopy(
    			leftWalkImgs).toArray(rightWalkImgs);
    	
    	leftStandImgs = AnimCreator.getImagesFromPaths(
    			leftStandPaths).toArray(leftStandImgs);
    	
    	rightStandImgs = AnimCreator.getHorizontallyFlippedCopy(
    			leftStandImgs).toArray(rightStandImgs);
    	
    	boolean autoUpdate = true;
    	
    	leftWalk = new /*Masked*/Animation(
						 leftWalkImgs, 
						 Actor.ANIM_DURATION, 
						 autoUpdate);

    	rightWalk = new /*Masked*/Animation(
    						rightWalkImgs, 
    						Actor.ANIM_DURATION, 
				            autoUpdate);
    	
    	leftStand = new /*Masked*/Animation(
    						leftStandImgs, 
							Actor.ANIM_DURATION, 
							autoUpdate);
    	
    	rightStand = new /*Masked*/Animation(
    						rightStandImgs, 
    						Actor.ANIM_DURATION,
    						autoUpdate);
    	
    	setInitialAnim();
    }    
    
    private void setInitialAnim()
    {
    	// get the initial direction from the level and 
    	// select an animation based upon it.
        facing = level.playerFacing;
        
        if(facing == Direction.LEFT)
        {	
        	currentAnim = leftWalk;
        }
        else
        {
        	currentAnim = rightWalk;
        }
        
        Image i = currentAnim.getCurrentFrame();
        width = i.getWidth();
        height = i.getHeight();
    }

	@Override
	public boolean isOutOfBounds() 
	{
		return outOfBounds ;
	}

	@Override
	public void setOutOfBounds(boolean b) 
	{
		outOfBounds = b;	
	}
	
	/*@Override
	public CollideMask getCollideMask()
	{
		
		return currentAnim.getCurrentFrameMask();
	}*/

	public Rectangle getCollisionBox()
	{
		/*
		 * A box slightly thinner than the current frame
		 * for more believable collision detection.
		 */
		//if(getFacing() == Direction.LEFT)
		//{
		collisionBox.setX(xPos + xLeftOffsetCollisionBox);
		/*}
		else
		{
			collisionBox.setX(xPos + xRightOffsetCollisionBox);
		}*/
		collisionBox.setY(yPos);
		//collisionBox.setHeight(height);
		//System.out.println("getCollisionBox : returning " + collisionBox);
		return collisionBox;
	}
}
