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
import java.util.concurrent.*;
import org.diediedie.actors.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;


/**
 * NavMesh. Generated for a Level using inner class MeshMaker.
 */ 
public class NavMesh implements Drawable
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
    public NavMesh(Level l, Collection<Shape> walkables,
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
        
        g.setColor(negativeColor);
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
    
    /*
     * Utility class for dealing with a vertical line of adjacent Tiles
     */ 
    public static class Slice 
    {
        public List<Tile> tiles = null;
         
        public Slice(List<Tile> t)
        {
            tiles = t;
            if(!hasValidTiles())
            {
                throw new IllegalStateException("Invalid Slice");
            }
        }
        
        public Tile getFirstTile()
        {
            return tiles.get(0);
        }
        
        public Tile getLastTile()
        {
            return tiles.get(tiles.size()-1);
        }

        
        /*
         * For de-bugging 
         */ 
        public void printSliceInfo()
        {
            Tile s, e;
            
            s = tiles.get(0);
            e = tiles.get(tiles.size() - 1);
            
            System.out.println("Slice " + hashCode());
            System.out.println("\t" + s.xCoord + ", " + s.yCoord + " to "
                               + e.xCoord + ", " + e.yCoord);            
        }
        
        /*
         * Returns true if the Tile list is invalid (i.e. each tile has 
         * the same xCoord as the previous one and its yCoord == the 
         * previous yCoord + 1
         */ 
        public boolean hasValidTiles()
        {
            if(tiles.isEmpty())
            {
                return false;
            }
            if(tiles.size() == 1)
            {
                return true;
            }
            
            int x = tiles.get(0).xCoord;
            int y = tiles.get(0).yCoord;
            
            for(int i = 1; i < tiles.size(); ++i)
            {
                Tile cur = tiles.get(i);
                if(cur.xCoord != x || cur.yCoord != y + 1)
                {
                    return false;    
                }
                y = cur.yCoord;
            }
            return true;
        }
    }
    
    /**
     * A bunch of connected, identically-shaped Slices.
     */ 
    public static class SliceGroup
    {
        public List<Slice> slices;
        private Shape rect = null;
        
        /**
         * 
         */ 
        public SliceGroup(List<Slice> s)
        {
            if(s.isEmpty()) 
            {
                throw new IllegalStateException();
            }
            slices = s;
            createShape();
        }
        
        
        
        /*
         * Creates a Shape encompassing the 
         */ 
        private void createShape()
        {
            Slice startSlice = getStartSlice();
            Slice endSlice = getEndSlice();
            
            Tile first = startSlice.tiles.get(0);
            Tile last = endSlice.tiles.get(endSlice.tiles.size() - 1);
            
            /*System.out.println("SliceGroup: createShape() \n\tfrom "
                                + first + "\n\t to " + last);*/

            rect = new Rectangle(first.xPos, first.yPos, 
                                 (last.endX - first.xPos),
                                  (last.endY - first.yPos));
        }        
        
        /**
         * Tries to add a Slice to the end of this Group. Returns true
         * if successful.
         */ 
        public boolean addSlice(Slice s)
        {
            if(MeshMaker.areCompatible(getEndSlice(), s))
            {
                if(slices.add(s))
                {
                    createShape();
                    return true;
                }
            }
            return false;
        }
        
        public Slice getEndSlice()
        {
            return slices.get(slices.size() - 1);
        }
        
        public Slice getStartSlice()
        {
            return slices.get(0);
        }
        
        /**
         * Returns the Shape encompassing this group of Slices
         */ 
        public Shape getShape()
        {
            return rect;
        }
        
        /*
         * Prints out the contents of each slice in the group
         */ 
        public void printGroupInfo()
        {
            PrintStream out = System.out;
            out.println("SliceGroup " + hashCode() + " (" + slices.size()
                            + " slices) : ");
           
            for(Slice s : slices)
            {
                s.printSliceInfo();
            }
            out.println();
        }
    }
    
    /**
     * Inner class that generates a NavMesh from a Level
     */ 
    public static class MeshMaker
    {
        static Collection<Tile> ledgeTiles;
        static Collection<Shape> walkableZones;
        static Collection<Shape> negativeSpace;
        
        /**
         * Generates a NavMesh for the given Level.
         */ 
        public static NavMesh generateMesh(Level l)
        {
            createWalkableZones(l);
            createNegativeSpace(l);
            return new NavMesh(l, walkableZones, negativeSpace);
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
         * Discovers and assembles a collection of negative space Shapes
         * for a given Level
         */ 
        private static void createNegativeSpace(Level l)
        {
            // movable area polygons
            negativeSpace = new ArrayList<Shape>();
                        
            // slice the spaces and combine where possible
            List<Slice> slices = sliceSpaces(getSpaceTiles(l));
            List<SliceGroup> groups = new ArrayList<SliceGroup>();
            
            /*final int ITER = 10;
            int size = 0;*/
            
            groups.addAll(combineSlices(slices));
            System.out.println("\tSliceGroups: " + groups.size());   
                                               
            for(SliceGroup g : groups)
            {
                g.printGroupInfo();
                negativeSpace.add(g.getShape());
            }
        }
        
        /*
         * Combines compatible slices of Tiles together into 
         * SliceGroups which are returned. 
         * 
         * Slices combined are removed from the given list.
         */
        private static List<SliceGroup> combineSlices(List<Slice> slices)
        {                   
            ListIterator<Slice> slit = slices.listIterator();
            List<SliceGroup> groups = new ArrayList<SliceGroup>();
             
            // store the current lot of compatible Slices here when
            // traversing the given list
            List<Slice> currentSlices = new ArrayList<Slice>();
            
            // store successfully combined slices here so we can remove
            // them from the given list
            List<Slice> combinedSlices = new ArrayList<Slice>();
            
            
            while(!slices.isEmpty())
            {
                System.out.println("createNewGroup (" + slices.size() +
                                " slices left)");
                SliceGroup sg = createNewGroup(slices);
                
                groups.add(sg);
            }
            
            return groups;
        }
        
        /*
         * Creates a new SliceGroup from the given Slice list
         * 
         * 
         */ 
        private static SliceGroup createNewGroup(List<Slice> allSlices)
        {
            if(allSlices.isEmpty())
            {
                return null;
            }
            
            Slice first = allSlices.get(0);
            allSlices.remove(0);
            List<Slice> currentSlices = new LinkedList<Slice>();
            currentSlices.add(first);
            
            SliceGroup sg = new SliceGroup(currentSlices);
            
            while(addAllPossible(sg, allSlices) > 0);
            
            
            return sg;
        }
        
        
        /**
         * Adds as many slices to the given SliceGroup as possible.
         * 
         * Any slices added to the given group are removed from the
         * given List.
         * 
         * If the group is empty the first element from the given 
         * list is used
         * 
         * Returns the amount added (zero of higher). 
         */ 
        private static int addAllPossible(SliceGroup g, 
                                          List<Slice> slices)
        {
            boolean addition = false;
            int count = 0;
            
            if(slices.isEmpty())
            {
                return 0;
            }
            
            if(g.slices.isEmpty())
            {
                g.addSlice(slices.get(0));
                slices.remove(0);
                ++count;
                addition = true;
                
            }
            
            ListIterator<Slice> slit = slices.listIterator();
            
            while(slit.hasNext())
            {
                Slice s = slit.next();
                
                if(g.addSlice(s))
                {
                    ++count;
                    addition = true;
                    slit.remove();
                }
            }
            
            if(addition)
            {
                count += addAllPossible(g, slices);
            }
            return count;
        }
            
        
        private static void addSlicesToGroups(List<Slice> slices,
                                              List<SliceGroup> groups)
        {
            if(!slices.isEmpty())
            {
                for(SliceGroup sg : groups)
                {
                    ListIterator<Slice> lit = slices.listIterator();
                    while(lit.hasNext())
                    {
                        Slice s = lit.next();
                        
                        // try to add s to the current group
                        if(sg.addSlice(s))
                        {
                            lit.remove();
                        }
                    }
                }
            }      
        }
        
        /*
         * Returns true if two given vertical Slices can be joined.
         */ 
        private static boolean areCompatible(Slice a, Slice b)
        {
            if(a.equals(b))
            {
                return false;
            }
            
            Tile aTile = a.tiles.get(0), bTile = b.tiles.get(0);            
            if(b.tiles.size() == a.tiles.size() 
                && 
              (bTile.xCoord == aTile.xCoord + 1 || 
               bTile.xCoord == aTile.xCoord - 1)  )
            {
                return true;
            }
            
            return false;
        }
        
        /*
         * Slices up the collection of negative space Tiles into
         * continuous vertical 'slices' for later analysis 
         */ 
        private static List<Slice> sliceSpaces(List<Tile> spaces)
        {
            Set<Tile> checked = new HashSet<Tile>(); 
            List<Slice> slices = new ArrayList<Slice>();
            
            for(int i = 0; i < spaces.size(); ++i)
            {
                if(!checked.contains(spaces.get(i)))
                {
                    Slice current = createNewVerticalSlice(spaces, i);
                    checked.addAll(current.tiles);
                    slices.add(current);
                }
            }
            return slices;
        }
        
        
        /*
         * Creates a new vertical 'slice' of Tiles starting from the 
         * Tile at the given start position.
         */ 
        private static Slice createNewVerticalSlice(List<Tile> 
                                                  spaceTiles, int start)
        {
            int currentX = spaceTiles.get(start).xCoord;
            int currentY = spaceTiles.get(start).yCoord;
            
            List<Tile> sliceTiles = new ArrayList<Tile>();
            sliceTiles.add(spaceTiles.get(start));
            
            for(int i = start+1; i < spaceTiles.size(); ++i)
            {
                Tile t = spaceTiles.get(i);
                if(t.xCoord == currentX)
                {
                    if(t.yCoord == currentY + 1)
                    {
                        sliceTiles.add(t);
                        currentY = t.yCoord;
                    }
                } 
            }
            Slice sl = new Slice(sliceTiles);
            return sl;
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
