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
package org.diediedie.level;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import org.diediedie.Drawable;
import org.diediedie.level.Tile;
import org.diediedie.level.Level;
import org.diediedie.level.MapLayer;
import org.diediedie.level.actors.tools.Direction;


/**
 * NavMesh. Generated for a Level using inner class MeshMaker.
 */ 
public class NavMesh implements Drawable
{
    // walkable zones
    private Collection<Shape> walkableZones;
    private Collection<NegativeSpace> negativeSpaces;

    private Color walkableColor = Color.green;
    private Color negativeColor = Color.orange;
    
    
    private Map<Shape, List<Tile>> walkableTileMap;
    /*
     * walkableNegativeConnections - maps each walkable area to its intersecting
     * negative space rectangles.
     */
	private Map<Shape, List<Shape>> walkableNegativeConnections;
    
	
    /**
     * A mesh created by MeshMaker.
     */ 
    public NavMesh(Collection<Shape> walkables,
                   Collection<NegativeSpace> space,
                   Map<Shape, List<Tile>> walkableTiles)
    {

        walkableZones = walkables;
        negativeSpaces = space;
        walkableTileMap = walkableTiles;
        linkWalkableZonesToNegativeSpace();
        linkNegativeSpace();
    }
    
    public Map<Shape, List<Tile>> getWalkableTilesMap()
    {
    	return walkableTileMap;
    }
    
    public Map<Shape, List<Shape>> getWalkSpaceMap()
    {
    	return walkableNegativeConnections;
    }
    
    // boiler plate
    public void setLevel(Level l)
    {
    	return;
    }
    
    private void linkNegativeSpace()
    {    	
    	Iterator<NegativeSpace> nit = negativeSpaces.iterator();
    	
    	while(nit.hasNext())
    	{
    		NegativeSpace current = nit.next();
    		current.addNeighbours(negativeSpaces);
    	}
    }    
    
