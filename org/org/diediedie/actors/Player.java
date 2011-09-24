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
 */ 
public class Player extends BaseLevelObject implements Actor, 
													   InputProviderListener 
{    
	private boolean setUp = false, canJump = false;
    final boolean autoUpdate = true;
     
    private float accelX = 0f, bowCharge = 0, bowX, bowY, 
                  bowYCurrentOffset;
                  
    private int height, width, minReleaseCharge = 7;
    
    public final float MAX_CHARGE = 20.34f, 
                       CHARGE_INCR = 0.4f, 
                       BOW_Y_OFFSET_NORMAL = -2f, 
                       BOW_Y_OFFSET_AIM_UP = -10, 
                       MAX_Y_SPEED = 20.5f,
                       MAX_X_SPEED = 2.5f, 
                       JUMP_INCR = 0.8f,
                       INITIAL_JUMP_SPEED = -0.2f,
                       MAX_JUMP_SPEED = -5.6f,
                       BOW_Y_OFFSET_AIM_DOWN = 6, 
                       ARROW_Y_OFFSET = 15,
                       MOVE_SPEED = 0.9f, MAX_ACCEL = 4f, 
                       ACCEL_RATE = 0.03f;

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
                xLeftOffsetCollisionBox;
                    
    // Movement
    private Command jump;
    private Command left; 
    private Command right;    
    
    private Rectangle collisionBox;
    
    private Arrow currentArrow = null; 
    private List<Arrow> firedArrows = Collections.synchronizedList(new
                                                    ArrayList<Arrow>());
    private boolean isChargingArrow = false;
    
    private float xSpeed, ySpeed;
    private Direction facing = Direction.LEFT, moving = Direction.LEFT; 
        
    // running: indicates the user is holding a directional button
    private boolean running = false;
        
    private final String bowLeftPath = 
    	"data/player_images/STICKMAN_BOW_LEFT.png";
    
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
    
    // associated level for collision / item collection reference
	private boolean isJumping = false;
	private boolean jumpKeyDown = false;
	private boolean outOfBounds = false;
    private Tile startTile;
	private final int COLLISION_BOX_H_OFFSET = 1;
	private int collisionBoxHeight;
    
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
            setUp = true;
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
    
    @Override
    public void setCanJump(boolean b)
    {
        canJump = b;
        if(b == true)
        {
        	isJumping = false;
        }
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
    
    /*@Override
    public float getMoveSpeed()
    {
        return MOVE_SPEED;
    }*/
    
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
        else if(com.equals(jump))
        {
        	System.out.println("jump key released");
        	jumpKeyDown = false;
        	isJumping = false;
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
        	if(!jumpKeyDown)
        	{
        		startJump();
        	}
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
            if(a.getCollidedWithEnemy() ||
               a.isOutOfBounds())
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
                
        if(running)
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
    	
    	if(canJump())
        {
            setYSpeed(INITIAL_JUMP_SPEED);
            canJump = false;
            isJumping = true;
        }
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
		if(!canJump)
		{
			if(ySpeed > MAX_JUMP_SPEED)
			{
	    		ySpeed -= JUMP_INCR;
	    		//System.out.println("Player.updateJump() : " + ySpeed);
			}
			else
			{
				/*System.out.println(
    				"updateJump() --> MAX_JUMP_SPEED reached");*/
				isJumping = false;
			}
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
        
        //g.draw(getCurrentAnim().getCurrentFrame()
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
          //                          autoUpdate, leftWalkPaths);
        // get the images for leftWalk so we can flip them to use as right.#
    	Image[] leftWalkImgs = new Image[leftWalkPaths.length];
    	Image[] rightWalkImgs = new Image[leftWalkPaths.length];
    	Image[] leftStandImgs = new Image[leftStandPaths.length];
    	Image[] rightStandImgs = new Image[leftStandPaths.length];
    	
    	leftWalkImgs = AnimCreator.getImagesFromPaths(
    			leftWalkPaths).toArray(leftWalkImgs);
    	
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
