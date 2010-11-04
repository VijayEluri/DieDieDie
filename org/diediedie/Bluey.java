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
public class Bluey implements Enemy, StateMachine
{
    
    private List<State> states;
    private State currentState = null;
    
    private boolean setUp = false, canJump = false,
                    yCollision = false, xCollision = false, 
                    moving = false;
    
    public static final int MAX_HEALTH = 100;
    private int health;
    private Direction facing = null;
    private Level level;
    
    private float xPos, yPos, tileHeight, moveSpeed = 0, oldX, oldY,
                  xSpeed = 0, ySpeed = 0, accelX = 0, accelY = 0;
    
    public final float MAX_Y_SPEED = 20.5f, WALK_SPEED = 3.5f, 
                        RUN_SPEED = 5.1f, JUMP_SPEED = -5.5f,
                        ACCEL_RATE = 0.03f;
    
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
    
    @Override
    public void setMoveSpeed(float f)
    {
        moveSpeed = f;
    }
    
    @Override
    public Level getLevel()
    {
        return level;
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
    public void setX(float x)
    {
        xPos = x;
    }
    
    @Override
    public void setY(float y)
    {
        yPos = y;
    }
    
    /**
     * Set Bluey's possible States
     */ 
    @Override
    public void createStates()
    {
        states = new ArrayList<State>();
        states.add(new Patrol(this));
    }
    
    
    @Override
    public void move(Direction d)
    {
        if(d.equals(Direction.RIGHT))
        {
            //accelerate();
            currentAnim = rightWalkAnim;
            xSpeed = getMoveSpeed();// + accelX;
        }
        else if(d.equals(Direction.LEFT))
        {
            //accelerate();
            currentAnim = leftWalkAnim;
            xSpeed = -getMoveSpeed();// + accelX);
        }
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
    
    @Override
    public State getState()
    {
        return currentState;        
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
        
    /*
     * Returns the height of Bluey's Tile in the Map Editor, Tiled.
     */ 
    public float getTileHeight()
    {
        return tileHeight;
    }
    
    @Override
    public void update()
    {        
        updatePosition();
        updateProjectiles();
    }
    private void updatePosition()
    {
        applyGravity();
        
        // save pre-move position so that if moving causes a collision
        // it can be undone:
        oldX = xPos;
        oldY = yPos;
        
        if(isMoving())
        {
            move(facing);
        }
        
        
    }
    
    @Override
    public boolean isMoving()
    {
        return moving;   
    }

    
    @Override
    public void applyGravity()
    {
        
    }
    @Override
    public boolean canSeePlayer(float playerX, float playerY)
    {
        return false;
    }
    
    @Override
    public float getMaxFallSpeed()
    {
        return MAX_Y_SPEED;
        
    }
    
    public void die()
    {
        
    }

    private void updateProjectiles()
    {
        
    }
    @Override
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
