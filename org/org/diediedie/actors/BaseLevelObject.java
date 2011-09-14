package org.diediedie.actors;

import org.diediedie.Level;
import org.diediedie.Tile;
import org.newdawn.slick.Graphics;

public abstract class BaseLevelObject implements LevelObject 
{
	protected Level level;
	protected float xPos,
					yPos;
	
	protected Tile tile;
	
	public BaseLevelObject(Level l, Tile t)
	{
		setLevel(l);
		tile = t;
	}
	
	@Override
	public void update() 
	{
		// implemented in subclass
	}

	@Override
	public void draw(Graphics g) 
	{
		// implemented in subclass
	}

	@Override
	public void setLevel(Level l) 
	{
		level = l;
	}

	@Override
	public Level getLevel() 
	{
		return level;
	}

	@Override
	public float getX() 
	{
		return xPos;
	}

	@Override
	public float getY()
	{
		return yPos;
	}
}
