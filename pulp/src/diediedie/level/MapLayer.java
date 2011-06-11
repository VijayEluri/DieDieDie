/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */
package diediedie.level;

import java.util.List;
import pulpcore.sprite.Group;
import pulpcore.CoreSystem;


/**
 * Represents a layer of Tiles
 */ 
public class MapLayer extends Group
{
    protected List/**/ tiles;
    protected int index;
    protected boolean isVisible;
    
    /**
     * Constructs a new map layer from a List of Tiles.  
     */
    public MapLayer(List/**/ layerTiles, final int in, boolean vis)
    {
        tiles = layerTiles;
        index = in;
        isVisible = vis;
        CoreSystem.print(toString());
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
        for(int i = 0; i < tiles.size(); ++i)
        {
            Tile t = (Tile)tiles.get(i);
            if(t.xCoord == x && t.yCoord == y)
            {
                return true;
            }
        }
        return false;
    }
    
    public String toString()
    {
        return "MapLayer: tileCount " + tiles.size() + ", index " 
                + index + ", visible: " + isVisible;
    }
}
