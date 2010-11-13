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
import org.diediedie.actors.Direction;
import org.diediedie.actors.Player;
import org.diediedie.actors.State;
import org.diediedie.actors.actions.*;
import java.util.*;
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
public class Bluey implements Enemy, StateMachine
{
    // constants
    public static final int MAX_HEALTH = 50;
    private float viewSize = 500f;
    public final String leftStandPath = "data/bluey_standing_left.png";
    
    private Graphics g;
    
    public final String[] leftWalkPaths = 
    {
        "data/bluey_walk_left_1.png",
        "data/bluey_walk_left_2.png",
        "data/bluey_walk_left_3.png",
        "data/bluey_walk_left_4.png",
        "data/bluey_walk_left_5.png",
        "data/bluey_walk_left_6.png"
    };
    
    public final float MAX_Y_SPEED = 20.5f, WALK_SPEED = 1f, 
                       RUN_SPEED = 3.1f, JUMP_SPEED = -5.5f,
                       ACCEL_RATE = 0.03f, EYE_OFFSET_HEIGHT = 5f;        
    
    // Variables    
    private State currentState = null;
    private Patrol patrol;
    private Alert alert;
    
    private boolean setUp = false, canJump = false, moving = false,
                    canSeePlayer = false, hasSeenPlayer = false,
                    fsmRunning = false, seenPlayerEvidence = false;
                    
    private int health;
    private Direction facing = null;
    private Level level;
    
    private Set<LevelObject> visibleObjects;
    
