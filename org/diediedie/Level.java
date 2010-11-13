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
import java.util.*;
import org.diediedie.actors.Direction;
import org.diediedie.actors.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import java.util.List;
import java.util.ArrayList;

/**
 * A single level...
 */ 
public class Level extends TiledMap
{
    // where the exit of the level is
    public float exitX, exitY;
    
    // friction. lower number == more friction, because the horizontal
    // speed is multiplied by this number
    public static final float FRICTION = 0.91f;
    
    // other way with gravity because gravity is added to the Actors' 
    // ySpeed
    public float gravity;
    
    // initial direction player faces 
    public final Direction playerFacing;
    
    private String name;
    
    private final String VIS_STR = "isvisible", TRUE_STR = "true", 
                         FALSE_STR = "false", PLATFORM_STR = "platforms";
    
    private final int NOT_PRESENT = 0;
    
    private MapLayer collisionLayer, objectLayer, backgroundLayer, 
                     platformLayer;
        
    private Player player;
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
    
    
    
    public void associatePlayer(Player p)
    {
        player = p;        
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
     * Returns all enemies + the player
     */ 
    public List<Actor> getActors()
    {
        List<Actor> actors = new ArrayList<Actor>(enemies);
        actors.add(player);
        return actors;
    }
    
    /**
     * Updates the contents of the level, if any.
     */ 
    public void update()
    {
        updateEnemies();
    }
    
    /**
     * Updates the behaviour and position etc of all enemies on the
     * level
     */ 
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
                        enemies.add(new Bluey(this, t));
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
    
    /**
     * Returns the name of this Level.
     */ 
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
    
    /**
     * Draw using Graphics object g this Level at 0,0 plus any 
     * inhabiting enemies. 
     */ 
    public void draw(Graphics g)
    {
        render(0, 0);
        drawEnemies(g);
    }
    
    /**
     * Draw using Graphics object g any visible enemies. 
     */ 
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
