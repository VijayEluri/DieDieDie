package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.actors.Elevator;
import org.diediedie.actors.LevelObject;
import org.diediedie.actors.MovableObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/*
 * Comprised of switches, elevators, arrow bouncers, and anything else
 * that can be interacted with AND drawn underneath the PlayerLayer.
 */
public class ActiveSceneryLayer extends BaseSceneryLayer 
								implements UpdatableLayer
{
	MapLayer mapLayer;
	List<LevelObject> objects;
	
	public ActiveSceneryLayer(MapLayer ml) throws SlickException
	{
		super(ml);
		mapLayer = ml;
		parseObjects();
	}
	
	@Override
	public void draw(Graphics g)
	{
		for(LevelObject o : objects)
		{
			o.draw(g);
		}
	}

	@Override
	public void update()
	{
		for(LevelObject o : objects)
		{
			o.update();
		}
	}
	
	/*
	 * Parse the movable objects on this layer.
	 */
	private void parseObjects()
	{
		objects = new ArrayList<LevelObject>();
		
		for(Tile t : mapLayer.tiles)
        {
			if(t.properties.get("type").equalsIgnoreCase("elevator"))
			{
				objects.add(new Elevator(mapLayer.level, t));
			}
        }
	}
}
