package org.diediedie;


import java.io.*;
import java.util.*;
import org.diediedie.actors.Direction;
import org.diediedie.actors.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;


/**
 * Level in a game of DIE DIE DIE 
 */ 
public class Level extends TiledMap
{
    // where the player will first appear
    public float exitX, exitY;
    
    // friction. lower number == more friction
    public static final float FRICTION = 0.91f;
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    private String name;
    private final String VIS_STR = "isvisible", TRUE_STR = "true", 
                         FALSE_STR = "false", PLATFORM_STR = "platforms";
    
    private final int NOT_PRESENT = 0;
    
    private MapLayer collisionLayer, objectLayer, backgroundLayer, 
                     platformLayer;
    
    private List<Actor> enemies;
    private Tile playerTile = null;
    
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
                
        System.out.println("Level " + name + " has " + getLayerCount() +
                           " tile layers");
        
        collisionLayer = createMapLayer(getLayerIndex("collisions"));
        objectLayer = createMapLayer(getLayerIndex("objects"));
        platformLayer = createMapLayer(getLayerIndex("platforms"));
        backgroundLayer = createMapLayer(getLayerIndex("background"));    
        enemies = new ArrayList<Actor>();
        sortObjects();
    }   
    
    public Tile getPlayerTile()
    {
        return playerTile;   
    }
    
    public void update()
    {
        updateEnemies();
    }
        
    public void updateEnemies()
    {
        for(Actor a : enemies)
        {
            a.update();
        }
    }

    /*
     * Sorts the object layer into separate structures
     */ 
    private void sortObjects()
    {
        for(Tile t : objectLayer.tiles)
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
                }
                else if(t.properties.get("type").equalsIgnoreCase("enemy"))
                {
                    System.out.print("read enemy... ");
                    System.out.println(" name: " 
                                       + t.properties.get("name"));
                    
                    if(t.properties.get("name").equalsIgnoreCase("bluey"))
                    {
                        
                        enemies.add(new Bluey(t));
                    }
                }
            }
            catch(NullPointerException e)
            {
                //System.out.println("sortObjects: ignoring np exception");
                e.printStackTrace();
            }
        }
        System.out.println("level has " + enemies.size() + " enemies");
    }
    
    public String toString()
    {
        return name;
    }
    
    /*
     * Creates a map layer by retrieving the data from the Tiled file
     * at the given index
     */ 
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
    
    public void render(int x, int y)
    {
        // keep the ordering!
        render(x, y, backgroundLayer.index);
        render(x, y, platformLayer.index);
    }
    
    public void draw(Graphics g)
    {
        render(0, 0);
        drawEnemies(g);
    }
    
    private void drawEnemies(Graphics g)
    {
        for(Actor a : enemies)
        {
            a.draw(g);
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
    
}
