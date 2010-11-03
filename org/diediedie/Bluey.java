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
    private List<State> states = new ArrayList<State>();  
    private boolean setUp = false, canJump = false,
                    yCollision = false, xCollision = false;
    public static final int MAX_HEALTH = 100;
    private int health;
    private Direction facing = null;
    private Level level;
    private float xPos, yPos, tileHeight;

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
            health = MAX_HEALTH;
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
    
    /**
     * Sets Bluey's possible States
     */ 
    @Override
    public void setStates()
    {
        
    }
    @Override
    public void move(Direction d)
    {
        
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
    public void setState(State nextState)
    {
        
    }
    @Override
    public State getState()
    {
        return null;        
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
    
    /**
     * Returns true if Bluey has hit a collision Tile.
     */ 
    @Override
    public boolean collides()
    {
        return true;
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
