package org.diediedie.actors;
import org.diediedie.actors.Direction;
import org.diediedie.actors.Actor;
import org.diediedie.actors.AnimCreator;

/**
 * Utility class checking for collisions and for aligning an Actor 
 * against a collision Tile following a collision
 */ 
public class ActorAligner
{
    public static final float INCR = 0.01f;
    
    public static boolean collides(Actor a)
    {
        if(a.getLevel().collides(AnimCreator.getCurrentFrameRect(a)))
        {
            return true;
        }
        return false;
    }
    
    /**
     * Aligns a collided Actor to a non-colliding part of the Level.
     */ 
    public static void alignToObstacle(Actor a)
    {
        // finally, put the Player as close to the obstacle as possible
        while(!collides(a))
        {
            // here 'canJump' is used to discern the direction of the
            // collision; i.e. a 'true' value indicates (hopefully) 
            // that the player *fell* into this collision rather than
            // headbutted it... 
            
            if(a.canJump())
            {
                a.setY(a.getY() + INCR);
            }
            else
            {
                a.setY(a.getY() - INCR);
            }
        }
        if(a.canJump())
        {   
            a.setY(a.getY() - INCR);
        }
        else
        {
            a.setY(a.getY() + INCR);
        }
    }    
    
}