	private void linkWalkableZonesToNegativeSpace()
    {
		walkableNegativeConnections = new HashMap<Shape, List<Shape>>();
		
		List<Shape> connected;
		
		for(Shape w : walkableZones)
		{
			connected = new ArrayList<Shape>();
			for(NegativeSpace n : negativeSpaces)
			{
				if(w.intersects(n.getShape()))
				{
					connected.add(n.getShape());
				}
			}
			walkableNegativeConnections.put(w, connected);
		}
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
        
        for(NegativeSpace n : negativeSpaces)
        {
            n.draw(g);
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
    public Collection<NegativeSpace> getNegativeSpace()
    {
        return negativeSpaces;
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
        
        public List<Tile> getTiles()
        {
        	return tiles;
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
    
    /*
     * Returns the negative space object containing this Point, or 
     * null if there isn't one.
     */
    public NegativeSpace getNegativeSpaceFromPoint(Point p)
    {
    	for(NegativeSpace n : negativeSpaces)
    	{
    		if(n.getShape().contains(p.x, p.y))
    		{
    			return n;
    		}
    	}
    	return null;
    }
    
    
    /**
     * A bunch of connected, identically-shaped and horizontally 
     * adjacent Slices.
     */ 
    public static class SliceGroup
    {
        public List<Slice> slices;
        private Shape rect = null;
        
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
            createShape();
        }
        
        /*
         * 
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
            /*System.out.println("SliceGroup.(" + hashCode() + 
                               "(addSlice:");*/
            if(MeshMaker.areCompatible(getEndSlice(), s))
            {
                if(slices.add(s))
                {
                    /*System.out.println("\t[successfully added slice]");*/
                    createShape();
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
           /* System.out.println("SliceGroup(" + hashCode() + 
                               ").getEndSlice(): {");
            s.printSliceInfo();
            System.out.println("}");*/
            
            return s;
        }
        
        /**
         * Returns the first slice from this group
         */ 
        public Slice getStartSlice()
        {
            Slice s = slices.get(0);
            /*System.out.println("SliceGroup(" + hashCode() + 
                               ").getStartSlice(): {");
            s.printSliceInfo(); 
            System.out.println("}");*/
            return s;
        }
        
        /**
         * Returns a Shape encompassing this group of Slices
         */ 
        public Shape getShape()
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
                System.out.println("addGroup: ");
                other.printGroupInfo();*/
                
                for(Slice s : other.slices)
                {
                    if(!addSlice(s))
                    {
                        throw new IllegalStateException("Not compatible?");
                    }
                    /*else
                    {
                        System.out.println(
                            "added slice from other group: ");
                        s.printSliceInfo();
                    }*/
                }
                return true;
            }
            /*else
            {
                System.out.println("addGroup: incompatible");
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
        static List<Tile> ledgeTiles = null;
        
        /*
         * walkableTilesMap - this is used to link walkable area shapes
         * with lists of Tiles in order to help optimize collision
         * detection.
         */
        static Map<Shape, List<Tile>> walkableTilesMap = null;
        static Collection<Shape> walkableZones = null;
        static Collection<NegativeSpace> negativeSpace = null;
        static Level l = null;
        
        /**
         * Generates a NavMesh for the given Level.
         */ 
        public static NavMesh generateMesh(Level lev)
        {
            l = lev;
            createWalkableZones();
            createNegativeSpace();
            return new NavMesh(walkableZones, negativeSpace, walkableTilesMap);
        }
        
		private static void createWalkableZones()
        {   
            ledgeTiles = getLedgeTiles(l);

            if(ledgeTiles.isEmpty())
            {
            	System.out.println("No ledge tiles found on this level!");
            	System.exit(-1);
            }
            
            //Set<Tile> checked = new HashSet<Tile>();
            walkableZones = new HashSet<Shape>();
            List<List<Tile>> walkableTileList = new ArrayList<List<Tile>>();
            Collections.sort(ledgeTiles);
            List<Tile> ledgeTiles2 = 
            	Collections.synchronizedList(new ArrayList<Tile>(ledgeTiles));

            walkableTileList = splitWalkableZones(ledgeTiles2);
            
            walkableTilesMap = new HashMap<Shape, List<Tile>>();
            
                        
            for(List<Tile> tl : walkableTileList)
            {
            	/*System.out.println("Walkable Tile List");
            	for(Tile t : tl)
            	{
            		System.out.println("\t" + t.xCoord + ", " + t.yCoord);
            	}
            	System.out.println("\n");*/
            	Shape s = makeWalkShape(tl.get(0), tl.get(tl.size()-1));
            	walkableZones.add(s);
            	walkableTilesMap.put(s, tl);
            }
        }
        
        private static List<List<Tile>> splitWalkableZones(List<Tile> ledges)
        {
        	List<List<Tile>> walkableTileList = new ArrayList<List<Tile>>();
        	Tile previous = null;
        	List<Tile> currentNbrs = null;
        	
        	synchronized(ledges)
        	{
        		Iterator<Tile> tit = ledges.iterator();
	        	while(!ledges.isEmpty())
	        	{
	        		Tile current = tit.next();
	        		/*System.out.println(
        				 "current : " 
        				+ current.xCoord 
        				+ ", "
        				+ current.yCoord);*/
	        		
	        		if(previous == null)
	        		{
	        			/*System.out.println(
	        				"\t--> current is first Tile : " +
	        				"creating new list for it");
	        			*/
	        			currentNbrs = new ArrayList<Tile>();
	        			currentNbrs.add(current);
	        		}
	        		else if(previous.xCoord == (current.xCoord - 1)
	        					&&
	        				previous.yCoord == current.yCoord)
	        		{
	        			// not the first Tile -- but adjacent to previous
	        			/*System.out.println(
	        		         "\t--> current is adjacent to previous ("
	        				+ previous.xCoord
	        				+ ", "
	        				+ previous.yCoord
	        				+ ")");*/
	        			currentNbrs.add(current);
	        		}
	        		else
	        		{
	        			// not the first Tile -- nor adjacent to previous
	        			/*System.out.println(
	        				 "\t--> current is not adjacent to previous ("
	        				+ previous.xCoord
	        				+ ", "
	        				+ previous.yCoord);
	        			*/
	        			assert previous.xCoord != (current.xCoord - 1);
	        			
	        			/*System.out.println(
	        				"\t--> adding neighbours list to master list");*/
	        			walkableTileList.add(currentNbrs);
	        			/*System.out.println(
		        				"\t--> creating new list and adding current");*/
	        			currentNbrs = new ArrayList<Tile>();
	        			currentNbrs.add(current);
	        		}
	        		previous = current;
	        		tit.remove();
	        	}
        	}
        	// Add the final neighbour list to the master list
        	walkableTileList.add(currentNbrs);
        	/*System.out.println(
        		"\n\tmade " + walkableTileList.size() + " list(s)");*/
        	
        	return walkableTileList;
        }
        
		/*
         * Discovers and assembles a collection of negative space Shapes
         * for a given Level
         */ 
        private static void createNegativeSpace()
        {
            // movable area polygons
            negativeSpace = new ArrayList<NegativeSpace>();
                        
            // slice the spaces and combine where possible
            List<Slice> slices = sliceSpaces(getSpaceTiles(l));
            List<SliceGroup> groups = new ArrayList<SliceGroup>();
            
            groups.addAll(combineSlices(slices));
            combineGroups(groups);
                                               
            for(SliceGroup g : groups)
            {
                negativeSpace.add(new NegativeSpace(g));
            }            
        }
        

        
        /*
         * Attempts to combine any (and all) of the given SliceGroups
         * together.
         */ 
        private static void combineGroups(List<SliceGroup> groups)
        {
           /* System.out.println("combineGroups: looking at " +
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
        private static List<SliceGroup> combineSlices(List<Slice> slices)
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
                System.out.println("end of slices; end of groups");
                return null;
            }      
            List<Slice> currentSlices = new LinkedList<Slice>();
            currentSlices.add(allSlices.remove(0));        
            SliceGroup g = new SliceGroup(currentSlices);
            
            int added = 0;
            do 
            {    
                ListIterator<Slice> lit = allSlices.listIterator();
                added = 0;
                while(lit.hasNext())
                {
                    Slice s = lit.next();
                    
                    if(g.addSlice(s))
                    {
                        added++;
                        //System.out.println("|--adding Slice : ");
                        //s.printSliceInfo();
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
         * Returns a list of tiles not in the collision
         * layer or ArrowBouncer list.
         */ 
        private static List<Tile> getSpaceTiles(Level l)
        {
            MapLayer colls = l.getCollisionLayer();
            //MapLayer bgrnd = l.getBackgroundLayer();
            
            List<Tile> spaceTiles = new ArrayList<Tile>();
            	
            //for(Tile b : bgrnd.tiles)
            for(int x = 0; x < l.getWidth(); ++x)
            {
            	for(int y = 0; y < l.getHeight(); ++y)
            	{
	                if(!colls.containsTile(x, y))//b.xCoord, b.yCoord))
	                {
	                	/*System.out.println("Found space tile (" + x + ", "
	                				+ y + ")");*/
	                    spaceTiles.add(new Tile(l, x, y, 0));
	                }
            	}
            }
            return spaceTiles;
        }
        
        /*
         * Returns a line from the top surface of 2 (collision) Tiles
         */ 
        private static Rectangle makeWalkShape(Tile start, Tile end)
        {
            /*System.out.println("\tnew walk line from " 
                    + start.getCoords() + " | to | " + end.getCoords());
           */
            Rectangle rect = new Rectangle(
            		start.xPos, 
            		start.yPos - 3, 
            		(end.endX - start.xPos),
            		5);
            return rect;
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
        private static List<Tile> getLedgeTiles(Level l)
        {
        	MapLayer colls = l.getCollisionLayer();
            
        	System.out.println("getLedgeTiles : collision layer has " 
        			+ colls.tiles.size() + " tiles");
        	
            List<Tile> ledgeTiles = new ArrayList<Tile>();
            
            for(Tile t : colls.tiles)
            { 
                // naturally if at vertical position 0, there can't be a
                // 'higher' tile level (lower number)
                if(t.yCoord > 0)
                {        
                    if(isLedgeTile(t, colls))
                    {
                        ledgeTiles.add(t);
                    }
                }
            }
            System.out.println("Found total of " 
            		+ ledgeTiles.size() + " ledge tiles");
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
                //System.out.println("Tile " + test.xCoord + ", "
                  //           + test.yCoord + " is ledge tile");
                return true;
            }
            return false;
        } 
    }

	@Override
	public String getName() 
	{
		return "NavMesh";
	}
    
}
