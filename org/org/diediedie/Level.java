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

import java.io.*;
import org.diediedie.NavMesh;
import org.diediedie.NavMesh.MeshMaker;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Bluey;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.LevelObject;
import org.diediedie.actors.MovableObject;
import org.diediedie.actors.Player;
import org.diediedie.actors.Projectile;
import org.diediedie.actors.statemachine.BlueyFSM;
import org.diediedie.actors.statemachine.StateMachine;
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;

/**
 * A single level...
 */ 
public class Level extends TiledMap
{
    // where the exit of the level is
    public float exitX, exitY;
    
    // lesser number == more friction. xSpeed is multiplied by this number
    public static final float FRICTION = 0.91f;

	private static final int ILLEGAL_PLAYER_LAYER = -1;
    
    // other way with gravity because gravity is added to the Actors' 
    // ySpeed
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    
    
    private String name;
    private NavMesh navMesh;
    private final String VIS_STR = "isvisible", TRUE_STR = "true", 
                         FALSE_STR = "false";
    
    private final int NOT_PRESENT = 0;
    
    private MapLayer collisionLayer, objectLayer;//, 
                     /*platformLayer;*/
    
    /*
     *  list of the background layers in the order they should be drawn
     */
    private List<Integer> backgroundLayers = null;
    
    //List<LevelObject> objects;
    
    List<ArrowBouncer> bouncers;
    
    private Player player;
    private List<Enemy> enemiesLiving;
    //private List<Enemy> enemiesDead;
    private Tile playerTile = null;
    
	private int levelHeight;
	private int levelWidth;

	private int playerLayer = ILLEGAL_PLAYER_LAYER;

	private MapLayer playerLayerTiles;
    
    /**
     * Create a Level
     */ 
    public Level(String name, InputStream in, String tileSetsPath, 
                 Direction facing, float grav) throws SlickException
    {
        super(in, tileSetsPath);
        this.name = name;
        this.gravity = grav;
        this.playerFacing = facing;
        calculateLevelDimensions();
        
        System.out.println("Level " + name + " has " + getLayerCount() +
                           " tile layers. Mapsize " + this.levelWidth + " by "
                           + this.levelHeight);
        createLayers();
        
        
        
        
        enemiesLiving = new ArrayList<Enemy>();
        //objects = new ArrayList<LevelObject>();
        bouncers = new ArrayList<ArrowBouncer>();
    
        createNavMesh();
    }   
    
    private void createLayers()
    {
    	createBackgroundLayers();
    	addPlayerLayer();
    	collisionLayer = createMapLayer(getLayerIndex("collision"));
        //objectLayer = createMapLayer(getLayerIndex("objects"));
    }
    
    /*
     * Finds the "player" layer. This will be an empty
     */
    private void addPlayerLayer() 
    {	
    	playerLayer = getLayerIndex("player");    	
    	System.out.println("addPlayerLayer :" + playerLayer);
    	playerLayerTiles = createMapLayer(playerLayer);
    	sortObjectLayer(playerLayerTiles);
	}

	/*
     * Creates a list of the integers for those layers
     * in the map that are backgrounds.
     */
    private void createBackgroundLayers()
    {
    	backgroundLayers = new ArrayList<Integer>();
    	final int LAYERS = getLayerCount();
    	
    	for(int i = 0; i < LAYERS; ++i)
    	{
    		if(isVisible(i))
    		{
    			backgroundLayers.add(i);
    		}
    	}
    }
    
    private void calculateLevelDimensions()
    {
    	 levelHeight = this.height * this.tileHeight;
         levelWidth = this.width * this.tileWidth;
    }
    
    public int getLevelWidth()
    {
    	return levelWidth;
    }
    public int getLevelHeight()
    {
    	return levelHeight;
    }
    
    /*public void setLevelArt(String pathToImage)
    {
    	levelArt = AnimCreator.loadImage(pathToImage);
    }*/
    
    private void createNavMesh()
    {
        navMesh = MeshMaker.generateMesh(this);
    }
    
    public void associatePlayer(Player p)
    {
        player = p;        
    }
    
