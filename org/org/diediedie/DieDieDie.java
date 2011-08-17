/*
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */
package org.diediedie;

import org.diediedie.actors.Player;
import org.diediedie.actors.tools.Direction;
import java.io.FileInputStream;
import java.io.File;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;

/**
 * DieDieDie
 */
public class DieDieDie extends BasicGame
{
	private Player player;

	private InputProvider inputProv = null;

	private static int xSize = 640, ySize = 480;
	private Level level1 = null, currentLevel = null;

	private final String TILE_SETS_PATH = "data",
			LEVEL_1_PATH = "data/level1.tmx", LEVEL_ONE_NAME = "Level 1";

	public static final float GRAVITY = 0.20f;

	/**
	 * Create the game
	 */
	public DieDieDie() 
	{
		super("DieDieDie");
	}

	/**
	 * Initialises the game. Of course.
	 */
	public void init(GameContainer container) throws SlickException 
	{
		container.setVSync(true);
		// container.setTargetFrameRate(60);
		inputProv = new InputProvider(container.getInput());

		// set up levels
		createLevels();
		currentLevel = level1;

		// load player and associate with the level data
		player = new Player(level1);

		// hook up the player to the input provider
		player.associateInputProvider(inputProv, container.getInput());
	}

	/*
	 * Sets up the Levels objects
	 */
	private void createLevels()
	{
		level1 = loadLevel(LEVEL_ONE_NAME, LEVEL_1_PATH, TILE_SETS_PATH,
				Direction.LEFT, GRAVITY);
	}

	/*
	 * Utility method to load a Level into the current game. This just acts as a
	 * nice wrapper for the lavel path.
	 */
	private Level loadLevel(String name, String levelPath, String tileSetsPath,
							Direction startDir, float grav)
	{
		try
		{
			FileInputStream in = new FileInputStream(new File(levelPath));

			return new Level(name, in, tileSetsPath, startDir, grav);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Updates the game's state.
	 */
	public void update(GameContainer container, int delta)
	{
		player.update();
		currentLevel.update();
	}

	/**
	 * Render the updated game state.
	 */
	public void render(GameContainer container, Graphics g)
	{
		currentLevel.draw(g);
		player.draw(g);
	}

	/**
	 * For running the program from 		the command line.
	 */
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(
				new DieDieDie(), xSize, ySize, false);
		container.start();
	}
}
