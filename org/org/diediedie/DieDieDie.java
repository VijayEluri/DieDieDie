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

import org.diediedie.actors.Enemy;
import org.diediedie.actors.Player;
import org.diediedie.actors.tools.Direction;
import java.io.FileInputStream;
import java.io.File;
import java.util.Date;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;

/**
 * DieDieDie
 */
public class DieDieDie extends BasicGame
{
	private Player player;

	private InputProvider inputProv = null;

	private static int xSize = 1024, ySize = 768;
	
	private Level level1 = null, currentLevel = null;

	private final String TILE_SETS_PATH = "data",
						 LEVEL_1_PATH = "data/level1_1024x768.tmx",
						 LEVEL_ONE_NAME = "Level 1";

	private boolean gameStarted;

	private Date now = new Date();

	private long gameStartTime;

	private long currentTime;

	private ScalableGame scalableWrapper;

	private Level arrowBounceTest;

	public static final float GRAVITY = 0.2f;//.25f;

	/**
	 * Create the game
	 */
	public DieDieDie() 
	{
		super("DieDieDie");
		scalableWrapper = new ScalableGame(this, xSize, ySize);
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
		currentLevel = level1;/*arrowBounceTest;level1*/;
			
		// load player and associate with the level data
		player = level1.getPlayer();

		// hook up the player to the input provider
		player.associateInputProvider(inputProv, container.getInput());
	}

	/*
	 * Sets up the Levels objects
	 */
	private void createLevels()
	{
		//level1 = loadLevel(LEVEL_ONE_NAME, LEVEL_1_PATH, TILE_SETS_PATH,
			//	Direction.LEFT, GRAVITY);
		level1 = loadLevel(
				"Factory", 
				"data/levels/Factory_1_R1024x768_T32x32.tmx",
				"data");
		assert level1 != null;
		//arrowBounceTest = loadLevel("Arrow One Name", 
			//	"data/Bouncy_Arrows_1024x768x16x16.tmx",
				//TILE_SETS_PATH, Direction.RIGHT, GRAVITY);
	}

	/*
	 * Utility method to load a Level into the current game. This just acts as a
	 * nice wrapper for the lavel path.
	 */
	private Level loadLevel(String name,
			                String levelPath, 
			                String tileSetsPath)
	{
		try
		{
			FileInputStream in = new FileInputStream(new File(levelPath));
			return new Level(name, in, tileSetsPath);
		} 
		catch(Exception e) 
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
		if(!gameStarted)
		{
			gameStarted = true;
			/*
			 *  Record the game start time  in order to trigger
			 *  time-based events on the current level (for 
			 *  debugging purposes)
			 */
			gameStartTime = System.currentTimeMillis();
		}
		currentLevel.update();
	}
	
	public long millisSinceGameStart()
	{
		return currentTime - gameStartTime;
	}
	
	public long getCurrentTime()
	{
		return System.currentTimeMillis();
	}
	
	/**
	 * Render the updated game state.
	 */
	public void render(GameContainer container, Graphics g)
	{
		try 
		{
			currentLevel.render(0, 0, g);
		} 
		catch (SlickException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//player.draw(g);
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
