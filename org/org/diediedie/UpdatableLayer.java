package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.actors.LevelObject;
import org.newdawn.slick.Graphics;

/*
 * Contains objects that can be updated and drawn.
 */
public class UpdatableLayer extends BaseLayer
{
	protected List<LevelObject> objects;
	
	public UpdatableLayer(MapLayer ml) 
	{
		super(ml);		
		objects = new ArrayList<LevelObject>();
	}
	
	public void update()
	{
		updateObjects();
	}
	
	protected void updateObjects()
	{
		for(LevelObject l : objects)
		{
			l.update();
		}
	}


	
}
