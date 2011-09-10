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
import java.util.List;

import org.diediedie.actors.LevelObject;
import org.diediedie.actors.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;


/**
 * Represents a layer of Tiles and Level Objects that will
 * be rendered on a Level.
 */ 
public class MapLayer
{
	public enum Type
	{
		Background, Collision, Elevator, Player
	}

	private static final int TILE_NOT_PRESENT = 0;;
	
    protected List<Tile> tiles;
    protected int index;
    
    protected TiledMap map;
    // the parent map which holds this layer
    
    protected Type type;
    protected String name;
    
    protected List<LevelObject> levelContent;
    
    /**
     * Constructs a new map layer from a List of Tiles.  
     */
    public MapLayer(TiledMap map, 
    				String nameStr,
    				final int in, 
    				final boolean vis)
    {
    	extractTiles(map, in);
        System.out.println("MapLayer : Index " + in 
        		+ "\n\tName " + nameStr
        		+ "\n\tTile count : " + tiles.size()
        		+ "\n\tVisible : " + vis);

        index = in;
        name = nameStr;
        
        if(vis)
        {
        	type = Type.Background;
        }
        else
        {
        	createNonVisibleLayer();
        }
    }
    
    private void extractTiles(TiledMap map, int in)
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

    /*
     * Extracts the layer's content from the TiledMap.
     */
	private void createNonVisibleLayer() 
    {
    	if(name.equalsIgnoreCase("collision"))
    	{
    		type = Type.Collision;
    	}
    	else if(name.equalsIgnoreCase("elevator"))
    	{
    		type = Type.Elevator;
    		createElevators();
    	}
    	else if(name.equalsIgnoreCase("player"))
    	{
    		type = Type.Player;
    	}
	}
	


	
	/*
	 * For Elevator MapLayers. Iterates over the layer Tiles creating Elevators
	 * for each one.
	 */
	private void createElevators()
	{
		for(Tile t : tiles)
		{
			
		}
	}

	public void draw(int x, int y, Graphics g)
    {
    	
    }
    
    /**
     * Returns true if a Tile with the given coordinates exists in the
     * MapLayer.
     */ 
    public boolean containsTile(int x, int y)
    {
        if(x < 0 || y < 0)
        {
            return false;
        }
        for(Tile t : tiles)
        {
            if(t.xCoord == x && t.yCoord == y)
            {
                /*System.out.println(this + "|\tContains " +
                                xCoord + ", " + yCoord);*/
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
                + index + ", type : " + type;
    }
}
