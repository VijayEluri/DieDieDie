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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TileSet;


/**
 * Represents a layer of Tiles and Level Objects that will
 * be rendered on a Level.
 */ 
public class MapLayer
{
	public static final int TILE_NOT_PRESENT = 0;
	
    protected List<Tile> tiles;
    protected int index = -1;
    protected boolean visible;
    protected Level level;
    protected String name;
    protected SpriteSheet spriteSheet;
    protected TileSet tileSet;
    /**
     * Constructs a new map layer from a List of Tiles.  
     */
    public MapLayer(Level map, 
    				String nameStr,
    				final int in, 
    				final boolean vis)
    {
    	assert map != null;
    	index = in;
        name = nameStr;
        extractTiles(map);
        level = map;
        visible = vis;
        tileSet = map.getTileSet(index);
        
        System.out.println("MapLayer : \n\tIndex " + index
        		+ "\n\tName " + name
        		+ "\n\tTile count : " + tiles.size());
    }
    
    private void extractTiles(Level map)
    {
    	tiles = new ArrayList<Tile>();
    	
    	for(int x = 0; x < map.getWidth(); ++x)
        {
            for(int y = 0; y < map.getHeight(); ++y)
            {
                if(map.getTileId(x, y, index) != TILE_NOT_PRESENT)
                {
                    tiles.add(new Tile(map, x, y, index));
                    
                }
            }
        }
    	assert !tiles.isEmpty();
	}
    
    /**
     * Returns true if a Tile with the given coordinates exists in the
     * MapLayer.
     */ 
    public boolean containsTile(int x, int y)
    {
        if(x < 0 || y < 0)
        {
            System.out.println(
            	"MapLayer.containsTile : received invalid tile coords");
            System.exit(-1);
        }
        for(Tile t : tiles)
        {
            if(t.xCoord == x && t.yCoord == y)
            {
                return true;
            }
        }
        return false;
    }
    
    /*
     * Returns the Tile at the given coordinates, or null.
     */
    public Tile getTileAt(int x, int y)
    {
		  for(Tile t : tiles)
		  {
			  if(t.xCoord == x && t.yCoord == y)
			  {
				  return t;
			  }
		  }
		  return null;
    }
    
    public String toString()
    {
        return "MapLayer: tileCount " + tiles.size() + ", index " 
                + index + ", type : ";
    } 
}

