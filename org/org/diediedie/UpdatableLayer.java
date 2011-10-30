package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

/*
 * Contains objects that can be updated and drawn.
 */
public class UpdatableLayer extends BaseLayer
{
	protected List<Entity> objects;
	
	public UpdatableLayer(MapLayer ml) 
	{
		super(ml);		
		objects = new ArrayList<Entity>();
	}
	
	public void update()
	{
		updateObjects();
	}
	
	protected void updateObjects()
	{
		for(Entity l : objects)
		{
			l.update();
		}
	}


	
}
