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

import diediedie.level.Point;
import diediedie.level.Point;
import pulpcore.math.Rect;

public class Line 
{
    public int startX, 
               startY, 
               endX, 
               endY;
    
    public Line(int startx, int startY, int endX, int endY)
    {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    
    public boolean intersects(Rect r)
    {
        for(Point p : points())
        {
            if(r.contains(p.x, p.y))
            {
                return true;
            }
        }
        return false;
    }
    
    public Point[] points()
    {
        int n = endX - startX;
        Point[] points = new Point[n];
        System.out.println("points " + (endX - startX));
        
        for(int x = startX; x <= endX; ++x)
        {
            int i = 0;
            for(int y = startY; y <= endY; ++x)
            {
                points[i] = new Point(x, y);
                i++;
            }            
        }
        return points;
    }
}
