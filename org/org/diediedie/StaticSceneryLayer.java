package org.diediedie;

import org.newdawn.slick.SlickException;

/*
 * Drawable layer that does not move or change.
 * 
 * Drawing implementation is in BaseSceneryLayer.
 */
public class StaticSceneryLayer extends BaseSceneryLayer
{
	MapLayer mapLayer;
	
	public StaticSceneryLayer(MapLayer ml) throws SlickException
	{
		super(ml);
		mapLayer = ml;
	}
}