    /*public MapLayer getBackgroundLayer()
    {
        return backgroundLayer;
    }*/
    
    public List<Enemy> getEnemies()
    {
        return enemiesLiving;
    }
    
    /**
     * Returns the shape in the nav mesh that this actor is in/on, if
     * one exists
     */ 
    public Shape getActorZone(Actor a)
    {
        Rectangle r = AnimCreator.getCurrentFrameRect(a);
        
        for(Shape s : getNavMesh().getWalkableZones())
        {
            if(r.intersects(s))
            {
                return s;
            }
        }
        // find current negative space
        /*NegativeSpace n = getNavMesh().getNegativeSpaceFromPoint(
        	new Point(a.getX(), a.getY()));
        
        if(n != null)
        {
        	// look up the walkable from the negative space
        	
        	for(Map.Entry<Shape, List<Shape>> entry
        					: 
        		navMesh.getWalkSpaceMap().entrySet())
        	{
        		for(Shape n2 : entry.getValue())
        		{
        			if(n.getShape() == n2)
        			{
        				return entry.getKey();
        			}
        		}
        	}
        }*/
        
        return null;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public MapLayer getCollisionLayer()
    {
        return collisionLayer;
    }
    
    /**
     * Returns the Tile associated with the Player's start position on 
     * this Level.
     */ 
	public Tile getPlayerTile()
    {
        return playerTile;   
    }
    
    /*
     * Returns all enemiesLiving + the player
     */ 
    public List<Actor> getActors()
    {
        List<Actor> actors = new ArrayList<Actor>();
        actors.addAll(enemiesLiving);
        actors.add(player);
        return actors;
    }
    
    /**
     * Updates everything on the level.
     */ 
    public void update()
    {
        updateEnemies();
    }

    /**
     * Updates the behaviour and position etc of all enemiesLiving on the
     * level
     */ 
    private void updateEnemies()
    {
        ListIterator<Enemy> lit = enemiesLiving.listIterator();
        
        while(lit.hasNext())
        {
            Enemy e = lit.next();
            if(e.getHealth() <= 0 || e.isOutOfBounds())
            {
                e.die();
                System.out.println("Removing " + e);
                lit.remove();
            }
            else
            {
                e.update();
            }
        }
    }
    
    /*
     * Returns true if the Projectile P is in a collision with
     * an ArrowBouncer on this level.
     */
    public ArrowBouncer collidesBouncer(Projectile p)
    {
    	final float x = p.getX(), y = p.getY();
    	
    	for(ArrowBouncer ab : bouncers)
    	{
    		if(ab.getRect().contains(x, y))
    		{
    			return ab;
    		}
    	}
    	return null;
    }
    
    /*
     * Sorts the object layer into separate structures
     */ 
    private void sortObjectLayer(MapLayer ml)
    {
    	boolean foundPlayerStart = false;
    	
        for(Tile t : ml.tiles)
        {
            try
            {                
                if(t.properties.get("type").equalsIgnoreCase("exit"))
                {
                    exitX = t.xPos;
                    exitY = t.yPos;
                }
                else if(t.properties.get("type").equalsIgnoreCase("start"))
                {
                    playerTile = t;
                    foundPlayerStart = true;
                }
                else if(t.properties.get("type").equalsIgnoreCase("enemy"))
                {
                    System.out.print("read enemy... ");
                    System.out.println(" name: " 
                                       + t.properties.get("name"));
                    
                    if(t.properties.get("name").equalsIgnoreCase("bluey"))
                    {
                        enemiesLiving.add(new Bluey(this, t));
                    }
                }
                else if(t.properties.get("type").equalsIgnoreCase("bouncer"))
               	{	
                	System.out.println(
                		"found bouncer at "	+ t.xPos + ", " + t.yPos);

                	System.out.println(t.properties);
                
                	if(t.properties.get("direction").equalsIgnoreCase("left"))
                	{
                		bouncers.add(new ArrowBouncer(t, this, Direction.LEFT));
                	}
                	else if (
                		t.properties.get("direction").equalsIgnoreCase("right"))
                	{
                		bouncers.add(new ArrowBouncer(t, this, Direction.RIGHT));
                	}
                	else
                	{
                		System.out.println("Bouncer has no direction!");
                		System.exit(-1);
                	}
                	
               	}
            }
            catch(NullPointerException e)
            {
                //System.out.println("sortObjects: ignoring np exception");
                e.printStackTrace();
            }
        }
        if(!foundPlayerStart)
        {
        	System.out.println(
        		"did not find player start position." +
        		"Cannot continue.");
        	System.exit(-1);
        }
        if(enemiesLiving != null)
        {
        	System.out.println("level has " + enemiesLiving.size() + " enemiesLiving");
        }
    }
    
    /**
     * Returns the name of this Level.
     */ 
    @Override
	public String toString()
    {
        return name;
    }
    
    public NavMesh getNavMesh()
    {
        return navMesh;
    }
    
    /*
     * Creates a map layer by retrieving the data from the Tiled file
     * at the given index
     */ 
    private MapLayer createMapLayer(int index)
    {
        List<Tile> tiles = new ArrayList<Tile>();
        
        for(int x = 0; x < getWidth(); ++x)
        {
            for(int y = 0; y < getHeight(); ++y)
            {
                if(getTileId(x, y, index) != NOT_PRESENT)
                {
                    tiles.add(new Tile(this, x, y, index));
                }
            }
        }           
        return new MapLayer(tiles, index, isVisible(index));
    }
    
    /*
     * Returns true if the layer specified is visible.
     */
    private boolean isVisible(int layerIndex)
    {
    	return getLayerProperty(
	    			layerIndex, 
					VIS_STR, 
					Tile.NULL).equals(TRUE_STR);
    }
    
    /*
     * Draw the Level. 
     */
	public void render(int x, int y, Graphics g)
    {
        // ordering matters!
    	for(int i = 0; i < playerLayer; ++i)
    	{
    		render(x, y, i);
    	}
    	drawPlayerLayer(g);
    	for(int i = playerLayer+1; i < backgroundLayers.size(); ++i)
    	{
    		render(x, y, i);
    	}
        //navMesh.draw(g);
    }
    
    private void drawPlayerLayer(Graphics g)
    {
    	drawEnemies(g);
    	player.draw(g);
    	//drawObjects(g);
    }
    
    /**
     * Draw using Graphics object g this Level at 0,0 plus 
     * the player, enemies, objects..... 
     */ 
    public void draw(Graphics g)
    {
        render(0, 0, g);
    }
    
    private void drawObjects(Graphics g)
    {
    	for(ArrowBouncer ab : bouncers)
        {
        	ab.draw(g);
        }
    }
    
	/**
     * Draw using Graphics object g any visible enemiesLiving. 
     */ 
    private void drawEnemies(Graphics g)
    {
        for(Actor a : enemiesLiving)
        {
            a.draw(g);
        }
        /*
        for(Actor a : enemiesDead)
        {
            a.draw(g);
        }
        */
    }
    
    /**
     * Returns true if Shape p intersects with a collision tile on the
     * Level.
     */ 
    public boolean collides(Shape p)
    {
        for(Tile t : collisionLayer.tiles)
        {
            if(t.getRect().intersects(p))
            {
                
                /*System.out.println("[Shape [origin " + 
                                    new Throwable().fillInStackTrace()
                                    .getStackTrace()[3].getFileName()
                                      + "] collision with Tile " +
                                    t.xCoord + ", " + t.yCoord
                                    + "]"); */
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns true if the x / y coordinate position supplied is inside
     * a collision tile
     */ 
    public boolean isInCollisionTile(float x, float y)
    {
        for(Tile t : collisionLayer.tiles)
        {
            if(t.getRect().contains(x, y))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the collision Tile containing the given point, or null.
     */ 
    public Tile getCollisionTileAt(float x, float y)
    {
        for(Tile t : collisionLayer.tiles)
        {
           if(t.getRect().contains(x, y))
           {
               return t;
           }   
        }
        return null;
    }
}
