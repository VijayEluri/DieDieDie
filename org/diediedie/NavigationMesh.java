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


/**
 * NavMesh. Generated for a Level using inner class MeshMaker.
 */ 
public class NavigationMesh implements Drawable
{
    // walkable zones (Lines, as we're in 2D)
    private Collection<Shape> walkableZones;
    private Collection<Shape> negativeSpace;
    private Level level; 
    private Color walkableColor = Color.green;
    private Color negativeColor = Color.orange;
    
    
    /**
     * A mesh created by MeshMaker.
     */ 
    public NavigationMesh(Level l, Collection<Shape> walkables,
                                   Collection<Shape> space)
    {
        setLevel(l);
        walkableZones = walkables;
        negativeSpace = space;
    }
    
    @Override
    public void setLevel(Level l)
    {
        level = l;
    }
    
    @Override
    public void draw(Graphics g)
    {
        g.setColor(walkableColor);
        for(Shape l : walkableZones)
        {
            if(l != null)
            {
                g.draw(l);
            }
        }
        for(Shape n : negativeSpace)
        {
            if(n != null)
            {
                g.draw(n);
            }
        }
    }
    
    /*
     * Returns the walkable zones (excluding non-surface negative spaces)  
     */
    public Collection<Shape> getWalkableZones()
    {
        return walkableZones;
    }

    /*
     * Returns the negative space zones
     */
    public Collection<Shape> getNegativeSpace()
    {
        return negativeSpace;
    }
    
    /**
     * Inner class that generates a NavigationMesh from a game
     * Level
     */ 
    public static class MeshMaker
    {
        static Collection<Tile> ledgeTiles;
        static Collection<Shape> walkableZones;
        static Collection<Shape> negativeSpace;
        
        /**
         * Generates a NavigationMesh for the given Level.
         */ 
        public static NavigationMesh generateMesh(Level l)
        {
            createWalkableZones(l);
            createNegativeSpace(l);
            return new NavigationMesh(l, walkableZones, negativeSpace);
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
         * Discovers and assembles a collection of negative space for
         * a given Level
         */ 
        private static void createNegativeSpace(Level l)
        {
            negativeSpace = new ArrayList<Shape>();
            // ^ polygons
            
            List<Tile> spaces = getSpaceTiles(l);
            List<List<Tile>> slices = slicespaces(spaces);
            
            for(List<Tile> sl : slices)
            {
                System.out.println("Slice Tiles:");
                printTileCollection(sl);
            }
        }
        
        /*
         * Slices up the collection of negative space Tiles into
         * continuous vertical 'slices' for later analysis 
         */ 
        private static List<List<Tile>> slicespaces(List<Tile> spaces)
        {
            Set<Tile> checked = new HashSet<Tile>(); 
            
            List<List<Tile>> slices = new ArrayList<List<Tile>>();
            
            for(int i = 0; i < spaces.size(); ++i)
            {
                if(!checked.contains(spaces.get(i)))
                {
                    List<Tile> current = createNewVerticalSlice(
                                                        spaces, i);
                    checked.addAll(current);
                    slices.add(current);
                }
            }
            return slices;
        }
        
        
        /*
         * Creates a new vertical 'slice' of Tiles starting from the 
         * Tile at the given start position.
         */ 
        private static List<Tile> createNewVerticalSlice(List<Tile> 
                                                  spaceTiles, int start)
        {
            int currentX = spaceTiles.get(start).xCoord;
            int currentY = spaceTiles.get(start).yCoord;
            
            List<Tile> slice = new ArrayList<Tile>();
            slice.add(spaceTiles.get(start));
            
            for(int i = start+1; i < spaceTiles.size(); ++i)
            {
                Tile t = spaceTiles.get(i);
                if(t.xCoord == currentX)
                {
                    if(t.yCoord == currentY + 1)
                    {
                        slice.add(t);
                        currentY = t.yCoord;
                    }
                } 
            }
            return slice;
        }
        
        /*
         * Returns a list of tiles from the background layer that do
         * not have a copy (i.e. same coordinates) in the collision
         * layer.
         */ 
        private static List<Tile> getSpaceTiles(Level l)
        {
            MapLayer colls = l.getCollisionLayer();
            MapLayer bgrnd = l.getBackgroundLayer();
            
            List<Tile> spaceTiles = new ArrayList<Tile>();

            for(Tile b : bgrnd.tiles)
            {
                if(!colls.containsTile(b.xCoord, b.yCoord))
                {
                    spaceTiles.add(b);
                }
            }
            return spaceTiles;
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
            /*System.out.println("\tnew walk line from " 
                    + start.getCoords() + " | to | " + prev.getCoords());*/
            Line line = new Line(start.xPos, start.yPos,
                                 prev.endX, prev.yPos);
            start = null;
            prev = null;
            return line;
        }
        
        /*
         * Prints out information about a collection of Tiles for
         * debugging 
         */ 
        private static void printTileCollection(Collection<Tile> c)
        {
            for(Tile t : c)
            {
                System.out.println("\tx: " + t.xCoord + ", y: " +
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
         * Returns true if two given Tiles are +/- by one vertically
         * and equal horizontally
         */ 
        private static boolean areVerticalNeighbours(Tile original,
                                                     Tile possible)
        {
            if(original.xCoord == possible.xCoord)
            {
                if(possible.yCoord == (original.yCoord + 1)
                    ||
                   possible.yCoord == (original.yCoord - 1))
                {
                   /* System.out.println("vert neighbours: " + original +
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
         * Returns true if there is no collision tile directly above a 
         * given collision tile.
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
