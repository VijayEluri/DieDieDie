package org.diediedie;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public abstract class BaseSceneryLayer implements DrawableLayer
{
	protected Graphics graphics;
	protected MapLayer mapLayer;
	protected int drawMode;
	protected final String DRAW_MODE_STRING = "drawmode";	
	
	public BaseSceneryLayer(MapLayer ml) 
	{
		mapLayer = ml;
		parseGraphicsMode();
	}
	
	protected void parseGraphicsMode() 
	{
		assert mapLayer != null;
		String drawStr = mapLayer.level.getLayerProperty(
				mapLayer.index, DRAW_MODE_STRING, null);
		System.out.println("parseGraphicsMode layer " 
				+ mapLayer.index + ", " + drawStr);
		
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
	
	public void draw(Graphics g) 
	{
		g.setDrawMode(drawMode);
		mapLayer.level.render(0, 0, mapLayer.index);
		g.setDrawMode(Graphics.MODE_NORMAL);
	}
}
