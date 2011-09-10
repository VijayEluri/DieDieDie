package org.diediedie;

import org.newdawn.slick.Graphics;

public interface DrawableLayer extends LevelLayer
{
	void draw(int x, int y, Graphics g);
}
