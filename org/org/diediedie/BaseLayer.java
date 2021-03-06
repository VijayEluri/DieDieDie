package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.level.MapLayer;
import org.newdawn.slick.Graphics;


/*
 * Optional base class that implements basic
 * routines most sub-classes will need. 
 */
public abstract class BaseLayer implements LevelLayer
{
	protected Graphics graphics;
	protected MapLayer mapLayer;
	protected int drawMode;
	protected final String DRAW_MODE_STRING = "drawmode";
	private List<Entity> objects;	
	
	public BaseLayer(MapLayer ml) 
	{
		mapLayer = ml;
		parseGraphicsMode();
		objects = new ArrayList<Entity>();
	}
	
	@Override
	public List<Entity> getObjects()
	{
		return objects;
	}	
	@Override
	public String getName()
	{
		return mapLayer.getName();
	}
	
	protected void drawObjects(Graphics g)
	{
		//System.out.println("BaseLayer: drawing objects for " + getName());
		for(Entity l : objects)
		{
			l.draw(g);
		}
	}
	
	public void draw(Graphics g)
	{
		drawScenery(g);
		drawObjects(g);
	}
	
	public void setObjects(List<Entity> l)
	{
		objects = l;
		assert objects != null;
	}
	
	private void parseGraphicsMode() 
	{
		assert mapLayer != null;
		String drawStr = mapLayer.getLevel().getLayerProperty(
				mapLayer.getIndex(), DRAW_MODE_STRING, null);
		
		System.out.println("parseGraphicsMode layer " 
				+ mapLayer.getIndex() + ", " + drawStr);
		
		if(drawStr == null)
		{
			drawMode = Graphics.MODE_NORMAL;
		}
		else if(drawStr.equalsIgnoreCase("blend"))
		{
			drawMode = Graphics.MODE_ALPHA_BLEND;
		}
		else if(drawStr.equalsIgnoreCase("add"))
		{
			drawMode = Graphics.MODE_ADD;
		}
		else if(drawStr.equalsIgnoreCase("screen"))
		{
			drawMode = Graphics.MODE_SCREEN;
		}
		else if(drawStr.equalsIgnoreCase("multiply"))
		{
			drawMode = Graphics.MODE_COLOR_MULTIPLY;
		}
		else if(drawStr.equalsIgnoreCase("map"))
		{
			drawMode = Graphics.MODE_ALPHA_MAP;
		}
		else
		{
			/*
			 * Treat as normal
			 */
			drawMode = Graphics.MODE_NORMAL;
		}	
	}
	
	/*
	 * Draws the content of the TiledMap Layer
	 * in the draw mode parsed from the map.
	 */
	protected void drawScenery(Graphics g) 
	{
		g.setDrawMode(drawMode);
		mapLayer.getLevel().render(0, 0, mapLayer.getIndex());
		g.setDrawMode(Graphics.MODE_NORMAL);
	}
}
