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
import org.diediedie.actors.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Graphics;

import java.util.*;

/**
 * A single level...
 */ 
public class NavigationMesh
{
    List<Line> walkableZones;
    public NavigationMesh(Level l)
    {
        
    }

    public List<Line> getWalkableZones()
    {
        return walkableZones;
    }

    
    /**
     * Generates a NavigationMesh from a Level(TileBasedMap)
     */ 
    public static class NavigationMeshMaker
    {
        /**
         * Generates a NavigationMesh for the given Level.
         */ 
        public static NavigationMesh generateMesh(Level l)
        {
            createWalkableZones(l);
            
            return null;
        }
        
        private static void createWalkableZones(Level l)
        {           
            Set<Line> walkableZones = new HashSet<Line>();
            List<Tile> ledgeTiles = getLedgeTiles(l);
            
            for(Tile t : ledgeTiles)
            {
                
            }
            
            
        }
        
        /*
         * Returns collision Tiles from the Level which  
         */ 
        private static List<Tile> getLedgeTiles(Level l)
        {
            MapLayer colls = l.getCollisionLayer();
            List<Tile> tiles = colls.tiles;
            List<Tile> ledgeTiles = new ArrayList<Tile>();

            for(Tile t : tiles)
            { 
                // check if there is negative space above this Tile
                // naturally if at vertical position 0, there can't be an 
                // 'above'
                if(t.getYCoord() > 0)
                {
                    if(!colls.containsTile(t.getYCoord() - 1, t.getXCoord()))
                    {
                        // ok got an empty space so add t to ledgeTiles
                        ledgeTiles.add(t);
                    }
                }
            }
            return ledgeTiles;
        }
        
    }
}
