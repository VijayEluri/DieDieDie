package org.diediedie;


import java.io.*;
import java.util.*;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.*;


/**
 * DieDieDie AKA Captain EXCITEMENT
 */ 
public class DieDieDie extends BasicGame
{   
    private final float playerXLevel1 = 30;
	private final float playerYLevel1 = 100;
    
    //private float gravity = 4;
    public static final float GRAVITY = 0.49f;
    
    private static int xSize = 640, ySize = 480;
    
	private Level level1 = null, currentLevel = null;	
        
    private final String TILE_SETS_PATH = "data", 
                         LEVEL_1_PATH = "data/level1.tmx",
                         LEVEL_ONE_NAME = "Level 1";
    
	private Player player;
    
    /**
     * Creates the GAME...
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
        
        // load all the levels
		level1 = loadLevel(LEVEL_ONE_NAME, LEVEL_1_PATH, TILE_SETS_PATH,
                            playerXLevel1, playerYLevel1, Direction.LEFT,
                            GRAVITY); 
        
        
        currentLevel = level1;

        // load player and associate with the level data
        player = new Player(currentLevel);
        
        
	}
    
    
    
    /*
     * Utility method to load a Level into the current game. This just
     * acts as a nice wrapper for the lavel path.
     */ 
    private Level loadLevel(String name, String levelPath, 
                            String tileSetsPath, float x, float y, 
                            Direction startDir, float gravity)
    {
        try 
        {
            FileInputStream in = new FileInputStream(new File(levelPath));
            return new Level(name, in, tileSetsPath, x, y, startDir,
                            gravity);
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
        player.setStandingAnim();
        
        // left and right movement
		if(container.getInput().isKeyDown(Input.KEY_LEFT)) 
        { 
            player.move(Direction.LEFT);
        }
		else if(container.getInput().isKeyDown(Input.KEY_RIGHT)) 
        { 
            player.move(Direction.RIGHT);
        }
        else
        {
            player.decreaseAcceleration();
        }
        // jump
        if(container.getInput().isKeyDown(Input.KEY_UP))
        {
            player.jump();
        }
        player.update();
	}
    
    /**
     * Render the updated game state.
     */ 
	public void render(GameContainer container, Graphics g)  
    {
		currentLevel.render(0, 0);
        player.draw(g);
        //g.draw(player.getCurrentFrameRect());
	}
    
    
    /**
     * For running the program from the command line. 
     */ 
	public static void main(String[] argv) throws SlickException 
    {
		AppGameContainer container 
            = new AppGameContainer(new DieDieDie(), xSize, ySize, false);
		container.start();
	}
}
