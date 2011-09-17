package org.diediedie;

import java.util.List;
import java.util.Properties;


import org.diediedie.actors.LevelObject;
import org.newdawn.slick.Graphics;


/*
 * An Object layer from a TiledMap
 */
public class ObjectLayer implements UpdatableLayer, DrawableLayer
{
	protected List<LevelObject> objects;
	protected String name;
	protected int index;
	protected Properties props;
	
	public ObjectLayer(int index, String name, List<LevelObject> objects)
	{
		this.index = index;
		this.name = name;
		this.objects = objects;
	}

	@Override
	public void draw(Graphics g) 
	{
		for(LevelObject lo : objects)
		{
			lo.draw(g);
		}
	}
	
	@Override
	public void update() 
	{
		for(LevelObject lo : objects)
		{
			lo.update();
		}
	}
}