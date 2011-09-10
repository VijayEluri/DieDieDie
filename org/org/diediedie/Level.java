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
import org.diediedie.actors.tools.CollideMask;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;

import java.util.BitSet;
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
    
    // smaller number == more friction. xSpeed is multiplied by this number
    public static final float FRICTION = 0.91f;
    
    // other way with gravity because gravity is added to the Actors' 
    // ySpeed
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    private String name;
    private NavMesh navMesh;
    private final String VIS_STR = "isvisible", TRUE_STR = "true";
    private MapLayer collisionLayer;
    private List<DrawableLayer> drawableLevelLayers;
    //private List<ArrowBouncer> bouncers;    
    private PlayerLayer playerLayer;
    
    private Tile playerTile = null;
	private int levelHeight;
	private int levelWidth;
	private List<ArrowBouncer> bouncers;

    /**
     * Create a Level
     */ 
    public Level(String levelName, InputStream in, String tileSetsPath) 
    		     throws SlickException
    {
        super(in, tileSetsPath);
        this.name = levelName;
        gravity = getMapGravity();
        playerFacing = getPlayerStartingDir();
        calculateLevelDimensions();
        
        System.out.println("Level " + name + " has " + getLayerCount() +
                           " tile layers. Mapsize " + this.levelWidth + " by "
                           + this.levelHeight + " gravity " + gravity);
        createLayers();
        createNavMesh();
        bouncers = new ArrayList<ArrowBouncer>();
    }   
    
    /*
     * Parses the gravity from the TiledMap file.
     */
    private float getMapGravity()
    {
    	return Float.parseFloat(getMapProperty("gravity", null));
    }
    
    /*
     * Fetches the player's starting direction on this level
     * from the TiledMap
     */
    private Direction getPlayerStartingDir()
    {
    	return Direction.convertToDirection(
    				getMapProperty("playerdirection", null));
    }
        
    /*
     * Creates layers from the Tiled Map.
     */
    private void createLayers()
    {
    	int collisionLayerIndex = getLayerIndex("collision");
    	collisionLayer = createMapLayer(collisionLayerIndex);
    	drawableLevelLayers = new ArrayList<DrawableLayer>();
    	
    	/*
    	 * All layers except collision will have a drawable
    	 * element.
    	 */
    	for(int i = 0; i < getLayerCount(); ++i)
    	{
    		if(i != collisionLayerIndex)
    		{
    			drawableLevelLayers.add(
    				(DrawableLayer)createLevelLayer(createMapLayer(i)));
    		}
    	}
    }
    
    /*
     * Extracts the layer's content from the TiledMap.
     */
	private LevelLayer createLevelLayer(MapLayer ml) 
    {
		if(ml.visible)
		{
			return new StaticSceneryLayer(ml);
		}
		else if(ml.name.equalsIgnoreCase("elevator"))
    	{
    		return new ActiveSceneryLayer(ml);
    	}
    	else if(ml.name.equalsIgnoreCase("player"))
    	{
    		setPlayerLayer(new PlayerLayer(ml));
    		return getPlayerLayer();
    	}
		return null;
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
    
    private void createNavMesh()
    {
        navMesh = MeshMaker.generateMesh(this);
    }
    
    public List<Enemy> getEnemies()
    {
        return getPlayerLayer().getLivingEnemies();
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
        return null;
    }
    
    public Player getPlayer()
    {
        return playerLayer.player;
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
                
        return actors;
    }
    
    /**
     * Updates everything on the level.
     */ 
    public void update()
    {
    	
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
        Layer l = (Layer)layers.get(index);
        return new MapLayer(this, l.name, index, isVisible(index));
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
     * Draws the Level map, the Player, Enemies and objects. 
     * 
     * To use transparency the Level's TiledMap layout must 
     * include a 'player' layer which is rendered in between 
     * the background layers.  Not including a player layour
     * will cause the game to exit at init stage.
     */
	public void render(int x, int y, Graphics g)
    {
       for(DrawableLayer l : drawableLevelLayers)
       {
    	   l.draw(x, y, g);
       }
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

    /*
     * Return all Tiles that an Actor is intersecting.
     */
	public List<Tile> getTileCollisions(Actor m) 
	{
		List<Tile> colls = new ArrayList<Tile>();
		
		final Shape rect = m.getCollisionBox();//AnimCreator.getCurrentFrameRect(m);
		
		for(Map.Entry<Shape, List<Tile>> e 
						: 
			navMesh.getWalkableTilesMap().entrySet())
			{
				if(e.getKey().intersects(rect))
				{
					colls.addAll(e.getValue());
				}
			}
		//System.out.println(
			//"getTileCollisions : " + colls.size() + " tiles");
		return colls;
	}

	public PlayerLayer getPlayerLayer() {
		return playerLayer;
	}

	public void setPlayerLayer(PlayerLayer playerLayer)
	{
		this.playerLayer = playerLayer;
	}
	
	
}
