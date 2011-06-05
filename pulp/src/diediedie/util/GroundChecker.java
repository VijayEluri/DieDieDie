/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */
 
package diediedie.util;

import diediedie.level.actors.Actor;
import diediedie.level.Level;
//import org.newdawn.slick.util.pathfinding.*;

/**
 * Relays information to an Actor regarding the geometry of the 
 * ground collision tiles immediately in front of them in their currently
 * faced direction.
 */ 
public class GroundChecker
{
    public static boolean canMove(Actor a)
    {
        return false;
    }
    
    /*public static boolean canMoveTo(Actor a, float x, float y)
    {
        return false;
    }*/
}
