package org.diediedie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.diediedie.NavMesh.Slice;
import org.diediedie.NavMesh.SliceGroup;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class NegativeSpace implements Drawable
{
	private List<Tile> tiles;
	private Shape shape;
	//Map<Direction, NegativeSpace> neighbours;
	Set<NegativeSpace> neighbours;
	List<Shape> neighbourLinkShapes; 
	
	private Level level = null;
	private Point centre = null;
	private Rectangle centerRect = null;
	
	
	public NegativeSpace(SliceGroup sg)
	{
		this.shape = sg.getShape();
		createCentreSquare();
		unpackTiles(sg);
		neighbours = new HashSet<NegativeSpace>();//new HashMap<Direction, NegativeSpace>();
	}
		
	public Set<NegativeSpace> getNeighbours()
	{
		return neighbours;
	}
	
	private void unpackTiles(SliceGroup sg)
	{
		tiles = new ArrayList<Tile>();
		
		for(Slice s : sg.slices)
		{
			for(Tile t : s.tiles)
			{
				tiles.add(t);
			}
		}
	}
	
	
	public void addNeighbours(Collection<NegativeSpace> others)
    {
    	
    	System.out.println("getNeighbours : \n" + this);
    	
    	//Map<Direction, NegativeSpace> neighbours = null;
    	
    	for(NegativeSpace ns : others)
    	{    		
    		if(ns != this)
    		{
    			if(!this.getNeighbours().contains(ns))
    			{
    				Shape startShape = this.getShape();
    				Shape nsShape = ns.getShape();
    				
    				// not already in the list
	    			if(nsShape.intersects(startShape))
	    			{
	    				System.out.println("\t--> intersects " + ns);
	    				this.getNeighbours().add(ns);
	    			}
    			}
    		}
    	}
    	createLinesToLinkedSpaces();
    }
	
	private void createLinesToLinkedSpaces() 
	{
		neighbourLinkShapes = new ArrayList<Shape>();
		
		for(NegativeSpace ns : neighbours)
		{
			Shape s = new Path(centre.x, centre.y);
			 ((Path) s).lineTo(ns.getCentre().x, ns.getCentre().y);
			
		}
	}

	public List<Tile> getTiles()
	{
		return tiles;
	}
	
	public Shape getShape()
	{
		return shape;
	}
	
	private void createCentreSquare()
	{
		findCentre();
		this.centerRect = new Rectangle(
			centre.x - 5, centre.y - 5, 
			10, 10);
	}
	
	public String toString()
	{
		Tile first = tiles.get(0);
		Tile last = tiles.get(tiles.size()-1);
		return new String(
			"\tNegativeSpace: " + tiles.size() 
			+ " Tiles.\n\tCoords : " + " ("   
			+ first.xCoord  + ", " 
			+ first.yCoord
			+ ") - ("
            + last.xCoord
			+ ", "
			+ last.yCoord
			+ ")\n\tShape : ("
			+ shape.getMinX() 
			+ ", " 
			+ shape.getMinY()
			+ ") - ("
			+ shape.getMaxX()
			+ ", "
			+ shape.getMaxY()
			+ ")\n");
	}

	@Override
	public void draw(Graphics g)
	{
		final Color c = g.getColor();
		g.draw(shape);
		g.setColor(Color.blue);
		g.fill(centerRect);
		g.setColor(c);
	}
	
	public Point getCentre()
	{
		return centre; 
	}
	
	private void findCentre()
	{
		centre = new Point(shape.getCenterX(), shape.getCenterY());
	}
	

	@Override
	public void setLevel(Level l)
	{
		this.level = l;
	}
}
