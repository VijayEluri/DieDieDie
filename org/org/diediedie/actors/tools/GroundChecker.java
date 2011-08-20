package org.diediedie.actors.tools;
import java.util.Map;

import org.diediedie.actors.Actor;
import org.newdawn.slick.geom.Shape;

/**
 * Relays information to an Actor regarding the geometry of the 
 * ground collision tiles immediately in front of them in their currently
 * faced direction.
 */ 
public class GroundChecker
{
	/*
	 * NOTE: This method assumes that Actor.canJump() evaluates to true.
	 * 
	 * Returns true if the Actor can continue walking in the given 
	 * Direction without falling.
	 * 
	 * Returns false if the Actor cannot walk any further in the given 
	 * Direction.
	 */
	public static boolean canContinueMoving(Actor a, Direction d)
	{
		System.out.println("\tcanContinueMoving...");
		Shape zone = a.getZone();
		
		if(zone == null)
		{
			System.out.println("Zone is null!");
			System.exit(-1);
		}
		return false;
	}
    /*public static boolean canMove(Actor a)
    {
        return false;
    }*/
    
    /*public static boolean canMoveTo(Actor a, float x, float y)
    {
        return false;
    }*/
}
