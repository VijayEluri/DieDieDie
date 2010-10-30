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
    private final String COLLISION_STRING = "collisions", 
                         EXIT_STRING = "exits";
    private int collisionIndex = -1000, exitIndex = -1000;
    
    private List<Tile> collisionTiles = null; 
    private List<Tile> exitTiles = null;    
    private final int NOT_PRESENT = 0;
    
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
        
        exitIndex = getLayerIndex(EXIT_STRING);
        exitTiles = createLayerList(exitIndex);
        collisionIndex = getLayerIndex(COLLISION_STRING);
        collisionTiles = createLayerList(collisionIndex);
        
       
    }    
        
    public String toString()
    {
        return name;
    }
    
    private List<Tile> createLayerList(int index)
    {
        List<Tile> tiles = new ArrayList<Tile>();
        for(int x = 0; x < getWidth(); x++)
        {
            for(int y = 0; y < getHeight(); y++)
            {
                if(getTileId(x, y, index) != NOT_PRESENT)
                {
                    tiles.add(new Tile(this, x, y));
                }
            }
        } 
        System.out.println("layer " + index + ": " + tiles.size());    
        return tiles;
    }
    
    /**
     * Returns true if two polygons intersect.
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
        for(Tile t : collisionTiles)
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
        for(Tile t : collisionTiles)
        {
            if(t.getRect().contains(x, y))
            {
                return true;
            }
        }
        return false;
    }
    
}
