/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */
package diediedie;

import java.io.FileInputStream;
import java.io.File;
import diediedie.level.actors.Player;
import diediedie.level.Direction;
import diediedie.level.Level;
import diediedie.level.Tile;
import pulpcore.scene.Scene2D;
import pulpcore.Stage;
import pulpcore.image.CoreFont;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;
/*
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;
import pulpcore.sprite.FilledSprite;
*/
import pulpcore.Input;
/**
 * DieDieDie - This is the main class that ru(i)ns the game.
 */ 
public class DieDieDie //extends Stage 
{   
    private Player player;
    
    //private InputProvider inputProv = null;    
    
    private static int xSize  = 640, 
                       ySize  = 480,
                       mouseX = 0, 
                       mouseY = 0;
	
    // Only 1 level so far. 
    private Level level1       = null, 
                  currentLevel = null;	
    
    private final String TILE_SETS_PATH = "data", 
                         LEVEL_1_PATH   = "data/level1.tmx",
                         LEVEL_ONE_NAME = "Level 1";
                         
    public static final float GRAVITY = 0.20f;
    
    // Game Controls
    public final int KEY_LEFT  = Input.KEY_A;
    public final int KEY_RIGHT = Input.KEY_D;
    public final int KEY_JUMP  = Input.KEY_W;
    public final int KEY_FIRE  = Input.KEY_MOUSE_BUTTON_1;
    
    /**
     * Create the game
     */ 
	public DieDieDie() 
    {
		super();
	}
    
    /*
     * Load the game components.
     */ 
    public void load()
    {
        // set up levels
        createLevels();
        currentLevel = level1;
        // load player and associate with the level data
        player = new Player(level1);
        System.out.println("loaded!");
    }
    
    /*
     * Sets up the Levels objects
     */ 
    private void createLevels()
    {
        level1 = loadLevel(LEVEL_ONE_NAME,         
                           LEVEL_1_PATH, 
                           TILE_SETS_PATH,
                           Direction.LEFT,
                           GRAVITY);   
        Stage.pushScene(level1);
    }
 
    /*
     * Utility method to load a Level into the current game. This just
     * acts as a nice wrapper for the lavel path.
     */ 
    private Level loadLevel(String name, String levelPath, 
                            String tileSetsPath, int startDir, 
                            float grav)
    {
        try 
        {
            FileInputStream in = new FileInputStream(
                new File(levelPath));
            
            return new Level(name, in, tileSetsPath, startDir, grav);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
        
    /*
     * Reads keyboard input and updates the player accordingly.
     */
    private void checkKeyboardInput()
    {
        // LEFT Control Key
        if(Input.getState(KEY_LEFT) == Input.UP)
        {
            if(player.moving == Direction.LEFT)
            {
                player.setRunning(false);   
            }
        }
        else if(Input.getState(KEY_LEFT) == Input.DOWN)
        {

            player.setMovingDir(Direction.LEFT);
            player.setRunning(true);
        }
        // RIGHT Control Key
        if(Input.getState(KEY_RIGHT) == Input.UP)
        {
            if(player.moving == Direction.RIGHT)
            {
                player.setRunning(false);   
            }
        }
        else if(Input.getState(KEY_RIGHT) == Input.DOWN)
        {
            player.setMovingDir(Direction.RIGHT);
            player.setRunning(true);
        }
        // UP (Jump)
        if(Input.getState(KEY_JUMP) == Input.PRESSED)
        {
            player.jump();
        }
    }
        
    private void updateMousePosition()
    {
        mouseX = Input.getMousePressX();
        mouseY = Input.getMousePressY();
    }
    
    /*
     * Reads mouse input and updates the player accordingly.
     */
    private void checkMouseInput()
    {
        if(Input.isMouseMoving())
        {
           updateMousePosition();
        }
        if(Input.isMousePressed())
        {    
            //updateMousePosition()
            Tile c = player.getLevel().getCollisionTileAt(
                        mouseX, mouseY);
            
            if(c != null)
            {
                CoreSystem.print(
                    "Mouse pressed Tile " + c.xCoord + ", " + c.yCoord);
            }
              
            player.readyArrow(mouseX, mouseY);
        }
        else if(Input.getState(KEY_FIRE) == Input.RELEASED)
        {
            player.releaseArrow();
        }
    }
    
    /**
     * Updates the game's state.
     */ 
	public void update(/*GameContainer container, */int delta) 
    {
        checkMouseInput();
        checkKeyboardInput();
        player.update();
        currentLevel.update();
	}
    
    /**
     * Render the updated game state.
     */ 
/*
	public void render( CoreGraphics g)  
    {
        // old slick code!
		currentLevel.draw(g);
        player.draw(g);
	}
    
*/
    /**
     * For running the program from the command line. 
     */ 
	public static void main(String[] argv) //throws SlickException 
    {
		//AppGameContainer container = new AppGameContainer(
          //  new DieDieDie(), xSize, ySize, false);
		//container.start();
        DieDieDie newGame = new DieDieDie();
        
	}
}
