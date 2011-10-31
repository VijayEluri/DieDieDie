package org.diediedie.level.actors;


import java.util.Properties;

import org.diediedie.Entity;
import org.diediedie.level.Level;
import org.diediedie.level.actors.tools.AnimCreator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/*
 * An optional LevelObjet implementation which extracts the position,
 * image and name.
 */
public abstract class BaseLevelObject implements Entity 
{
	protected Level level;
	
	protected float xPos,
					yPos;
	
	protected Image image;
	protected String name;
	protected Properties props;
	
	/*
	 * Extracts basic properties from the Properties 
	 * object parsed form the TiledMap's LayerObject
	 * 
	 * (type is extracted before this in the Level object)
	 */
	protected void parseBaseObject(Properties p)
	{
		props = p;
		xPos = (Integer) props.get("x");
		yPos = (Integer) props.get("y");
		getImage(props);
		assert image != null;
		name = (String) props.get("name");
		assert name != null;
		System.out.println("parseBaseObject : name : " + name);
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public void update() 
	{
		// This must be implemented in a subclass.
	}

	
	/*
	 * Very basic 'draw the image' method
	 */
	@Override
	public void draw(Graphics g) 
	{
		g.drawImage(image, getX(), getY());
	}
	
	/*
	 * Convenience function for extracting the image
	 * from the parsed imagepth in the Properties object. 
	 */
	protected void getImage(Properties p)
	{
		image = AnimCreator.loadImage("data/" + (String)p.get("imagepath"));
		assert image != null;
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
