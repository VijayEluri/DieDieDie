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
package diediedie.level;

import pulpcore.image.CoreGraphics;
import pulpcore.math.Rect;
import diediedie.level.Level;
import diediedie.level.Drawable;

/**
 * Super-interface for any interactive thing that exists on a Level. 
 */ 
public interface LevelObject extends Drawable
{
    void draw(CoreGraphics g);
    void update();
}