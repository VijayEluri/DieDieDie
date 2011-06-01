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
import java.util.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import pulpcore.math.Rect;

/**
 * Acts as a bridge between a TiledMap and the individual tiles so they
 * can be accessed easily (for collision det. etc).
 * 
 * Hopefully this can still be used without problems in the translation
 * from Slick to Pulpcore.
 */ 
public class Tile
{   
    final float ROUNDING = 0.999f;    
    public int xCoord, yCoord, tileWidth, tileHeight, layer, id;
    public float xPos, yPos, endX, endY;
    private TiledMap tiledMap;
    private Rectangle rect;
    
    public Map<String, String> properties = new HashMap<String, String>();
    
    public static final String[] PROPERTIES = {"type", "name"};
    public static final String[] VALUES = {
        "exit", "enemy", "start", "bluey"};
    public static final String NULL = "NULL";
    
    /**
     * Constructs a Tile from a tile at specified coordinates (x,y). If
     * this is a collision 
     */ 
    public Tile(TiledMap map, int xCoord, int yCoord, int layer)
    {
        this.tiledMap = map;
        tileWidth = map.getTileWidth();
        tileHeight = map.getTileHeight();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.layer = layer;
        this.id = map.getTileId(xCoord, yCoord, layer); 
        calculatePosition();
        extractProperties();
        
        // System.out.println("new " + this);
    }
    
    public String getCoords()
    {
        return new String("[" + xCoord + ", " + yCoord + "]");
    }
        
    private void extractProperties()
    {
        for(final String P : PROPERTIES)
        {
            final String V = tiledMap.getTileProperty(id, P, NULL);
            
            if(!V.equals(NULL))
            {
                properties.put(P, V);
            }
        }
    }
    
    /**
     * Stores the coordinates and size of the current Tile. 
     */ 
    private void calculatePosition()
    {        
        xPos = (xCoord) * tileWidth;
        yPos = (yCoord) * tileHeight;
        
        rect = new Rectangle(xPos, yPos, tileWidth + ROUNDING, 
                                         tileHeight + ROUNDING);
                                         
        endX = xPos + tileWidth + ROUNDING;
        endY = yPos + tileHeight + ROUNDING;
    }
        
    public float getXPos() { return xPos; }
    public float getYPos() { return yPos; }
        
    /**
     * Returns the rectangular bounding box of this tile on the Level.
     */ 
    public Rectangle getRect() { return rect; }    
    
    /**
     * 
     */ 
    public String toString()
    {
        return "Tile: [xCoord, yCoord], [xPos, yPos], [endX, endY], layer: [" 
                + xCoord + ", " + yCoord + "], ["+ xPos + ", " + 
                yPos + "], [" + (xPos+tileWidth) + ", " 
                + (yPos+tileHeight) + "] " + layer;
    }
}
