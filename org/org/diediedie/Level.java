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

import java.util.Properties;
import java.io.*;
import org.diediedie.NavMesh;
import org.diediedie.NavMesh.MeshMaker;
import org.diediedie.actors.Actor;
import org.diediedie.actors.Elevator;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.LevelObject;
import org.diediedie.actors.Player;
import org.diediedie.actors.Projectile;
import org.diediedie.actors.Radio;
import org.diediedie.actors.SignalReceiver;
import org.diediedie.actors.Switch;
import org.diediedie.actors.Transmitter;
import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.midi.Receiver;

/**
 * A single level...
 */ 
public class Level extends TiledMap
{
	enum ObjectType 
	{
		Radio, 
		Elevator, 
		Switch,
	}
    // where the exit of the level is
    public float exitX, exitY;
    
    // smaller number == more friction. xSpeed is multiplied by this number
    public static final float FRICTION = 0.7f;

    // number of pixels from the player's current position to check a
    // tile for possible collision
	private static final int TILE_COLLISION_CHECK_DIST = 20;

	private static final String LevelObjectFactory = null;
    
    // other way with gravity because gravity is added to the Actors' 
    // ySpeed
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    // the name of this level
    private String name;
    
    // a navigable mesh for AI-controlled actors.
    private NavMesh navMesh;
    private final String VIS_STR = "isvisible", TRUE_STR = "true";
    
    /*
     * Level Layers
     */
    //private List<LevelLayer> drawableLayers;
    //private List<UpdatableLayer> updatableLayers;
    private List<UpdatableLayer> levelLayers;
    private MapLayer collisionLayer;
    
    //private List<ArrowBouncer> bouncers;    
    private PlayerLayer playerLayer;
    
    private Tile playerTile = null;
    
    private int levelHeight;
	private int levelWidth;
	
	/*
	 * Some objects are added to lists of their type
	 * as well as their parent UpdatableLayer for fast access
	 * during play.
	 */
	private List<ArrowBouncer> bouncers;
	private ArrayList<Transmitter> transmitters;
	private ArrayList<SignalReceiver> signalReceivers;



