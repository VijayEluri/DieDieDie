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
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;

/**
 * Represents a layer of Tiles
 */ 
public class MapLayer 
{
    protected List<Tile> tiles;
    protected int index;
    protected boolean isVisible;
    
    /**
     * Constructs a new map layer from a List of Tiles.  
     */
    public MapLayer(List<Tile> layerTiles, final int in, boolean visible)
    {
        tiles = layerTiles;
        index = in;
        isVisible = visible;
        System.out.println(toString());
    }
    
    public String toString()
    {
        return "MapLayer: tileCount " + tiles.size() + ", index " 
                + index + ", visible: " + isVisible;
    }
}
