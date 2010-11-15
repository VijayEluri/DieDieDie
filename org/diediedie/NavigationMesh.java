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
import org.diediedie.actors.*;
import java.io.*;
import java.util.*;
import org.diediedie.actors.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

import java.util.*;

/**
 * A single level...
 */ 
public class NavigationMesh implements Drawable
{
    private Collection<Shape> walkableZones;
    private Level level; 
    private Color color = Color.green;
    
    public NavigationMesh(Level l, Collection<Shape> walkables)
    {
        level = l;
        walkableZones = walkables;
    }
    
    @Override
    public void setLevel(Level l)
    {
        level = l;
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.setColor(color);
        for(Shape l : walkableZones)
        {
            if(l != null)
            {
                g.draw(l);
            }
        }
    }
    
    public Collection<Shape> getWalkableZones()
    {
        return walkableZones;
    }

    
    /**
     * Generates a NavigationMesh from a Level(TileBasedMap)
     */ 
    public static class MeshMaker
    {
        static Collection<Tile> ledgeTiles;
        static Collection<Shape> walkableZones;
        
        /**
         * Generates a NavigationMesh for the given Level.
         */ 
        public static NavigationMesh generateMesh(Level l)
        {
            createWalkableZones(l);
            
            return new NavigationMesh(l, walkableZones);
        }
        
        /*
         * Examines collision tiles and saves those which have negative
         * space above them. 
         */
        private static void createWalkableZones(Level l)
        {   
            Line line;        
            ledgeTiles = getLedgeTiles(l);
            Set<Tile> checked = new HashSet<Tile>();
            walkableZones = new HashSet<Shape>();
            
            Tile end = null;

            
            for(Tile start : ledgeTiles)
            {
                if(!checked.contains(start) && start != null)
                {
                    end = getEndTile(start, ledgeTiles);
                    line = makeWalkLine(start, end);
                    walkableZones.add(line);
                }
            }
        }
        
        /*
         * Returns the last Tile linked to the right of the given Tile.  
         * Recursively. B)
         */ 
        private static Tile getEndTile(Tile start, Collection<Tile> tiles)
        {
            for(Tile t : tiles)
            {
                if(t.yCoord == start.yCoord)
                {
                    if(t.xCoord == start.xCoord + 1)
                    {
                        return getEndTile(t, tiles);
                    }
                }
                
            }
            return start;
        }
        
        /*
         * Returns a line from the top surface of 2 (collision) Tiles
         */ 
        private static Line makeWalkLine(Tile start, Tile prev)
        {
            System.out.println("\tnew walk line from " 
                    + start.getCoords() + " | to | " + prev.getCoords()); 
            Line line = new Line(start.xPos, start.yPos, prev.endX, prev.yPos);
            start = null;
            prev = null;
            return line;
        }
        
        private static void printTileCollection(Collection<Tile> c)
        {
            for(Tile t : c)
            {
                System.out.println("\t" + t.xCoord + ", " +
                                          t.yCoord);
            }
        }
        
        /*
         * Returns true if two given Tiles are +/- by one horizontally
         * and equal vertically. [ | | | | ] 
         */ 
        private static boolean areHorizontalNeighbours(Tile original,
                                                       Tile possible)
        {
            if(original.yCoord == possible.yCoord)
            {
                if(possible.xCoord == (original.xCoord + 1)
                    ||
                   possible.xCoord == (original.xCoord - 1))
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
                if(t.yCoord > 0)
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
            if(!colls.containsTile(test.xCoord, test.yCoord - 1))
            {
                /*System.out.println("Tile " + test.xCoord + ", "
                             + test.yCoord + " is ledge tile");*/
                return true;
            }
            return false;
        } 
    }
}
