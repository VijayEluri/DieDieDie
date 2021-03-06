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
package org.diediedie.level;

import java.util.Properties;
import java.io.*;

import org.diediedie.Drawable;
import org.diediedie.Entity;
import org.diediedie.LevelLayer;
import org.diediedie.level.NavMesh;
import org.diediedie.level.NavMesh.MeshMaker;
import org.diediedie.level.actors.Actor;
import org.diediedie.level.actors.Enemy;
import org.diediedie.level.actors.Player;
import org.diediedie.level.actors.tools.AnimCreator;
import org.diediedie.level.actors.tools.Direction;
import org.diediedie.level.objects.ArrowBouncer;
import org.diediedie.level.objects.Elevator;
import org.diediedie.level.objects.Projectile;
import org.diediedie.level.objects.Radio;
import org.diediedie.level.objects.SignalReceiver;
import org.diediedie.level.objects.Switch;
import org.diediedie.level.objects.Transmitter;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.sound.midi.Receiver;
import java.util.regex.*;
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
		Drone,
	}
	
    // where the exit of the level is
    public float exitX, exitY;
    
    // smaller number == more friction. xSpeed is multiplied by this number
    public static final float FRICTION = 0.7f;
    
    // other way with gravity because gravity is added to the Actors' 
    // ySpeed
    private float gravity;
    
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
    
    public float getGravity()
    {
    	return gravity;
    }
    
    /*
     * Parses only the object layers from the map and
     * adds them to their parent layers.
     */
    private void parseObjectLayers()
    {
    	List<Entity> objs;
    	transmitters = new ArrayList<Transmitter>();
    	signalReceivers = new ArrayList<SignalReceiver>();
    	
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
    		for(Entity lo : ll.getObjects())
    		{
    			System.out.println("\t" + lo.getName());
    			
    			if(lo instanceof Transmitter)
    			{
    				System.out.println("  found a Transmitter");
    				transmitters.add((Transmitter) lo);
     			}
    			if(lo instanceof SignalReceiver)
    			{
    			    signalReceivers.add((SignalReceiver)lo);
    			    System.out.println(
    			           "Found a SignalReceive " + lo.getName());
    			}
    		}
    	}
    	
    	/*
    	 * 
    	 */
    	connectTransmittersToReceivers();
	}
    
    /*
     * Attaches all the Transmitters in the transmitters
     * list for this Level to those returned by 
     * a_transmitter.getTargetNames().
     */
    private void connectTransmittersToReceivers()
    {
        for(Transmitter t : transmitters)
        {
            for(String name  :  t.getTargetNames())
            {
                assert name != null;
                SignalReceiver sr = findSignalReceiver(name);
                assert sr != null;

                System.out.println("    found SignalReceiver '" + 
                        sr.getName() + "', attaching to " 
                        + ((Drawable) t).getName());
                t.addTarget(sr);
            }
        }
    }
    
    /*
     * Attempts to return the SignalReceiver object on the Level
     * from the object's name. This should only be used during
     * initialization as it it's slow. 
     */
    private SignalReceiver findSignalReceiver(String name)
    {
    	/*System.out.println(
				"findSignalReceiver: looking for " + name);*/
    	
    	for(SignalReceiver sr : signalReceivers)
    	{
    		if(name.equals(sr.getName()))
    		{
    			return sr;
    		}
    	}
    	/*System.out.println("Failed to find " + name);*/
    	return null;
    }
    
    /*
     * Attempts to locate a LevelLayer via it's name String.
     */
    public LevelLayer getLevelLayerByName(String name)
    {
    	for(LevelLayer l : levelLayers)
		{
    		if(l.getName().equals(name))
			{
    			/*System.out.println("getLevelLayerByName : found " 
    								+ name);*/
    			return l;
			}
		}
    	return null;
    }
    
    /*
     * Attempts to add 
     */
	private void attachObjectsToLayer(List<Entity> objs, 
    								  String layerName) 
    {
		assert objs != null;
    	assert layerName != null;
    		
    	LevelLayer l = getLevelLayerByName(layerName);
    	
		System.out.println(
			"attaching layer " + layerName + "'s " +
			+ objs.size() + " objects");
    	
		for(Entity o : objs)
		{
			System.out.println(
				"    LevelObject : " + o.getName());
		}
		
		l.getObjects().addAll(objs);
	}
	
	/*
	 * Debugging method
	 */
	public void printLevelSummary()
	{
		System.out.println("Summary of Level : " + this.name);
		System.out.println("=================");
		
		for(LevelLayer l : levelLayers)
		{
			System.out.println("  Layer " + l.getName());
			
			for(Entity lo : l.getObjects())
			{
				System.out.println(
				    "   Type: " + getObjectTypeName(lo)	
				    + " ("
				    + Arrays.deepToString(getObjectInterfaceNames(
				    		lo).toArray())
					+ ") '" + lo.getName() + "' ");
			}
		}
	}
	
	/*
	 * Returns a List of the names that an Object implements.
	 */
	public List<String> getObjectInterfaceNames(Object o)
	{
		List<String> names = new ArrayList<String>();
		
		for(Class<?> c : o.getClass().getInterfaces())
		{
			String[] strs = splitClassName(c.getName());
			names.add(strs[strs.length-1]);
		}
		return names;
	}
	
	public String getObjectTypeName(Object o)
	{
		Class<? extends Object> ocls = o.getClass();
		String[] namespaces = splitClassName(ocls.getName());
		return namespaces[namespaces.length - 1];
	}
	
	/*
	 * Splits a classname into an array.
	 * 
	 * e.g. 'org.diediedie.SomeThing' -> ['org', 'diediedie', 'SomeThing']
	 */
	public String[] splitClassName(String className)
	{
		return className.split("\\.");
	}
	
	/*
     * Creates LevelObjects from a TiledMap.ObjectGroup,
     * and attaches them to a Drawable layer.
     */
    private List<Entity> createLayerObjects(ObjectGroup og) 
    {
    	System.out.println(
    	    "ObjectGroup : index " + og.index + " : " + og.name);
    	
    	// adjust the properties file - add name, index and type
    	List<Entity> objs = new ArrayList<Entity>();
    	
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
    	
    		Entity lo = LevelObjectFactory.createObject(go.props);
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
    
    /*
     * Returns all living enemies on the Level.
     */
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
	public void render(int x, int y, Graphics g) //throws SlickException
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

    
	
	public PlayerLayer getPlayerLayer()
	{
		return playerLayer;
	}

	
	public void setPlayerLayer(PlayerLayer playerLayer)
	{
		this.playerLayer = playerLayer;
	}

	
}
