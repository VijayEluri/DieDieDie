package org.diediedie;

import java.io.*;
import java.util.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.tiled.TileSet;
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
    public static final float groundFric = 0.9f;
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    private String name;
    private final String COLLISION_STRING = "collisions";
    private int collisionIndex;
    private List<Tile> collisionTiles; 
    
    /**
     * --------------------
     * |!!Create a Level!!|
     * --------------------
     */ 
    public Level(String name, InputStream in, String tileSetsPath, 
                 float playerX, float playerY, Direction facing,
                 float grav)
                 throws SlickException
    {
        super(in, tileSetsPath);
        this.name = name;
        this.gravity = grav;
        this.playerFacing = facing;
        this.playerX = playerX;
        this.playerY = playerY;
        collisionIndex = getLayerIndex(COLLISION_STRING);
        makeCollisionList();
    }    
    
    public String toString()
    {
        return name;
    }
    
    /**
     * Creates list of tiles on the map that act as collision markers.
     */ 
    private void makeCollisionList()
    {
        collisionTiles = new ArrayList<Tile>();
        
        final int present = 1;
        
        for(int x = 0; x < getWidth(); x++)
        {
            for(int y = 0; y < getHeight(); y++)
            {
                if(getTileId(x, y, collisionIndex) == present)
                {
                    collisionTiles.add(new Tile(this, x, y));
                }
            }
        }
    }
    
    /**
     * Returns true if two polygons intersect.
     */ 
    public boolean intersection(Shape p1, Shape p2)
    {
        return p1.intersects(p2);
    }
    
    
    /**
     * Returns true if Polygon p intersects with a collision tile on the
     * Level.
     */ 
    public boolean collides(Rectangle p)
    {
        for(Tile t : collisionTiles)
        {
            if(t.getRect().intersects(p))
            {
                //System.out.println(t + " intersects Rect " + p);
                return true;
            }
        }
        return false;
    }
}
