package org.diediedie;

import java.util.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Rectangle;


/**
 * Acts as a bridge between a TiledMap and the individual tiles so they
 * can be accessed easily (for collision det. etc).
 */ 
public class Tile
{
    private int xCoord, yCoord, tileWidth, tileHeight, layer, id;
    private float xPos, yPos;
    private TiledMap tiledMap;
    private Rectangle rect;
    private Map<String, String> properties = new HashMap<String, String>();
    
    private final String[] ALL_PROPERTIES = {"isvisible", "type"};
    private final String[] ALL_VALUES = {"true", "false", "exit", 
                                         "object"};
    private final String NULL = "NULL";
    /**
     * Creates a Tile from a tile at specified coordinates (x,y). If
     * this is a collision 
     */ 
    public Tile(TiledMap map, int xCoord, int yCoord, int layer)
    {
        this.tiledMap = map;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.layer = layer;
        this.id = map.getTileId(xCoord, yCoord, layer); 
        
        calculatePosition();
        extractProperties();
    }
    
    private void extractProperties()
    {
        for(final String P : ALL_PROPERTIES)
        {
            final String V = tiledMap.getTileProperty(id, P, NULL);
            
            if(!V.equals(NULL))
            {
                properties.put(P, V);
            }
        }
        for(Map.Entry<String, String> e : properties.entrySet())
        {
            System.out.println("\t" + e.getKey() + " : " + e.getValue());
        }
    }
    
    /**
     * Stores the coordinates and size of the current Tile. 
     */ 
    private void calculatePosition()
    {
        tileWidth = tiledMap.getTileWidth();
        tileHeight = tiledMap.getTileHeight();
        xPos = (xCoord) * tileWidth;
        yPos = (yCoord) * tileHeight;
        rect = new Rectangle(xPos, yPos, tileWidth, tileHeight);
        //System.out.println("Tile size: " + tileWidth + ", " + tileHeight);
    }
    
    /**
     * Returns the rectangular bounding box of this tile on the Level.
     */ 
    public Rectangle getRect()
    {
        return rect;
    }
    
    public String toString()
    {
        return "Tile: x, y: " + xPos + ", " + yPos;
    }
}
