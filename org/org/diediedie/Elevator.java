package org.diediedie;

import org.diediedie.actors.MovableObject;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

public class Elevator implements MovableObject 
{
	/*
	 * Need to add a 'causation' condition i.e. something else from the
	 * level that 
	 */
	// number of tiles to travel from the starting tile
	int distance;
	int layerIndex;
	
	// the Tile in the map that the elevator starts at
	Tile tile;
	
	// the direction the elevator moves from its starting tile
	Direction direction;
	
	// how fast the elevator moves.
	private float speed;
	
	Image image;
	
	public Elevator(TiledMap map, Tile t)
	{
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
    	tile = t;
    	
    	image = map.getTileImage(t.xCoord, t.yCoord, t.layer);
    	
    	
	}
	
	
	@Override
	public void update() 
	{
		
		
	}

	@Override
	public void draw(Graphics g) 
	{

	}

	@Override
	public Level getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLevel(Level l) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOutOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOutOfBounds(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getMoveSpeed() {
		// TODO Auto-generated method stub
		return 0;
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
	public void setX(float x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setYSpeed(float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setXSpeed(float x) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getMaxFallSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetAccelX() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetAccelY() {
		// TODO Auto-generated method stub

	}

}
