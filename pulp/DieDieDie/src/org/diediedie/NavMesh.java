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
//import org.diediedie.actors.*;
import java.io.*;
import java.util.*;
//import java.awt.geom.Line;
import java.util.concurrent.*;
import org.diediedie.Line;
//import org.diediedie.actors.Player;
//import org.diediedie.actors.Enemy;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;
import pulpcore.image.Colors;
import pulpcore.animation.Color;
import pulpcore.math.Rect;
//import org.newdawn.slick.geom.Shape;
/**
 * NavMesh. Generated for a Level using inner class MeshMaker.
 */ 

public class NavMesh implements Drawable
{
    // walkable zones (Lines, as we're in 2D)
    
    private Collection<Line> walkableZones;
    private Collection<Rect> negativeSpace;
    private Level level;
    
    private int walkableColorInt = Colors.GREEN;
    private Color walkableColor = new Color(walkableColorInt);
    
    private int negativeColorInt = Colors.ORANGE;
    private Color negativeColor = new Color(Colors.ORANGE);
    
    /**
     * A mesh created by MeshMaker.
     */ 
    public NavMesh(Level l, Collection<Line> walkables,
                            Collection<Rect> space)
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
    public void draw(CoreGraphics g)
    {
        g.setColor(walkableColorInt);
        for(Line l : walkableZones)
        {
            if(l != null)
            {
                g.drawLine(l.startX, l.startY,
                           l.endX, l.endY);
            }
        }
        
        g.setColor(negativeColorInt);
        for(Rect r : negativeSpace)
        {
            if(r != null)
            {
                g.drawRect(r.x, 
                           r.y, 
                           r.width, 
                           r.height);
            }
        }
    }
    
    /*
     * Returns the walkable zones (excluding non-surface negative 
     * spaces) as a series of horizontal lines
     */
    public Collection<Line> getWalkableZones()
    {
        return walkableZones;
    }

    /*
     * Returns the negative space zones (rectangles)
     */
    public Collection<Rect> getNegativeSpace()
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
            
