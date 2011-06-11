/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */

package diediedie.level.actors;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import pulpcore.animation.Animation;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;
import pulpcore.sprite.ImageSprite;
import pulpcore.sprite.Sprite;
import pulpcore.CoreSystem;
import pulpcore.math.Rect;
import pulpcore.image.CoreImage;
import pulpcore.image.AnimatedImage;
import diediedie.level.Level;
import diediedie.level.Tile;
import diediedie.level.Direction;
import diediedie.level.Arrow;
import diediedie.level.Projectile;
import diediedie.util.AnimCreator;
import diediedie.util.Mover;
import diediedie.util.Collider;
import diediedie.util.Aligner;
import pulpcore.Input;

/**
 * ..where *YOU* are the HERO!
 */ 
public class Player implements Actor
{    
    //final boolean autoUpdate = true;
    private boolean setUp = false, canJump = false;
    
    private ImageSprite sprite;
    
    private float accelX = 0f, 
                  bowCharge = 0;
                  
                  
    public final float MAX_CHARGE = 20.34f, CHARGE_INCR = 0.3f, 
                       MAX_Y_SPEED = 20.5f,
                       MAX_X_SPEED = 2.5f, JUMP_SPEED = -5.5f,
                       MOVE_SPEED = 0.9f, 
                       ACCEL_RATE = 0.03f;
                        
    private final int MAX_HEALTH = 20, 
                      BOW_AIM_UP_TRIGGER = 50,
                      BOW_AIM_DOWN_TRIGGER = 110, 
                      BOW_X_OFFSET = 5,
                      BOW_ANGLE_OFFSET = 90, 
                      BOW_Y_OFFSET_AIM_UP = -10,
                      BOW_Y_OFFSET_NORMAL = -2,
                      BOW_Y_OFFSET_AIM_DOWN = 6, 
                      ARROW_Y_OFFSET = 15,
                      MAX_ACCEL = 4;
                      
    private int health = MAX_HEALTH, 
                arrowCount = 0;    
    
    private Arrow currentArrow = null; 
    private List<Arrow> firedArrows = Collections.synchronizedList(
                            new ArrayList<Arrow>());
    
    private boolean isChargingArrow = false, isFiringArrow = false;
    
    private float xSpeed, ySpeed, 
                  xPos, yPos, 
                  oldX, oldY, 
                  bowX, bowY,
                  bowYCurrentOffset, 
                  bowXCurrentOffset;
    
    public Direction facing = Direction.LEFT, 
                     moving = Direction.LEFT; 
        
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
    
    private String leftStandPath = "data/STICKMAN_LEFT_STAND.png";
    
    private AnimatedImage leftWalk, rightWalk, currentAnim;
            
    private CoreImage leftStand, rightStand, 
                      bowLeft, bowRight, 
                      currentBow;
    
    
    
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
    
    public Sprite getSprite()
    {
        return sprite;
    }
    
    public void setRunning(boolean b)
    {
        running = b;
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
        this.yPos = level.getPlayerTile().yPos
                    - level.getPlayerTile().tileHeight;
        yPos--;
        CoreSystem.print("Player is on level " + level + 
                           " at position x: " + xPos + 
                                      ", y: " + yPos);              
    }
    
    private void initBow()
    {
          bowLeft = AnimCreator.loadImage(bowLeftPath);
          bowRight = bowLeft.mirror();
          currentBow = bowLeft;
    }    
    
    @Override
    public Rect getZone()
    {
        return getLevel().getActorZone(this);
    }
    
    @Override
    public float getMoveSpeed()
    {
        return MOVE_SPEED;
    }
    
    /*
     
    API NOTE >> http://www.interactivepulp.com/pulpcore/api/pulpcore/Input.html
    
    ========
    Mouse buttons and keyboard keys are both treated as "virtual keys". 
    Virtual keys have four states: UP, DOWN, PRESSED, and RELEASED. The
    states only change when polled, which occurs before each call to
    Scene.update().
    
    */
    
    /**
     * Links the game's InputProvider to the Player obkect
     */ 
    /*
    public void associateInputProvider(InputProvider prov, Input in)
    {
        // http://www.interactivepulp.com/pulpcore/api/pulpcore/Input.html
        prov.addListener(this); 
        
        //jump = new BasicCommand("jump");
        //left = new BasicCommand("left");
        //right = new BasicCommand("right");
        
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
                        CoreSystem.print("Mouse pressed Tile " +
                                            c.xCoord + ", " + 
                                            c.yCoord);
                    }
                    mouseX = x;
                    mouseY = y;  
                    readyArrow();
                }
            }
            public void	mouseReleased(int button, int x, int y)
            {
                if(button == BOW_BUTTON)
                {
                    //CoreSystem.print("left mouse released");
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
   
    
    /*
     * Starts the player aiming an arrow towards the
     * Mouse Pointer
     */ 
    public void readyArrow(float mouseX, float mouseY)
    {
        CoreSystem.print("Readying arrow");
        currentArrow = new Arrow(xPos, yPos, level, mouseX, mouseY);
        isChargingArrow = true;
    }
    
