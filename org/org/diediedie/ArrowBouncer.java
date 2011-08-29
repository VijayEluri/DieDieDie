package org.diediedie;

import org.diediedie.actors.LevelObject;
import org.diediedie.actors.tools.AnimCreator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class ArrowBouncer implements LevelObject, Bounded
{
	final String IMAGE_PATH = "data/arrow_bounce_tile.png";
	Image image;
	Level level;
	Tile tile;
	private Rectangle rect;
	
	public ArrowBouncer(Tile t, Level l)
	{
		setLevel(l);
		image = AnimCreator.loadImage(IMAGE_PATH);
		tile = t;
		createRect();
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
		g.draw((Shape)rect);
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

}