    /**
     * Create a Level
     * @throws IllegalAccessException 
     */ 
    public Level(String levelName, InputStream in, String tileSetsPath) 
    		     throws SlickException, IllegalAccessException
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
        parseObjectLayers();
    }   
    
    /*
     * Parses only the object layers from the map and
     * adds them to their parent layers.
     */
    private void parseObjectLayers()
    {
    	List<LevelObject> objs;
    	
    	for(Object o : objectGroups)
        {
    		ObjectGroup og = (ObjectGroup) o;
    		
    		System.out.println(
    			"\nProperties of group : "
    			+ og.props + "\n");
    		
    		assert og.props.containsKey("parent");
    		objs = createLayerObjects(og);	
    		assert objs != null;
    		attachObjectsToLayer(objs, (String)og.props.get("parent"));
        }
    	sortAndConnectObjects();
	}
    
    /*
     * Sorts the LevelObjects in all layers on this Level 
     * into Lists based on their exact type for ease of
     * access during game execution.
     * 
     * Hooks up Transmitters and SignalReceivers after all
     * objects have been parsed from the TiledMap.
     * 
     * (This can't be done during initial parsing because
     * the order of object creation is unknown)
     */
    private void sortAndConnectObjects() 
    {
    	System.out.println(
    			"sorting and connecting objects");
    	for(LevelLayer ll : levelLayers)
    	{
    		for(LevelObject lo : ll.getObjects())
    		{
    			System.out.println("\t" + lo.getName());
    			
    			if(lo instanceof Transmitter)
    			{
    				System.out.println("  found a Transmitter");
    				
    				for(String name  :  ( (Transmitter) lo).getTargetNames())
    				{
    					assert name != null;
    					SignalReceiver sr = findSignalReceiver(name);
    					assert sr != null;
    					
    					
    					System.out.println("    found SignalReceiver '" + 
    							sr.getName() + "', attaching to " + lo.getName());
    					((Transmitter) lo).addTarget(sr);
    				}
     			}
    		}
    	}
	}
    
    
    /*
     * Attempts to return the SignalReceiver object on the Level
     * from the object's name.
     */
    private SignalReceiver findSignalReceiver(String name)
    {
    	System.out.println(
				"findSignalReceiver: looking for " + name);
    	for(SignalReceiver sr : signalReceivers)
    	{
    		if(name.equals(sr.getName()))
    		{
    			return sr;
    		}
    	}
    	System.out.println("Failed to find " + name);
    	return null;
    }
    
	private void attachObjectsToLayer(List<LevelObject> objs, 
    								  String layerName) 
    {
		assert objs != null;
    	assert layerName != null;
    	/*System.out.println("attaching objects to layer :" 
    			+ objs + " -> " + layerName);
    	*/
		for(LevelLayer l : levelLayers)
		{
			if(l.getName().equals(layerName))
			{
				System.out.println(
					"attaching layer " + layerName + "'s " +
					+ objs.size() + " objects");
				for(LevelObject o : objs)
				{
					System.out.println(
						"    LevelObject : " + o.getName());
				}
				l.getObjects().addAll(objs);
				return;
			}
		}
		System.out.println("Failed to find layer " + layerName);
		System.exit(-1);
	}

	/*
     * Creates LevelObjects from a TiledMap.ObjectGroup,
     * and attaches them to a Drawable layer.
     */
    private List<LevelObject> createLayerObjects(ObjectGroup og) 
    {
    	System.out.println("ObjectGroup : index " + og.index +" : " + og.name);
    	
    	// adjust the properties file - add name, index and type
    	List<LevelObject> objs = new ArrayList<LevelObject>();
    	
    	for(Object o2 : og.objects)
    	{
    		GroupObject go = (GroupObject)o2;
    		
    		go.props.put("type", go.type);
    		go.props.put("index", go.index);
    		go.props.put("name", go.name);
    		go.props.put("width", og.width);
    		go.props.put("height", og.height);
    		go.props.put("x", go.x);
    		go.props.put("y", go.y);
    		
    		// Add all layer-level properties as well
    		go.props.putAll(og.props);
    	
    		LevelObject lo = createObject(go.props);
    		assert lo != null;
    		lo.setLevel(this);
    		objs.add(lo);
    	}
    	
		return objs;
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
     * Creates Tile layers (not object layers) from the Tiled Map.
     */
    private void createLayers() throws SlickException
    {
    	int collisionLayerIndex = getLayerIndex("collision");
    	collisionLayer = createMapLayer(collisionLayerIndex);
    	
    	levelLayers =  new ArrayList<UpdatableLayer>();
    	
    	/*
    	 * All layers except collision will have a drawable
    	 * element.
    	 */
    	for(int i= 0; i < getLayerCount(); ++i)
    	{
    		if(i != collisionLayerIndex)
    		{
    			createLevelLayer(createMapLayer(i));
    		}
    	}

		System.out.println("\nDrawing Order");
		
    	for(LevelLayer d : levelLayers)
    	{
    		System.out.println("  " + d.getName());
        	
    	}
		System.out.println("\n");
    }
    
    /*
     * Extracts the layer's content from the TiledMap.
     * 
     * Adds to the drawable / updatable layers lists.
     */
	private void createLevelLayer(MapLayer ml) throws SlickException 
    {
		/*if(ml.visible)
		{
			return new StaticSceneryLayer(ml);
		}
		else*/ 
		
    	if(ml.name.equalsIgnoreCase("player"))
    	{
    		PlayerLayer pl = new PlayerLayer(ml);
    		setPlayerLayer(pl);
    		levelLayers.add(pl);
    	}
    	else
    	{
    		levelLayers.add(new UpdatableLayer(ml));
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
    	updateLayers();
    }
    
    private void updateLayers() 
    {
    	for(UpdatableLayer ul : levelLayers)
    	{
    		ul.update();
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
    	assert index < getLayerCount();
    	System.out.println("creating map layer " + index);
        Layer l = null;
        l = (Layer)layers.get(index);
        assert l != null;
        
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
     */
	public void render(int x, int y, Graphics g) throws SlickException
    {
       for(LevelLayer l : levelLayers)
       {
    	   l.draw(g);
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
		
		final Shape rect = m.getCollisionBox();
		
		for(Tile t : collisionLayer.tiles)
		{
			boolean closeX = false;
			boolean closeY = false;
			
			if(t.yPos < rect.getY())
			{
				// tile start is above the actor
				if(rect.getY() - t.endY <= TILE_COLLISION_CHECK_DIST)
				{
					closeY = true;
				}
			}
			else if(t.yPos > rect.getY())
			{
				// tile start is below the actor
				if(t.yPos - rect.getMaxY() <= TILE_COLLISION_CHECK_DIST)
				{
					closeY = true;
				}
			}
			else
			{
				// same vertical position
				closeY = true;
			}
			if(t.xPos < rect.getX())
			{
				// tile start is to the left of the actor
				if(rect.getX() - t.endX <= TILE_COLLISION_CHECK_DIST)
				{
					closeX = true;
				}
			}
			else if(t.xPos > rect.getX())
			{
				// tile start is to the right of the actor
				if(t.xPos - rect.getMaxX() <= TILE_COLLISION_CHECK_DIST)
				{
					closeX = true;
				}
			}
			else
			{
				// same horizontal position
				closeX = true;
			}
			if(closeX && closeY)
			{
				if(rect.intersects(t.getRect()))
				{
					colls.add(t);
				}
			}
		}
		return colls;
	}
	
	
	public PlayerLayer getPlayerLayer()
	{
		return playerLayer;
	}

	public void setPlayerLayer(PlayerLayer playerLayer)
	{
		this.playerLayer = playerLayer;
	}

	/*
	 * Creates a LevelObject based on the properties object.
	 * 
	 * Some objects are added to specific lists so they can
	 * be queried quickly during the game.
	 */
	public LevelObject createObject(Properties props) 
	{
		if(transmitters == null)
		{
			transmitters = new ArrayList<Transmitter>();
		}
		if(signalReceivers == null)
		{
			signalReceivers = new ArrayList<SignalReceiver>();
		}
		
		System.out.println("\nLOF.createObject -> " + props);
		
		if(props.get("type").equals(ObjectType.Radio.name()))
		{
			Radio r = new Radio(props);
			System.out.println("\t^ LevelObjectFactory found a radio");
			transmitters.add(r);
			signalReceivers.add(r);
			return r; 
		}
		else if(props.get("type").equals(ObjectType.Elevator.name()))
		{
			Elevator e = new Elevator(props);
			signalReceivers.add(e);
			return e;
		}
		else if(props.get("type").equals(ObjectType.Switch.name()))
		{
			Switch s = new Switch(props);
			transmitters.add(s);
			return s;
		}
		System.out.println(
			"Couldn't determine type of LevelObject.");
		
		return null;
	}

	
}
