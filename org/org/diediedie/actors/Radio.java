package org.diediedie.actors;

import java.util.Properties;

import org.diediedie.Level;
import org.diediedie.Tile;
import org.diediedie.actors.tools.AnimCreator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/*
 * A Radio used for two-way communication. Both
 * transmissions occur simultaneously
 */
public class Radio implements LevelObject, Transmitter		   
{
	public enum Color
	{
		Red, Blue, Green
	}
	
	protected Image image = null;
	
	protected float x, y;
	
	protected Level level;
	
	protected Radio.Color visColor;
	
	
	
	public Radio(Properties p) 
	{
		// perhaps refactor some of this into BaseLevelObject
		System.out.println("Creating Radio " + p);
		x = (Integer) p.get("x");
		y = (Integer) p.get("y");
		
		System.out.println("position : " + x + ", " + y);
		visColor = getVisColor((String)p.get("visColor"));
		System.out.println("Radio.Color : " + visColor);
		image = AnimCreator.loadImage("data/" + (String)p.get("imagepath"));
		assert image != null;
		
	}
	
	private Radio.Color getVisColor(String s)
	{
		for(Radio.Color c: Radio.Color.values())
		{
			if(s.equalsIgnoreCase(c.toString()))
			{
				return c;
			}
		}
		return null;
	}
	
	@Override
	public void draw(Graphics g)
	{
		//System.out.println("draw radio " + visColor);
		g.drawImage(image, x, y);
	}

	@Override
	public void transmit(Signal s) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveTransmission(Signal s) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(Transmitter t) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
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
		return x;
	}

	@Override
	public float getY() 
	{
		return y;
	}

	@Override
	public void setX(float x) 
	{
		this.x = x;
	}

	@Override
	public void setY(float y) 
	{
		this.y = y;
	}
}