package org.diediedie.actors;

import org.diediedie.Level;
import org.diediedie.Tile;

import org.diediedie.actors.statemachine.StateType;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


/*
 * A movable and collidable object that can move
 * up and down only.
 * 
 * Starts in a stationary state. Initial direction and distance
 * (in Tiles) to travel supplied from the TiledMap tile
 * property.
 * 
 * Doesn't use StateMachine here, as it's unnecessarily 
 * heavyweight.
 * 
 * Uses the original map Tile id to hook it up to a Switch.
 * 
 */
public class Elevator extends BaseLevelObject implements Callable
{
	//the number of tiles travelled from the start tile
	protected int distance;
	
	//the layer in the tmx map file it was found on
	protected int layerIndex;
			
	
	// maximum speeds, for each possible direction.
	final float DOWN_MAX_SPEED = 5.2f,
				UP_MAX_SPEED = -4.9f;
	
	// the direction the elevator moves from its starting tile
	// with distance, this is used to work out where it ends up
	protected Direction direction = null;
	
	// the current speed of the Elevator
	protected float speed;
	
	// the image from the tiled map
	protected Image image;
	
	
	protected Level level;
	

	
	// states
	//protected Idle idle;
	
	protected StateType currentState;
	
	// ^always idle or active

	protected boolean fsmRunning;

	// how rapidly the speed changes on the elevator
	protected float speedIncrUp = 0.1f, 
					speedIncrDown = 1.3f;
	
	// the top and bottom coordinates on the level
	// of where the elevator moves
	private float yTop, yBottom;

	private boolean xPosSet = false;
	
	/*
	 * An Elevator
	 */
	public Elevator(Level map, Tile t)
	{
		super(map, t);
		
		/*
    	 * Get the elevator speed, distance and direction to travel 
    	 * from the elevator tile. 
    	 */
		String dirStr = (String)map.getTileProperty(
				t.id, "direction", null);
		
		System.out.println("direction tile string : " + dirStr);
    	direction = Direction.convertToDirection(dirStr);
    	
    	assert direction != null;
    	String distanceStr = map.getTileProperty(
    							t.id, "distance", null);
    	
    	distance = Integer.parseInt(distanceStr);
    	String speedStr = map.getTileProperty(t.id, "speed", null);
    	speed = Float.parseFloat(speedStr);
    	image = map.getTileImage(t.xCoord, t.yCoord, t.layer);
    	assert image != null;

    	findPositions();
    	
    	System.out.println(
    			"Elevator at " + t.xCoord + ", " + t.yCoord);
	}
	
	public final int getID()
	{
		return tile.id;
	}
	
	private void findPositions()
	{
		/*
		 * work out where the top / bottom travel-positions for the
		 * elevator are based on the Direction parsed from the map tile
		 */
		if(direction == Direction.DOWN)
		{
			yTop = tile.yPos - tile.tileHeight;
			yBottom = yTop + (tile.tileHeight * distance);
		}
		else if(direction == Direction.UP)
		{	
			yBottom =  tile.yPos - tile.tileHeight;
			yTop = yBottom - (tile.tileHeight * distance);
		}
		else
		{
			System.out.println(
						"Elevator.findPositions() : bad direction " 
						+ direction);	
			System.exit(-1);
		}
		xPos = tile.xPos;
		yPos = tile.yPos - tile.tileHeight;
	}
	
	/*
	 * Updates the Elevator. We're not using the
	 * StateMachine interface because it is 
	 * unnecessarily large - the elevator only
	 * has two very simple states, moving and
	 * stationary.
	 */
	@Override
	public void update() 
	{
		// TODO Check if we should change the state
		
		if(currentState == StateType.ACTIVE)
		{
			updatePosition();
		}
	}

	@Override
	public void draw(Graphics g) 
	{
		g.drawImage(image, getX(), getY());
	}
	 
	/*
	 * Called only when the Elevator has been instructed to move.
	 */
	private void updatePosition()
	{
		// TODO remove this assert
		assert currentState == StateType.IDLE;
		
		if(direction == Direction.DOWN)
		{				
			if(speed < DOWN_MAX_SPEED)
			{
				speed += speedIncrDown;
			}
			if(yPos < yBottom)
			{
				setY(yPos + speed);
			}
			else
			{
				alignElevator();
				setIdle();
			}
		}
		else if(direction == Direction.UP)
		{				
			if(speed > UP_MAX_SPEED)
			{
				speed -= speedIncrUp;
			}
			if(yPos > yTop)
			{
				setY(yPos + speed);
				// ^ speed is negative, hence the addition
			}
			else
			{
				alignElevator();
				setIdle();
			}
		}	
		else
		{
			System.out.println("Elevator.updatePosition() - bad direction");
			System.exit(-1);
		}
	}
	
	/*
	 * Sets the current state to IDLE, from ACTIVE.
	 * [Elevator has stopped]
	 */
	private void setIdle()
	{
		assert currentState == StateType.ACTIVE;
		currentState = StateType.IDLE;
		speed = 0f;
	}

	/*
	 * Aligns the Elevator to its vertical destination.
	 */
	private void alignElevator()
	{
		// TODO this!
		System.out.println("alignElevator not implemented");
	}

	/*
	 * We're overriding this from the BaseLevelObject
	 * implementation and doing nothing because the
	 * idea of the Elevator class is that it can only
	 * be set once.
	 */
	@Override
	public void setX(float x) 
	{
		if(!xPosSet)
		{
			xPos = x;
			xPosSet = true;
		}
	}

	@Override
	public void call(Caller c) 
	{
		// TODO Auto-generated method stub
		
	}
}