    /*
     * Charges the arrow and updates the Direction the player faces.
     * -- Used by Player.update().
     */ 
    public void chargeArrow(float mouseX, float mouseY)
    {
        if(bowCharge < MAX_CHARGE)
        {
            bowCharge += CHARGE_INCR;
        }
        CoreSystem.print("bowCharge " + bowCharge);
        currentArrow.updateAiming(mouseX, mouseY);

        if(currentArrow.getAngle() >= 0)
        {
            currentBow = bowRight;   
            setFacing(Direction.RIGHT);
            bowX = xPos + BOW_X_OFFSET + (getCurrentFrameWidth() / 2);
            //currentBow.setRotation(
            currentBow.rotate(
                currentArrow.getAngle() - BOW_ANGLE_OFFSET);
        }
        else
        {
            setFacing(Direction.LEFT);
            currentBow = bowLeft;   
            bowX = xPos - BOW_X_OFFSET;
            //currentBow.setRotation(currentArrow.getAngle() 
            currentBow.rotate(currentArrow.getAngle() 
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
        CoreSystem.print("Player is dead!");
    }
    
    public float getMiddleXPos()
    {
        final float midX = xPos + (getCurrentFrameWidth() / 2);
        //CoreSystem.print("xPos: " + xPos + ", midX: " + midX);
        return midX;
    }
    
    /*
     * Updates the position of the Player's Bow. 
     * Used by: Player.chargeArrow()
     */
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
    
    /*
     * Returns the x position of the arrow when being held by the 
     * player.
     */ 
    public float getHoldingArrowX()
    {
        return xPos + getCurrentFrameWidth() / 2;
    } 
    
    public float getCurrentFrameWidth()
    {
        return AnimCreator.getCurrentFrameRect(this).width;
    }
    
    /*
     * Returns the y position of the arrow when being held by the player
     */
    public float getHoldingArrowY()
    {
        return yPos + ARROW_Y_OFFSET;
    } 
    
    /*
     * Fires an Arrow from the Player's position towards the X / Y
     * coordinates.
     */ 
    public void releaseArrow()
    {
        isChargingArrow = false;
        isFiringArrow = true;
        
        currentArrow.release(bowCharge);
        firedArrows.add(currentArrow);
        currentArrow = null;
        
        CoreSystem.print("released arrow, power " + bowCharge);        
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
            setStandingImage();
        }
    }
    
    /*
     * Sets the direction the Player is moving.
     * Called by 
     */ 
    public void setMovingDir(Direction dir)
    {
        if(moving != dir)
        {
            // changed direction, reset horizontal acceleration.
            resetAccelX();
        }
        moving = dir;
        setFacing(dir);
        CoreSystem.print("Player.setMovingDir: " + dir);
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
     * Stops the Player's animation var to 'standing'. 
     */ 
    private void setStandingImage()
    {
        if(facing.equals(Direction.RIGHT))
        {
            //currentAnim = rightStand;
            sprite.setImage(rightStand);
        }
        else if(facing.equals(Direction.LEFT))
        {
            //currentAnim = leftStand;   
            sprite.setImage(leftStand);
        }
        else throw new IllegalStateException(
            "standing dir neither left or right");
    }
    
    /*
     * Applies speed to the Player -- called by player.update()
     */    
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
     *  -- Called by player.applySpeed()
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
     * -- called by player.update()
     */ 
    private void applyFriction()
    {
        xSpeed *= level.FRICTION;
    }
    
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
            chargeArrow(
                Input.getMouseX(), Input.getMouseY());
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
    public CoreImage getCurrentImage()
    {
        return sprite.getImage();
    }
    
    @Override
    public boolean canJump()
    {
        return canJump;
    }
    
    public void printSpeed()
    {
        CoreSystem.print("accelX==" + accelX + ", " + "xSpeed==" 
                            + xSpeed + ", ySpeed==" + ySpeed);
    }
    
    public void printPosition()
    {
        CoreSystem.print("xPos==" + xPos + ", yPos==" + yPos); 
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
        setStandingImage();
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
    public void draw(CoreGraphics g)
    {
        //g.drawAnimation(currentAnim, getX(), getY());
        g.drawImage(currentAnim, (int)getX(), (int)getY());

        drawArrows(g);
        
        if(isChargingArrow)
        {
            g.drawImage(currentBow, (int)bowX, (int)bowY);
        }        
    }
    
    /*
     * Draw all the arrows that still exist 
     */
    private void drawArrows(CoreGraphics g)
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
        
        leftWalk = AnimCreator.createAnimFromPaths(
            Actor.ANIM_DURATION, /*autoUpdate,*/ leftWalkPaths);
        
        rightWalk = AnimCreator.createAnimFromPaths(
            Actor.ANIM_DURATION, /*autoUpdate,*/ rightWalkPaths);           
        
        /*
        rightStand = AnimCreator.createAnimFromPaths(
            Actor.ANIM_DURATION, rightStandPaths);
        
        leftStand = AnimCreator.createAnimFromPaths(
            Actor.ANIM_DURATION, leftStandPath);                
        */
        
        sprite.setImage(leftStand);
        sprite.setPixelLevelChecks(true);
        // get initial direction from the level
        facing = level.playerFacing;
    }    
    

}
