package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.actors.MovableObject;
import org.newdawn.slick.Graphics;

/*
 * Comprised of switches, elevators, arrow bouncers, and anything else
 *  that can be interacted with that is drawn underneath the PlayerLayer.
 */
public class ActiveSceneryLayer implements UpdatableLayer, DrawableLayer
{
	MapLayer mapLayer;
	
	List<MovableObject> objects;
	
	public ActiveSceneryLayer(MapLayer ml)
	{
		mapLayer = ml;
		parseObjects();
	}
	
	@Override
	public void draw(int x, int y, Graphics g)
	{
		for(MovableObject o : objects)
		{
			o.draw(g);
		}
	}

	@Override
	public void update()
	{
		for(MovableObject o : objects)
		{
			o.update();
		}
	}
	
	/*
	 * Parse the movable objects on this layer.
	 */
	private void parseObjects()
	{
		objects = new ArrayList<MovableObject>();
		
		for(Tile t : mapLayer.tiles)
        {
			if(t.properties.get("type").equalsIgnoreCase("elevator"))
			{
				objects.add(new Elevator(mapLayer.level, t));
			}
        }
	}
}
