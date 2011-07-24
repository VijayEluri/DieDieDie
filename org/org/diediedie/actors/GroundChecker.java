package org.diediedie.actors;
import org.diediedie.actors.Actor;

/**
 * Relays information to an Actor regarding the geometry of the 
 * ground collision tiles immediately in front of them in their currently
 * faced direction.
 */ 
public class GroundChecker
{
    public static boolean canMove(Actor a)
    {
        return false;
    }
    
    /*public static boolean canMoveTo(Actor a, float x, float y)
    {
        return false;
    }*/
}
