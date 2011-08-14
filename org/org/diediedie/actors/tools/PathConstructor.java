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
package org.diediedie.actors.tools;
//import org.diediedie.actors.Enemy;
import org.diediedie.Point;
//import org.diediedie.actors.actions.Action;
//import org.newdawn.slick.Graphics;
//import org.newdawn.slick.util.pathfinding.*;

/**
 * Attempts to create a path across the 2D terrain between two Points.
 */
public class PathConstructor
{
	/*
	 * NOTES
	 * =====
	 * The course of the path will depend on the restrictions of its user. i.e.
	 * a flying drone AI will be able to navigate more areas than a human AI, but
	 * the human will also be able to open doors, communicate...
	 * 
	 * How to include this in the PathConstructor?
	 * 
	 */
	private Point start, end;
	private boolean isSet;
	
	
	public PathConstructor()
	{
		// Empty Constructor
		this.isSet = false;
	}
	
	public PathConstructor(Point a, Point b)
	{
		this.setVars(a, b);
	}
	
	private void setVars(Point a, Point b)
	{
		this.start = a;
		this.end = b;
		this.isSet = true;
	}
	
	
	
	
}
