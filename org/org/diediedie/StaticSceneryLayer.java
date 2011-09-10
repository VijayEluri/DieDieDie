package org.diediedie;

import org.newdawn.slick.Graphics;

/*
 * Drawable layer that does not move or change.
 */
public class StaticSceneryLayer implements DrawableLayer
{
	MapLayer mapLayer;
	
	public StaticSceneryLayer(MapLayer ml)
	{
		mapLayer = ml;
	}
	
	public void draw(int x, int y, Graphics g)
    {
		mapLayer.level.render(x, y, mapLayer.index);
    }
}
