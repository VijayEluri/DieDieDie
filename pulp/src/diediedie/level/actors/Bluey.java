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

import java.util.Set;
import java.util.HashSet;
import diediedie.level.Level;
import diediedie.level.LevelObject;
import diediedie.level.Direction;
import diediedie.level.actors.Player;
import diediedie.level.actions.Action;
import diediedie.level.states.State;
import diediedie.level.states.Patrol;
import diediedie.level.states.Alert;
import diediedie.level.states.StateMachine;
import diediedie.level.actions.Look;
import diediedie.level.Tile;
import diediedie.util.AnimCreator;
import diediedie.util.Mover;
import pulpcore.animation.Animation;
import pulpcore.math.Rect;
import pulpcore.sprite.ImageSprite;
import pulpcore.image.CoreGraphics;
import pulpcore.image.CoreImage;
import pulpcore.image.AnimatedImage;
import pulpcore.sprite.Sprite;
/*
 * Blue stick-enemy wielding 2 pistols. 
 */ 
public class Bluey implements Enemy, StateMachine
{
    public static final int MAX_HEALTH = 50;
    
    private float viewSize = 500f;
    public final String leftStandPath = "data/bluey_standing_left.png";
    
    private CoreGraphics g;
    
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
    private int facing = Direction.LEFT;
    private Level level;
    
    private Set visibleObjects;
    
    private float tileHeight, moveSpeed = 0, oldX, oldY,
                  xSpeed = 0, ySpeed = 0, accelX = 0, accelY = 0,
                  xPos, yPos;
   
