package org.diediedie;

import java.util.List;

import org.newdawn.slick.Graphics;

public interface LevelLayer
{
	public String getName();
	void draw(Graphics g);
	public void setObjects(List<Entity> l);
	public List<Entity> getObjects();
}
