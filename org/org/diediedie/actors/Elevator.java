package org.diediedie.actors;

import org.diediedie.Level;
import org.diediedie.Tile;
import org.diediedie.actors.statemachine.State;
import org.diediedie.actors.statemachine.StateMachine;
import org.diediedie.actors.statemachine.StateType;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

/*
 * A movable and collidable object that can move
 * up and down only.
 * 
 * Starts in an Idle state. Initial direction and distance
 * (in Tiles) to travel supplied from the TiledMap tile
 * property.
 * 
 */
public class Elevator extends BaseLevelObject 
					  implements MovableObject, StateMachine
{
	// number of tiles to travel from the starting tile
	protected int distance;
	
	// the layer in the tmx map file it was found on
	protected int layerIndex;
	
	
	
	// the Tile in the map on which the elevator starts
	// this is parsed from the map
	protected Tile startTile;
	
	// position away from the startTile that the 
	protected float destY;
	
	
	// the direction the elevator moves from its starting tile
	protected Direction direction;
	
	// how fast the elevator moves.
	protected float speed;
	
	protected Image image;
	
	protected Level level;
	
	// states
	protected Idle idle;
	
	protected Active active;
	
	protected State currentState;
	// ^always idle or active

	protected boolean fsmRunning;

	// how rapidly the speed changes on the elevator
	protected float speedIncrUp = 0.1f, 
					speedIncrDown = 1.3f;
	
	// the starting position of the elevator, as derived
	// from the Tile.
	private float startY;
	
	/*
	 * AN Elevator!
	 */
	public Elevator(Level map, Tile t)
	{
		super(map, t);

		/*
    	 * Get the elevator speed, distance and direction to travel 
    	 * from the elevator tile. 
    	 */
    	direction = Direction.convertToDirection(
    		map.getTileProperty(t.id, "direction", null));
    	String distanceStr = map.getTileProperty(
    		t.id, "distance", null);
    	distance = Integer.parseInt(distanceStr);
    	
    	String speedStr = map.getTileProperty(
    							t.id, "speed", null);
    	
    	speed = Float.parseFloat(speedStr);
    	
    	image = map.getTileImage(t.xCoord, t.yCoord, t.layer);
    	assert image != null;
    	
    	setInitialPosition();
    	findDestination();
    	
    	idle = new Idle();
    	active = new Active();
    	
    	System.out.println("Elevator at \n\t" + t);
    	System.out.println("\tDistance " + (destY - t.yPos));
    	start();
	}
	
	private void setInitialPosition()
	{
		xPos = tile.xPos;
		startY = tile.yPos - tile.tileHeight;
		yPos = startY;
	}
	
	@Override
	public void update() 
	{
		updateFSM();
	}

	/*
	 * Calculate the destination position from the start Tile
	 * in the direction parsed from the map
	 */
	private void findDestination()
	{
		if(direction == Direction.DOWN)
		{
			destY = startTile.yPos + (startTile.tileHeight * distance); 
		}
		else if(direction == Direction.UP)
		{
			destY = startTile.yPos - (startTile.tileHeight * distance); 
		}
		/*else
		{
			System.out.println("Illegal Elevator direction : " + 
					direction);
			System.exit(-1);
		}*/
	}
	
	@Override
	public void draw(Graphics g) 
	{
		g.drawImage(image, getX(), getY());
	}

	@Override
	public State getCurrentState() 
	{
		return currentState;
	}

	@Override
	public void start() 
	{
		currentState = idle;
		fsmRunning = true;
	}

	@Override
	public void stop() 
	{
		// stops the FSM - unlikely to be used - unless we want 
		// to break an elevator dynamically
		fsmRunning = false;
	}

	@Override
	public boolean isRunning()
	{
		return fsmRunning;
	}

	@Override
	public boolean isOutOfBounds() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOutOfBounds(boolean b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMoveSpeed() 
	{
		return speed;
	}

	@Override
	public float getXSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getYSpeed() {
		// TODO Auto-generated method stub
		return 0;
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
	public void setYSpeed(float y) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setXSpeed(float x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getMaxFallSpeed() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetAccelX() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetAccelY()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	///////////////////////////////////////////////////////////////////////
	// 						State Machine Logic                          //
	///////////////////////////////////////////////////////////////////////
	
	/*
	 * Controls which State the FSM is in and updates
	 * whichever one it is.
	 */
	private void updateFSM() 
	{
		// first decide if we should change state
		if(currentState.equals(idle))
		{
			// have we reached the destination?
			
			
			
			// anything else? change direction?
		}
		else if(currentState.equals(active))
		{
			
		}
		currentState.update();
	}

	/*
	 * The Elevator has two states : idle (stationary) and
	 * active (moving up or down).
	 */
	class Idle implements State
	{
		@Override
		public void update()
		{
			// Idle = Do Sweet FA
		}

		@Override
		public StateType getType() 
		{
			return StateType.IDLE;
		}
	}
	
	/*
	 * Active moves in the current direction.
	 */
	class Active implements State
	{
		final float DOWN_MAX_SPEED = 5.2f,
					UP_MAX_SPEED = -4.9f;

		
		@Override
		public void update()
		{
			// adjust the *speed*
			if(direction == Direction.DOWN)
			{
				if(speed < DOWN_MAX_SPEED
						&&
					yPos < destY)
				{
					speed += speedIncrDown;
				}
			}
			else if(direction == Direction.UP)
			{
				if(speed < UP_MAX_SPEED
						&&
					yPos > destY)
				{
					speed -= speedIncrUp;
				}
			}
			setX(getX() + getXSpeed());
			setY(getY() + getYSpeed());
		}
		
		@Override
		public StateType getType() 
		{
			return StateType.ACTIVE;
		}
	}
}
