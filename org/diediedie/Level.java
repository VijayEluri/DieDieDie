package org.diediedie;

import java.io.*;
import java.util.*;
import org.newdawn.slick.tiled.TiledMap;
//import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;


/**
 * Level in a game of DIE DIE DIE aka snapper snap snap snappy snap a 
 * snapp snap
 */ 
public class Level extends TiledMap
{
    // where the player will first appear
    public float playerX, playerY;
    
    // friction. lower number == more friction
    public static final float FRICTION = 0.9f;
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    private String name;
    private final String COLLISION_STR = "collisions", 
                         OBJECTS_STR = "objects", 
                         BACKGROUND_STR = "background",
                         TILES_STR = "platforms",
                         VIS_STR = "isvisible", TRUE_STR = "true", 
                         FALSE_STR = "false", PLATFORM_STR = "platforms";
    
    private final int NOT_PRESENT = 0;
    private int collisionIndex, visibleTilesIndex, objectsIndex;
    
    private MapLayer collisionTiles, /*exitTiles,*/ objectTiles, 
                     visibleTiles, backgroundTiles, platformTiles;
    
    
    
    //private Map<int, List<Tile>> layers = new HashMap<int, List<Tile>>();
    
    
    
    /**
     * Create a Level
     */ 
    public Level(String name, InputStream in, String tileSetsPath, 
                 float playerX, float playerY, Direction facing,
                 float grav) throws SlickException
    {
        super(in, tileSetsPath);
        this.name = name;
        this.gravity = grav;
        this.playerFacing = facing;
        this.playerX = playerX;
        this.playerY = playerY;
        
        System.out.println("Level " + name + " has " + getLayerCount() +
                           " tile layers");
                           
     
       // collisionTiles = createLayerList(collisionIndex);

       // objectsIndex = getLayerIndex(OBJECTS_STR);
        //objectTiles = createLayerList(objectsIndex);
        
        //layers.put(collisionIndex, collisionTiles);
        //layers.put(objectsIndex, objectTiles);
        
        collisionTiles = createMapLayer(getLayerIndex(COLLISION_STR));
        objectTiles = createMapLayer(getLayerIndex(OBJECTS_STR));
        platformTiles = createMapLayer(getLayerIndex(PLATFORM_STR));
        backgroundTiles = createMapLayer(getLayerIndex(BACKGROUND_STR));
        
        
                
        //System.out.println("objectsIndex: " + objectsIndex);
    }   
    

    
    /*public void render(int x, int y)
    {
       render(x, y, 
    }*/
        
    public String toString()
    {
        return name;
    }
    
    private MapLayer createMapLayer(int index)
    {
        List<Tile> tiles = new ArrayList<Tile>();
                
        for(int x = 0; x < getWidth(); x++)
        {
            for(int y = 0; y < getHeight(); y++)
            {
                if(getTileId(x, y, index) != NOT_PRESENT)
                {
                    tiles.add(new Tile(this, x, y, index));
                }
            }
        }   
        
        // is this a visible layer?
        final boolean VISIBLE; 
        final String v = getLayerProperty(index, VIS_STR, Tile.NULL);
        
        if(v.equals(TRUE_STR))
        {
            VISIBLE = true;
        }
        else
        {
            VISIBLE = false;
        }
        
        return new MapLayer(tiles, index, VISIBLE);
    }
    
    /**
     * Returns true if two Shapes intersect.
     */ 
    public boolean intersection(Shape p1, Shape p2)
    {
        return p1.intersects(p2);
    }
    
    
    
    /**
     * Returns true if Shape p intersects with a collision tile on the
     * Level.
     */ 
    public boolean collides(Shape p)
    {
        for(Tile t : collisionTiles.tiles)
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
        for(Tile t : collisionTiles.tiles)
        {
            if(t.getRect().contains(x, y))
            {
                return true;
            }
        }
        return false;
    }
    
}
