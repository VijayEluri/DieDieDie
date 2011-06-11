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
package diediedie.level.actions;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import diediedie.level.Tile;
import diediedie.level.Line;
import diediedie.level.Point;
import diediedie.level.actors.Actor;
import diediedie.level.actors.Enemy;
import diediedie.level.LevelObject;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;

/**
 * Looks at the immediate area in the current Direction, relaying
 * information back to the Actor's FSM
 */
public class Look implements Action
{
    private boolean started, finished, viewCreated;
    private float xViewStart, yViewStart;
    //private View view = null;    
    private Line sightLine = null; 
        
    /**
     * Look around the Level.
     */
    public Look()
    {
        reset();
    }
    
    /*
     * Resets all the vars
     */ 
    
    public void reset()
    {
        started = false;
        finished = false;
        viewCreated = false;
        sightLine = null;
    }
    
    
    public void setHost(LevelObject lo)
    {
        
    }
    
    /*
     * Rem: perform() is the entry point for States. 
     */ 
    
    public void perform()
    {
        if(!started && !finished)
        {
            started = true;
            update();
            viewCreated = true;
            CoreSystem.print("Look.perform()");
        }
        else if(started && !finished)
        {
            update();
        }
    }
    
    
    public void draw(CoreGraphics g)
    {
        CoreSystem.print(this + "not draw()ing");
        /*if(viewCreated)
        {
            view.draw(g);
        }*/
    }
         
    
    public void update()
    {
        //view = new View(e);
        CoreSystem.print(this + "NOT callign analyseView()" +
                                " as Shape not implemented");
        //analyseView(e, view.getShape());   
    }
    
    /*
     * Analyses an Actor's view Shape for various LevelObjects. 
     */ 
    /*private void analyseView(Actor e, Shape sh)
    {
        //List<Actor> actors = e.getLevel().getActors();
        
        checkVisibleActors(e, sh, e.getLevel().getActors());
        checkVisiblePlayerObjects(e, sh, e.getLevel().getPlayer());
    }*/
    
    /*
     * Checks the visibility of Player pl's Objects on this Level.
     * 
     * So far: Projectiles.
     */ 
/*
    private void checkVisiblePlayerObjects(Actor e, Shape sh, Player pl)
    {
        List<Point> points;
        for(Projectile pr : pl.getFiredProjectiles())
        {
            points = new ArrayList<Point>();
            points.add(new Point(pr.getX(), pr.getY()));
            points.add(new Point(pr.getEndX(), pr.getEndY()));
         
            if(hasPointInsideShape(points, sh) 
                && (!isViewBlocked(e, points.get(0)) 
                    ||  !isViewBlocked(e, points.get(1))))
            {
                e.addVisibleObject(pr);
                //CoreSystem.print(e + " can see " + pr);
            }
        }
    }
*/
    
    /*
     * Checks for the visibility of the other Actors on the Level to 
     * this Actor. 
     */
/*
    private void checkVisibleActors(Actor e, Shape sh, List<Actor> actors)
    {
        for(Actor a : actors)
        {
            if(a.hashCode() != e.hashCode())
            {
                if(actorIsVisible(e, sh, a))
                {
                    if(a.getClass().equals(e.getLevel().getPlayer().getClass()))
                    {
                        e.setCanSeenPlayer(true);
                    }
                }
            }
        }
        e.setCanSeenPlayer(false);        
    }
*/
    
