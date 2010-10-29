package org.diediedie;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Rectangle;


/**
 * Acts as a bridge between a TiledMap and the individual tiles so they
 * can be accessed easily (for collision det. etc).
 */ 
public class Tile
{
    private int xCoord, yCoord, tileWidth, tileHeight;
    private float xPos, yPos;
    private TiledMap tiledMap;
    private Rectangle rect;
    
    /**
     * Creates a Tile from a tile at specified coordinates (x,y). If
     * this is a collision 
     */ 
    public Tile(TiledMap tm, int xCoord, int yCoord)
    {
        this.tiledMap = tm;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        calculatePosition();
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
