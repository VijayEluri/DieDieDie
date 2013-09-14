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

package org.diediedie.level.actors.tools;

public enum Direction
{
    LEFT, RIGHT, UP, DOWN;
    
    
    /*
	 * Converts a string in a Direction object, or null
	 * if invalid.
	 */
	public static Direction convertToDirection(String s)
    {
		System.out.println("convertToDirection : " + s);
    	for(Direction  d : Direction.values())
    	{
    		if(s.equalsIgnoreCase(d.toString()))
    		{
    			
    			return d;
    		}
    	}
    	return null;
    
    }
}
