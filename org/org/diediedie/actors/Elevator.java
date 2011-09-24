package org.diediedie.actors;

import java.util.Properties;

import org.diediedie.Level;
import org.diediedie.Tile;

import org.diediedie.actors.statemachine.StateType;
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


/*
 * A non-stationary collidable object that can move
 * up and down only. Not using the 
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
public class Elevator extends BaseLevelObject implements SignalReceiver
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
	
	private int height, width;
	
	private Switch elSwitch;
	
	/*
	 * An Elevator
	 */
	public Elevator(Properties p)
	{
		System.out.println("new Elevator ");
		parseBaseObject(p);
		System.out.println("\tposition : " + xPos + ", " + yPos);
		height = (Integer)p.get("height");
		width = (Integer)p.get("width");
		direction = Direction.convertToDirection(
							  (String)p.get("direction"));
		assert direction != null;
		distance = Integer.parseInt((String)p.get("distance"));
		assert distance > 0;
		assert image != null;
		
		findPositions();
	}
	
	
	private void findPositions()
	{
		/*
		 * work out where the top / bottom travel-positions for the
		 * elevator are based on the Direction parsed from the map tile
		 */
		if(direction == Direction.DOWN)
		{
			yTop = yPos;
			yBottom = yTop + (height * distance);
		}
		else if(direction == Direction.UP)
		{	
			yBottom = yPos;
			yTop = yBottom - (height * distance);
		}
		else
		{
			System.out.println(
				"Elevator.findPositions() : bad direction " 
				+ direction);	
			System.exit(-1);
		}
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
				// ^ speed is negative, so we use addition
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
			this.xPos = x;
			xPosSet = true;
		}
	}

	/*
	 * Elevators can only receive Switch signals from the
	 * source parsed from the tiled map object layer.
	 */
	@Override
	public void receive(Signal s) 
	{
		// check source transmitter is correct type
		// check signal is Switch.UP or Switch.DOWN	
	}

}
