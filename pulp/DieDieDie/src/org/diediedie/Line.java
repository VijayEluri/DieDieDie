package org.diediedie;
import org.diediedie.actors.Point;
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