    private float xPos, yPos, tileHeight, moveSpeed = 0, oldX, oldY,
                  xSpeed = 0, ySpeed = 0, accelX = 0, accelY = 0;
   
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
            health = MAX_HEALTH;
            createStates();
            canSeePlayer = false;
            hasSeenPlayer = false;
            setUp = true;
        }
        visibleObjects = new HashSet<LevelObject>();
        setLevel(l);;
        xPos = t.xPos;
        yPos = t.yPos;
        yPos -= (AnimCreator.getCurrentFrameRect(this).getHeight() - 
                 t.tileHeight);
        yPos--;
        System.out.println("new Bluey enemy at " + xPos + ", " + yPos);
    }
    
    @Override
    public Set<LevelObject> getVisibleObjects()
    {
        return visibleObjects;
    }
    
    @Override
    public boolean isFSMRunning()
    {
        return fsmRunning;   
    }
    
    @Override
    public void setLevel(Level l)
    {
        level = l;
    }
    
    @Override
    public void setMoveSpeed(float f)
    {
        moveSpeed = f;
        System.out.println("bluey setMoveSpeed: " + f);
    }
    
    @Override
    public void setMoving(boolean m)
    {
        moving = m;
    }
    
    @Override
    public void stopFSM()
    {
        System.out.println("stopping FSM on " + this + ", current state " 
                            + currentState);
        currentState.exit();
        fsmRunning = false;
    }
    
    @Override
    public void startFSM()
    {
        
        System.out.println(this + "\n\tstartFSM()");
        fsmRunning = true;
        setInitialState();
        currentState.enter();
    }
    
    @Override
    public Level getLevel()
    {
        return level;
    }
 
 
    public void addVisibleObject(LevelObject lo)
    {
        visibleObjects.add(lo);
    }
       
    @Override
    public final float getWalkSpeed()
    {
        return WALK_SPEED;   
    }
    
    @Override
    public float getViewSize()
    {
        return viewSize;
    }
 
    @Override
    public final float getRunSpeed()
    {
        return RUN_SPEED;
    }
   
    @Override
    public void setFacing(Direction d)
    {
        facing = d;
        if(d.equals(Direction.LEFT))
        {
            currentAnim = leftWalkAnim;
        }
        else
        {
            currentAnim = rightWalkAnim;
        }
    }
    
 
    @Override
    public boolean hasSeenPlayerEvidence()
    {
        return seenPlayerEvidence;
    }
    
    @Override
    public void setSeenPlayerEvidence(boolean b)
    {
        seenPlayerEvidence = b;
    }
    
    @Override
    public void setInitialState()
    {
        currentState = patrol;
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
    
    @Override
    public void createStates()
    {
        System.out.println("creating states for " + this);
        patrol = new Patrol(this);
        alert = new Alert(this);
        setInitialState();
    }
    
    @Override
    public void setJump(boolean b)
    {
        canJump = b;
        // System.out.println("bluey: canJump == " + canJump);
    }   
    
    @Override
    public State getState()
    {
        return currentState;
    }
    
    @Override
    public void resetAccelX()
    {
        // do nothing for now
    }
    
    
    @Override
    public void resetAccelY()
    {
        // do nothing for now
    }
    
    @Override
    public float getMoveSpeed()
    {
        return moveSpeed;
    }
    
    @Override
    public float getJumpSpeed()
    {
        return JUMP_SPEED;
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
    public Direction getFacing()
    {
        return facing;
    }
    
    @Override
    public int getHealth()
    {
        return health;
    }
    
    @Override
    public void changeState(State nextState)
    {
        currentState.exit();
        currentState = nextState;
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
    public boolean canJump()
    {
        return canJump;
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
                                      Actor.ANIM_DURATION, true);
        rightStandAnim = new Animation(rightStandImages,
                                      Actor.ANIM_DURATION, true);
                                                
        // walking anims
        Image[] leftWalkImages = AnimCreator.getImagesFromPaths(
                               leftWalkPaths).toArray(rightStandImages);
        Image[] rightWalkImages = AnimCreator
                            .getHorizontallyFlippedCopy(leftWalkImages)
                                            .toArray(rightStandImages);
        leftWalkAnim = new Animation(leftWalkImages, 
                                      Actor.ANIM_DURATION, true);
        rightWalkAnim = new Animation(rightWalkImages,
                                       Actor.ANIM_DURATION, true);
        facing = Direction.LEFT;
        currentAnim = leftStandAnim;
    }
        
    /*
     * Returns the height of Bluey's Tile in the Map Editor, Tiled.
     */ 
    public float getTileHeight()
    {
        return tileHeight;
    }
    
    @Override
    /**
     * Instructs the enemy to jump
     */ 
    public void jump()
    {
        if(canJump())
        {
            System.out.println("bluey jump");    
            ySpeed = JUMP_SPEED;
            canJump = false;
        }
    }
    
    @Override
    public void update()
    {        
        updatePosition();
        updateProjectiles();
        updateState();
        
        /*if(xSpeed > 0)
        {
            System.out.println("bluey speed " + xSpeed);        
        }*/
    }
    
    /*
     * 
     */ 
    private void updateState()
    {
        // Start the machine!
        if(!isFSMRunning() && (health > 0))
        {
            startFSM();
        }
        
        
        currentState.update();
        
        if(currentState.equals(patrol))
        {
            if(canSeePlayer())
            {
                //System.out.println("changing to alert");
                //changeState(alert);
            }
        }
        else if(currentState.equals(alert))
        {
            
        }
    }
    
    public void updatePosition()
    {
        Mover.applyGravity(this);
        applyFriction();
        
        if(isMoving())
        {
            applySpeed(facing);
        }
        
        if(!Mover.move(this))
        {
            setStandingAnim();
            moving = false;
        }
    }
    
    // this is just a dupe of the one from Player, give or take...
    private void setStandingAnim()
    {
        if(getFacing().equals(Direction.RIGHT))
        {
            currentAnim = rightStandAnim;
        }
        else if(getFacing().equals(Direction.LEFT))
        {
            currentAnim = leftStandAnim;   
        }
        else throw new IllegalStateException(
                            "standing dir neither left or right");
    }
    
    // Woah there!
    private void applyFriction()
    {
        xSpeed *= level.FRICTION;
    }
    
    @Override
    public void applySpeed(Direction dir)
    {
        setFacing(dir);
        if(dir.equals(Direction.RIGHT))
        {
            xSpeed = (moveSpeed);
        }
        else if(dir.equals(Direction.LEFT))
        {            
            xSpeed = -(moveSpeed);
        }  
    }
    
    @Override
    public float getEyePosX()
    {
       return xPos + (AnimCreator.getCurrentFrameRect(this)
                                 .getWidth() / 2);
    }
    
    @Override
    public float getEyePosY()
    {
        return yPos + EYE_OFFSET_HEIGHT;
    }
    
    @Override
    public boolean isMoving()
    {
        return moving;   
    }

   
    
    @Override
    public void setHasSeenPlayer(boolean b)
    {
        hasSeenPlayer = b;
    }
    
    @Override
    public void setCanSeenPlayer(boolean b)
    {
        canSeePlayer = b;
        
        if(b && !hasSeenPlayer())
        {
            setHasSeenPlayer(true);
        }
    }
    
    @Override
    public boolean canSeePlayer()
    {
        return canSeePlayer;
    }
    
    @Override
    public boolean hasSeenPlayer()
    {
        return hasSeenPlayer;
    }
    
    @Override
    public float getMaxFallSpeed()
    {
        return MAX_Y_SPEED;
    }
    
    @Override
    public void die()
    {
        System.out.println("Die, you, " + this);
        // n i
    }
    
    
    private void updateProjectiles()
    {
        
    }
    
    @Override
    public Animation getCurrentAnim()
    {
        return currentAnim;
    }
    
    @Override
    public void draw(Graphics g)
    {
        //System.out.println("drawing Bluey: " + getX() + ", " + getY());
        g.drawAnimation(currentAnim, getX(), getY());

        drawProjectiles(g);

        final Look look = null;
        
        // this is just debug code; it won't be here for ever :)
        if(currentState.getClass().equals(patrol.getClass()))
        {
            Action act = patrol.getCurrentAction();
            act.draw(g);
        }
    }

    public Graphics getGraphics()
    {
        return g;
    }
    
    private void drawProjectiles(Graphics g)
    {
        
    }
    
    public float getX(){ return xPos; }   
    public float getY(){ return yPos; } 
}
