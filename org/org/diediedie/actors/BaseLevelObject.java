package org.diediedie.actors;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.diediedie.Level;
import org.diediedie.Tile;
import org.newdawn.slick.Graphics;

public abstract class BaseLevelObject implements LevelObject 
{
	protected Level level;
	protected float xPos,
					yPos;
	
	protected Tile tile;
	
	/*
	 * Basic constructor (read: old)
	 */
	public BaseLevelObject(Level l, Tile t)
	{
		setLevel(l);
		tile = t;
	}
	
	@Override
	public void update() 
	{// implemented in subclass	
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
	
	@Override
	public void setY(float y) 
	{
		yPos = y;		
	}
	
	@Override
	public void setX(float x)
	{
		xPos = x;
	}
}