    /*
     * Returns true if any of the four points of an Actor's containing
     * Rect are inside the view Shape sh.
     */ 
/*
    private boolean actorInView(Shape sh, Actor a)
    {
        final Shape r = AnimCreator.getCurrentFrameRect(a);
        
        List<Point> points = new ArrayList<Point>();
        
        points.add(new Point(r.getX(), r.getY()));
        points.add(new Point(r.getX() + r.getWidth(), r.getY()));
        points.add(new Point(r.getX() + r.getWidth(), r.getY() + r.getHeight()));
        points.add(new Point(r.getX(), r.getY() + r.getHeight()));
        
        return hasPointInsideShape(points, sh);
    }
    
    private boolean hasPointInsideShape(List<Point> points, Shape sh)
    {
        for(final Point p : points)
        {
            if(sh.contains(p.x, p.y))
            {
           
                return true;
            }
        }        
        
        return false;
    }
    
    /*
     * Returns true if Actor a is visible to Actor e.
     */ 
/*
    public final boolean actorIsVisible(Actor e, Shape sh, Actor a)
    {
        final float[] pos = AnimCreator.getCurrentFrameRect(a).getCenter();
        final Point p = new Point(pos[0], pos[1]);
        
        if(actorInView(sh, a) && !isViewBlocked(e, p))
        {
            //CoreSystem.print(a + " | is visible to | " + e);
            return true;
        }  
        return false;
    }
*/

    
    /*
     * Returns true if a line from Actor e's eye position to the given
     * position collides with any obstacles on the Level.
     */ 
/*
    private boolean isViewBlocked(Enemy e, Point pos)
    {
        // create line from the view start to the x/y coord pos. 
        // if unobstructed, return false        
        sightLine = new Line(
            (int)e.getEyePosX(), (int)e.getEyePosY(), pos.x, pos.y);
       
        if(e.getLevel().collides(sightLine))
        {
            sightLine = null;
            return true;
        }
        
        return false;
    }
         
*/
   
    
    /**
     * View - constructs the geometry describing the field-of-view from
     * the Actor's eye position to the Level that s/he exists in. 
     * 
     * This View is used to calculate whether or not the Actor can 
     * see an object, platform or Actor on the Level.  
     

    class View 
    {
        //private final int EYE_ANG_UP = 50, EYE_ANG_DOWN = 140; 
        // now using stored radians instead of degrees
        private final float EYE_ANG_UP = 0.872664625f, 
                            EYE_ANG_DOWN = 2.44346095f; 
        
        private Line topLine, botLine;
        //private Path fovShape;

        private float viewSize;

        
        private Color color = Color.white;
        
        // view geometry components
        private float xViewStart, yViewStart, topEndX, topEndY, 
                      endX, botEndY;
          
       **
        * Constructs a view from a Actor's facing direction and position 
        *
        
        /*
        View(Actor e)
        {
            viewSize = e.getViewSize();
            constructFOV(e);
            //CoreSystem.print("constructed view");
        } 
        
        public Shape getShape()
        {
            return (Shape)fovShape;
        }
        
        public float fastSin(float x, float radians)
        {
            return x + viewSize * (float)FastTrig.sin(radians);
        }

        public float fastCos(float y, float radians)
        {
            return y - viewSize * (float)FastTrig.cos(radians);
        }
    
        
         * Create FOV Lines
         
/*
        private void constructFOV(Actor e)
        {            
            xViewStart = e.getEyePosX();
            yViewStart = e.getEyePosY();
            
            
            if(e.getFacing().equals(Direction.LEFT))
            {
                //CoreSystem.print(e + " left view");
                endX = fastSin(xViewStart, -EYE_ANG_UP);
            }
            else if(e.getFacing().equals(Direction.RIGHT))
            {
               //CoreSystem.print(e + " right view");
                endX = fastSin(xViewStart, EYE_ANG_UP);    
            }
            
            topEndY = fastCos(yViewStart, EYE_ANG_UP);
            botEndY = fastCos(yViewStart, EYE_ANG_DOWN);   
                     
            fovShape = new Path(xViewStart, yViewStart);
            fovShape.lineTo(endX, topEndY);
            fovShape.lineTo(endX, botEndY);
            fovShape.close();
        }       

               
        
         * For testing purposes, draw the field of vision lines.
         

        public void draw(CoreGraphics g)
        {
            g.setColor(color);
            
            //g.draw(fovShape);
            if(sightLine != null)
            {
                g.draw(sightLine);
            }
        } 
    }
*/
     
    public boolean hasStarted() 
    { 
        return started; 
    }
    
    
    public boolean hasFinished() 
    { 
        return finished; 
    }
    
}
    