    private /*Animation*/ AnimatedImage leftWalkAnim,
                                        rightWalkAnim, 
                                        leftStandAnim, 
                                        rightStandAnim;
    private ImageSprite sprite;
    
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
        visibleObjects = new HashSet();
        setLevel(l);
        xPos = t.xPos;
        yPos = t.yPos;
        yPos -= (AnimCreator.getCurrentFrameRect(this).height - 
                                                     t.tileHeight);
        yPos--;
        System.out.println("new Bluey enemy at " + xPos + ", " + yPos);
    }
    
    
    public Set getVisibleObjects()
    {
        return visibleObjects;
    }
    
    
    public boolean isFSMRunning()
    {
        return fsmRunning;   
    }
    
    
    public void setLevel(Level l)
    {
        level = l;
    }
    
    
    public void setMoveSpeed(float f)
    {
        moveSpeed = f;
        System.out.println("bluey setMoveSpeed: " + f);
    }
    
    
    public void setMoving(boolean m)
    {
        moving = m;
    }
    
    
    public CoreImage getCurrentImage()
    {
        return sprite.getImage();
    }
    
    
    public Sprite getSprite()
    {
        return sprite;
    }
    
    
    public void stopFSM()
    {
        System.out.println("stopping FSM on " + this + ", current state " 
                            + currentState);
        currentState.exit();
        fsmRunning = false;
    }
    
    
    public void startFSM()
    {
        
        System.out.println(this + "\n\tstartFSM()");
        fsmRunning = true;
        setInitialState();
        currentState.enter();
    }
    
    
    public Level getLevel()
    {
        return level;
    }
 
 
    public void addVisibleObject(LevelObject lo)
    {
        visibleObjects.add(lo);
    }
       
    
    public final float getWalkSpeed()
    {
        return WALK_SPEED;   
    }
    
    
    public float getViewSize()
    {
        return viewSize;
    }
 
    
    public final float getRunSpeed()
    {
        return RUN_SPEED;
    }
   
    
    public void setFacing(int d)
    {
        facing = d;
        if(d == Direction.LEFT)
        {
            sprite.setImage(leftWalkAnim);
        }
        else if(d == Direction.RIGHT)
        {
            sprite.setImage(rightWalkAnim);
        }
    }
    
 
    
    public boolean hasSeenPlayerEvidence()
    {
        return seenPlayerEvidence;
    }
    
    
    public void setSeenPlayerEvidence(boolean b)
    {
        seenPlayerEvidence = b;
    }
    
    
    public void setInitialState()
    {
        currentState = patrol;
    }
    
    
    public void setX(float x)
    {
        xPos = x;
    }
    
    
    public void setY(float y)
    {
        yPos = y;
    }
    
    
    public void createStates()
    {
        System.out.println("creating states for " + this);
        patrol = new Patrol(this);
        alert = new Alert(this);
        setInitialState();
    }
    
    
    public void setJump(boolean b)
    {
        canJump = b;
        // System.out.println("bluey: canJump == " + canJump);
    }   
    
    
    public State getState()
    {
        return currentState;
    }
    
    
    public void resetAccelX()
    {
        // do nothing for now
    }
    
    
    
    public void resetAccelY()
    {
        // do nothing for now
    }
    
    
    public float getMoveSpeed()
    {
        return moveSpeed;
    }
    
    
    public float getJumpSpeed()
    {
        return JUMP_SPEED;
    }
    
    
    public void setYSpeed(float f)
    {
        ySpeed = f;
    }
    
    
    public void setXSpeed(float f)
    {
        xSpeed = f;
    }
    
    
    public int getFacing()
    {
        return facing;
    }
    
    
    public int getHealth()
    {
        return health;
    }
    
    
    public void changeState(State nextState)
    {
        currentState.exit();
        currentState = nextState;
    }
    
    
    public float getYSpeed()
    {
        return ySpeed;
    }
    
    
    public float getXSpeed()
    {
        return xSpeed;
    }
    
    
    public boolean canJump()
    {
        return canJump;
    }

    private void createAnimations()
    {
        System.out.println("bluey -> creating animations");
        
        //CoreImage leftStand1 = AnimCreator.loadImage(leftStandPath);
        CoreImage leftStand1 = CoreImage.load(leftStandPath);
        CoreImage rightStand1 = leftStand1.mirror();        
        
        // standing anims
        /*
        CoreImage[] leftStandImages = { leftStand1 };
        CoreImage[] rightStandImages = { rightStand1 };  

        leftStandAnim = new Animation(leftStandImages, 
                                      Actor.ANIM_DURATION, true);
        rightStandAnim = new Animation(rightStandImages,
                                      Actor.ANIM_DURATION, true);
        */
                                                
        // walking anims
        //CoreImage[] type;
        CoreImage[] leftWalkImages = new CoreImage[
            leftWalkPaths.length];
        leftWalkImages = (CoreImage[])AnimCreator.getImagesFromPaths(
            leftWalkPaths).toArray(leftWalkImages);
            
        CoreImage[] rightWalkImages = new CoreImage[
            leftWalkPaths.length];
        rightWalkImages = 
            (CoreImage[])AnimCreator.getHorizontallyFlippedCopy(
                    rightWalkImages).toArray(rightWalkImages);
        
        leftWalkAnim = new AnimatedImage(leftWalkImages);
        rightWalkAnim = new AnimatedImage(rightWalkImages);
        
        facing = Direction.LEFT;
        sprite.setImage(leftStandAnim);
    }
        
    /*
     * Returns the height of Bluey's Tile in the Map Editor, Tiled.
     */ 
    public float getTileHeight()
    {
        return tileHeight;
    }
    
    
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
    
    
    public Rect getZone()
    {
        return getLevel().getActorZone(this);
    }
    
    
    
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
     * Updates the current State (in turn performing its current Action)
     * and looking for any changes to Bluey.
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
        f = getFaci();
        if(f == Direction.RIGHT)
        {
            sprite.setImage(rightStandAnim);
        }
        else if(f == Direction.LEFT)
        {
            sprite.setImage(leftStandAnim);   
        }
        else throw new IllegalStateException(
                            "standing dir neither left or right");
    }
    
    // Woah there!
    private void applyFriction()
    {
        xSpeed *= level.FRICTION;
    }
    
    
    public void applySpeed(int dir)
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
    
    public float getEyePosX()
    {
        return xPos + (
            AnimCreator.getCurrentFrameRect(this).width / 2);
    }
    
    
    public float getEyePosY()
    {
        return yPos + EYE_OFFSET_HEIGHT;
    }
    
    
    public boolean isMoving()
    {
        return moving;   
    }

   
    
    
    public void setHasSeenPlayer(boolean b)
    {
        hasSeenPlayer = b;
    }
    
    
    public void setCanSeenPlayer(boolean b)
    {
        canSeePlayer = b;
        
        if(b && !hasSeenPlayer())
        {
            setHasSeenPlayer(true);
        }
    }
    
    
    public boolean canSeePlayer()
    {
        return canSeePlayer;
    }
    
    
    public boolean hasSeenPlayer()
    {
        if(!hasSeenPlayer)
        {
            System.out.println("so startled! - " + this);
            jump();
        }
        return hasSeenPlayer;
    }
    
    
    public float getMaxFallSpeed()
    {
        return MAX_Y_SPEED;
    }
    
    
    public void die()
    {
        System.out.println("Die, you, " + this);
        // n i
    }
    
    
    private void updateProjectiles()
    {
        
    }
    
    public void draw(CoreGraphics g)
    {
        //System.out.println("drawing Bluey: " + getX() + ", " + getY());
        g.drawImage(sprite.getImage(), (int)getX(), (int)getY());

        drawProjectiles(g);

        final Look look = null;
        
        // this is just debug code; it won't be here for ever :)
        if(currentState.getClass().equals(patrol.getClass()))
        {
            Action act = patrol.getCurrentAction();
            act.draw(g);
        }
    }

/*
    
    public CoreGraphics getGraphics()
    {
        return g;
    }
*/
    
    private void drawProjectiles(CoreGraphics g)
    {
        
    }
    
    public float getX(){ return xPos; }   
    public float getY(){ return yPos; } 
}
