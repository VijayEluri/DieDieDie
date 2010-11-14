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
    Collection<Line> walkableZones;
    
    public NavigationMesh()
    {
        
    }

    public Collection<Line> getWalkableZones()
    {
        return walkableZones;
    }

    
    /**
     * Generates a NavigationMesh from a Level(TileBasedMap)
     */ 
    public static class MeshMaker
    {
        static Collection<Tile> ledgeTiles;
        static Collection<Line> walkableZones;
        
        /**
         * Generates a NavigationMesh for the given Level.
         */ 
        public static NavigationMesh generateMesh(Level l)
        {
            createWalkableZones(l);
            
            return null;
        }
        
        /*
         * Examines collision tiles and saves those which have negative
         * space above them, then sorts them into groups depending on
         * their relative positions.
         */ 
        private static void createWalkableZones(Level l)
        {           
            ledgeTiles = getLedgeTiles(l);
            walkableZones = linkLedgeTiles(ledgeTiles);            
        }
        
        
        private static void printTileCollection(Collection<Tile> c)
        {
            for(Tile t : c)
            {
                System.out.println("\t" + t.getXCoord() + ", " +
                                          t.getYCoord());
            }
        }
        
        /*
         * Links together adjacent the Tiles found with getLedgeTiles.
         */ 
        private static Collection<Line> linkLedgeTiles(Collection<Tile> 
                                                             ledgeTiles)
        {
            Collection<Tile> checkedTiles = new HashSet<Tile>();
            Collection<Line> platforms;
            Collection<Tile> neighbours;
            
            for(Tile t : ledgeTiles)
            {
                if(!checkedTiles.contains(t))
                {
                    neighbours = new HashSet<Tile>();
                    getNeighbours(t, ledgeTiles, neighbours);
                    checkedTiles.addAll(neighbours); 
                    //System.out.println("Ledge row: " );
                    //printTileCollection(neighbours);                  
                }
            }
            
            return null;
        }
        
        
        private static Line getLedgeLine(Collection<Tile> ledge)
        {
            return null;
        }
        
        /*
         * Returns the Tiles in a given collection that form a ledge,
         * starting from a given Tile.
         */ 
        private static void getNeighbours(Tile t, Collection<Tile> 
                                          ledgeTiles, Collection<Tile> dest)
        {            
            for(Tile l : ledgeTiles)
            {
                if(!dest.contains(l))
                {
                    if(areHorizontalNeighbours(t, l))
                    {
                        dest.add(l);
                        getNeighbours(l, ledgeTiles, dest);
                    }
                }
            }
        }
        
        /*
         * Returns true if two given Tiles are +/- by one horizontally
         * and equal vertically. [ | | | | ] 
         */ 
        private static boolean areHorizontalNeighbours(Tile original,
                                                       Tile possible)
        {
            if(original.getYCoord() == possible.getYCoord())
            {
                if(possible.getXCoord() == (original.getXCoord()+1)
                    ||
                   possible.getXCoord() == (original.getXCoord()-1))
                {
                   /* System.out.println("hori neighbours: " + original +
                                        ", " + possible);*/
                    return true;   
                }
            }
            return false;
        }
        
        /*
         * Finds the collision Tiles from the Level that can be used as
         * ledges.   
         */ 
        private static Collection<Tile> getLedgeTiles(Level l)
        {
            MapLayer colls = l.getCollisionLayer();
            Collection<Tile> ledgeTiles = new ArrayList<Tile>();
            
            for(Tile t : colls.tiles)
            { 
                // naturally if at vertical position 0, there can't be a
                // 'higher' level (read: lower number)
                if(t.getYCoord() > 0)
                {        
                    if(isLedgeTile(t, colls))
                    {
                        ledgeTiles.add(t);
                    }
                }
            }
            
            return ledgeTiles;
        }
        
        /*
         * Returns true if there is no collision tile above a given 
         * collision tile.
         */
        private static boolean isLedgeTile(Tile test, MapLayer colls)
        {
            if(!colls.containsTile(test.getXCoord(), test.getYCoord()-1))
            {
                /*System.out.println("Tile " + test.getXCoord() + ", "
                             + test.getYCoord() + " is ledge tile");*/
                return true;
            }
            return false;
        } 
    }
}
