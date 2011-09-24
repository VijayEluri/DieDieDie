package org.diediedie;

import java.util.List;

import org.diediedie.actors.LevelObject;
import org.newdawn.slick.Graphics;

public interface LevelLayer
{
	public String getName();
	void draw(Graphics g);
	public void setObjects(List<LevelObject> l);
	public List<LevelObject> getObjects();
}
