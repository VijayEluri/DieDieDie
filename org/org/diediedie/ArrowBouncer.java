package org.diediedie;

import org.diediedie.actors.LevelObject;
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public class ArrowBouncer implements LevelObject, Bounded
{
	public static final int SPEED_BOOST = 1;
	final String IMAGE_PATH_RIGHT = "data/bouncer_right_debug.png",
			     IMAGE_PATH_LEFT = "data/bouncer_left_debug.png"; 
	Image image;
	Level level;
	Tile tile;
	private Rectangle rect;
	private final int RIGHT_ANGLE_CHANGE = 90;
	private final int LEFT_ANGLE_CHANGE = -RIGHT_ANGLE_CHANGE;
	Direction dir;
	
	public ArrowBouncer(Tile t, Level l, Direction d)
	{
		setLevel(l);
		if(d == Direction.LEFT)
		{
			image = AnimCreator.loadImage(IMAGE_PATH_LEFT);
		}
		else
		{
			image = AnimCreator.loadImage(IMAGE_PATH_RIGHT);
		}
		tile = t;
		dir = d;
		createRect();
	}
	
	public Direction getDirection()
	{
		return dir;
	}
	
	public int getAngleChange()
	{
		/*
		 * Returns the value to add to the Arrow's
		 * angle.
		 */
		if(dir == Direction.LEFT)
		{
			return LEFT_ANGLE_CHANGE;
		}
		return RIGHT_ANGLE_CHANGE;
	}
	
	private void createRect()
	{
		this.rect = new Rectangle(
			tile.xPos, tile.yPos, image.getWidth(), image.getHeight());			
	}
	
	public Image getImage()
	{
		return image;
	}
	
	/*public long getTimeLastUsed()
	{
		return lastUsed ;
	}*/
	
	/*public void setUsed()
	{
		lastUsed = System.currentTimeMillis();
	}
	
	public boolean canUse()
	{
		boolean ret = (System.currentTimeMillis()
				- lastUsed) 
				   	>= 
				  USE_DELAY;
		System.out.println("canUse " + ret);
		return ret;
	}*/
	
	@Override
	public void setLevel(Level l) 
	{
		level = l;	
	}	
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void draw(Graphics g) 
	{
		g.drawImage(image, tile.xPos, tile.yPos);
		//g.draw((Shape)rect);
	}

	@Override
	public Level getLevel() 
	{
		return level;
	}

	@Override
	public float getX() 
	{
		
		return tile.xPos;
	}

	@Override
	public float getY() 
	{
		return tile.yPos;
	}

	@Override
	public Rectangle getRect() 
	{
		return rect;
	}

	public int getSpeedBoost()
	{
		return SPEED_BOOST;
	}
}