            CoreSystem.print("Slice " + hashCode());
            CoreSystem.print("\t" + s.xCoord + ", " + s.yCoord + " to "
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
     * A bunch of connected, identically-shaped and horizontally 
     * adjacent Slices.
     */ 
    public static class SliceGroup
    {
        public List<Slice> slices;
        private Rect rect = null;
        
        /**
         * Assembles a group from a list.
         */ 
        public SliceGroup(List<Slice> s)
        {
            if(s.isEmpty()) 
            {
                throw new IllegalStateException();
            }
            slices = s;
            //createSlicesRect();
            createRect();
        }
        
        /*
         * Creates a Shape encompassing the mesh
         */ 
        private void createRect()
        {
            Slice startSlice = getStartSlice();
            Slice endSlice = getEndSlice();
            
            Tile first = startSlice.tiles.get(0);
            Tile last = endSlice.tiles.get(endSlice.tiles.size() - 1);
            
            /*CoreSystem.print("SliceGroup: createShape() \n\tfrom "
                                + first + "\n\t to " + last);*/

            rect = new Rect(
                 (int)first.xPos, (int)first.yPos, 
                ((int)last.endX - (int)first.xPos),
                ((int)last.endY - (int)first.yPos));
            check();
        }        
        
        private void check()
        {
            if(slices.size() == 1)
            {
                return;
            }
            
            Slice prev, curr;
            
            for(int i = 1; i < slices.size(); ++i)
            {
                prev = slices.get(i-1);
                curr = slices.get(i);
                
                if(!MeshMaker.areCompatible(prev, curr))
                {
                    throw new IllegalStateException();
                }
            }
        }
    
        /**
         * Tries to add a Slice to the end of this Group. Returns true
         * if successful.
         */ 
        public boolean addSlice(Slice s)
        {
            /*CoreSystem.print("SliceGroup.(" + hashCode() + 
                               "(addSlice:");*/
            if(MeshMaker.areCompatible(getEndSlice(), s))
            {
                if(slices.add(s))
                {
                    /*CoreSystem.print("\t[successfully added slice]");*/
                    //createShape();
                    createRect();
                    return true;
                }
                else
                {
                    System.exit(0);
                }
            }
            return false;
        }
        
        /**
         * Returns the last slice from this group
         */ 
        public Slice getEndSlice()
        {
            Slice s = slices.get(slices.size() - 1);
           /* CoreSystem.print("SliceGroup(" + hashCode() + 
                               ").getEndSlice(): {");
            s.printSliceInfo();
            CoreSystem.print("}");*/
            
            return s;
        }
        
        /**
         * Returns the first slice from this group
         */ 
        public Slice getStartSlice()
        {
            Slice s = slices.get(0);
            /*CoreSystem.print("SliceGroup(" + hashCode() + 
                               ").getStartSlice(): {");
            s.printSliceInfo(); 
            CoreSystem.print("}");*/
            return s;
        }
        
        /**
         * Returns a Shape encompassing this group of Slices
         */ 
        public Rect getSlicesRect()
        {
            return rect;
        }
        
        /**
         * Attempts to add the contents of the given SliceGroup to 
         * this. 
         * 
         * Returns true if
         */ 
        public boolean addGroup(SliceGroup other)
        {
            if(MeshMaker.areCompatible(this, other))
            {
                /*printGroupInfo();
                CoreSystem.print("addGroup: ");
                other.printGroupInfo();*/
                
                for(Slice s : other.slices)
                {
                    if(!addSlice(s))
                    {
                        throw new IllegalStateException("Not compatible?");
                    }
                    /*else
                    {
                        CoreSystem.print(
                            "added slice from other group: ");
                        s.printSliceInfo();
                    }*/
                }
                return true;
            }
            /*else
            {
                CoreSystem.print("addGroup: incompatible");
            }*/
            
            return false;
        }
        
        /**
         * Prints out the contents of each slice in the group
         */ 
        public void printGroupInfo()
        {
            PrintStream out = System.out;
            out.println("SliceGroup " + hashCode() + 
                        " (" + slices.size() + " slices) : ");
           
            for(Slice s : slices)
            {
                s.printSliceInfo();
            }
            out.println();
        }
    }
    
    /**
     * Inner factory class that generates a NavMesh from a Level
     */ 
    public static class MeshMaker
    {
        static Collection<Tile> ledgeTiles = null;
        static Collection<Line> walkableZones = null;
        static Collection<Rect> negativeSpace = null;
        static Level l = null;
        
        /**
         * Generates a NavMesh for the given Level.
         */ 
        public static NavMesh generateMesh(Level lev)
        {
            l = lev;
            createWalkableZones();
            createNegativeSpace();
            return new NavMesh(l, walkableZones, negativeSpace);
        }
        
        /*
         * Examines collision tiles and saves those which have enough
         * negative space above them for the player to move through. 
         */
        private static void createWalkableZones()
        {   
            Line line;        
            ledgeTiles = getLedgeTiles(l);
            Set<Tile> checked = new HashSet<Tile>();
            walkableZones = new HashSet<Line>();
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
        private static void createNegativeSpace()
        {
            // movable area rects
            negativeSpace = new ArrayList<Rect>();
                        
            // slice the spaces and combine where possible
            List<Slice> slices = sliceSpaces(getSpaceTiles(l));
            List<SliceGroup> groups = new ArrayList<SliceGroup>();
            
            groups.addAll(combineSlices(slices));
            combineGroups(groups);
                                               
            for(SliceGroup g : groups)
            {
                //negativeSpace.add(g.getShape());
                negativeSpace.add(g.getSlicesRect());
                
            }            
        }
        
        /*
         * Attempts to combine any (and all) of the given SliceGroups
         * together.
         */ 
        private static void combineGroups(List<SliceGroup> groups)
        {
           /* CoreSystem.print("combineGroups: looking at " +
                                groups.size());*/
                                
            SliceGroup prev = null;
            SliceGroup curr = null;
            
            ListIterator<SliceGroup> it = groups.listIterator();
            
            while(it.hasNext())
            {
                if(it.hasPrevious())
                {
                    prev = it.previous();
                    it.next();
                    curr = it.next();
                   
                    if(curr.equals(prev))
                    {
                        throw new IllegalStateException();
                    }
                    
                    if(areCompatible(prev, curr))
                    {
                        prev.addGroup(curr);
                        it.remove();
                        it.next();
                    }
                }
                else
                {
                    it.next();       
                }
            }
        }
             
        /*
         * Returns true if the end Slice of a is compatible with the
         * start Slice of b.
         */ 
        private static boolean areCompatible(SliceGroup a, SliceGroup b)
        {    
            if(areCompatible(a.getEndSlice(), b.getStartSlice()))
            {
                return true;
            }
            return false;
        }
        
        /*
         * Combines compatible slices of Tiles together into 
         * SliceGroups which are returned. 
         * 
         * Slices combined are removed from the given list.
         */
        private static List<SliceGroup> combineSlices(
            List<Slice> slices)
        {                   
            List<SliceGroup> groups = new ArrayList<SliceGroup>();
            
            while(!slices.isEmpty())
            {
                SliceGroup sg = createNewGroup(slices);
                groups.add(sg);
            }
            
            return groups;
        }
        
        
        /*
         * Creates a new SliceGroup from the first given Slice list
         */ 
        private static SliceGroup createNewGroup(List<Slice> allSlices)
        {
            if(allSlices.isEmpty())
            {
                CoreSystem.print("end of slices; end of groups");
                return null;
            }      
            List<Slice> currentSlices = new LinkedList<Slice>();
            currentSlices.add(allSlices.remove(0));        
            SliceGroup g = new SliceGroup(currentSlices);
            
            int added = 0;
            do{
                
                ListIterator<Slice> lit = allSlices.listIterator();
                added = 0;
                while(lit.hasNext())
                {
                    Slice s = lit.next();
                    
                    if(g.addSlice(s))
                    {
                        added++;
                        CoreSystem.print("|--adding Slice : ");
                        s.printSliceInfo();
                        lit.remove();
                    }
                }
            } while(added > 0);
            return g;
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
            
            if(areHorizontalNeighbours(aTile, bTile) 
                &&
               b.tiles.size() == a.tiles.size()     )
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
            CoreSystem.print("\tnew walk line from " 
                + start.getCoords() + " to " + prev.getCoords());
            Line line = new Line((int)start.xPos, (int)start.yPos,
                                 (int)prev.endX,  (int)prev.yPos);
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
                CoreSystem.print("\tx: " + t.xCoord + ", y: " +
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
                   /* CoreSystem.print("hori neighbours: " + original +
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
                   /* CoreSystem.print("vert neighbours: " + original +
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
                /*CoreSystem.print("Tile " + test.xCoord + ", "
                             + test.yCoord + " is ledge tile");*/
                return true;
            }
            return false;
        } 
    }
}
