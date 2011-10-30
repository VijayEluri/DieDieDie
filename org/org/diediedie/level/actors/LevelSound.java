package org.diediedie.level.actors;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/*
 * Superclass of all Sounds in the game.
 * 
 * Wraps slick.Sound.
 */
public class LevelSound extends Sound
{
	public LevelSound(String path) throws SlickException
	{
		super(path);
	}	
}
