package org.diediedie.level.actors.tools;

import org.diediedie.level.actors.Actor;
import org.newdawn.slick.geom.Shape;

/**
 * Relays information to an Actor regarding the geometry of the 
 * ground collision tiles immediately in front of them in their currently
 * faced direction.
 */ 
public class GroundChecker
{
	/*
	 * !!!!!! ONLY APPLIES TO LEFT AND RIGHT DIRECTIONS!!!!!
	 * 
	 * NOTE: This method assumes that Actor.canJump() evaluates to true.
	 * 
	 * Returns true if the Actor can continue walking in the given 
	 * Direction without falling.
	 * 
	 * Returns false if the Actor cannot walk any further in the given 
	 * Direction.
	 */
	/*public static boolean canContinueMoving(Actor a, Direction d)
	{
		//assert a.canJump();
		//System.out.println("\tcanContinueMoving...");
		//assert (d == Direction.LEFT) || (d == Direction.RIGHT);
		Shape zone = getWalkableZone();
				//a.getZone();

		if(zone == null)
		{
			
			System.out.println("Zone is null!");
			return true;
			//System.exit(-1);
		}
		
		/*
		 * Actor is on a walkableZone -- check how far it extends it the
		 * Actor's direction (d)
		 */
		/*if(d == Direction.LEFT)
		{
			if(zone.getMinX() < (a.getX() - 2))
			{
				return true;
			}
		}
		else if(d == Direction.RIGHT)
		{
			if(zone.getMaxX() > (a.getX() + (a.getWidth()+ 2) ))
			{
				return true;
			}
		}
		return false;
	}*/
}
