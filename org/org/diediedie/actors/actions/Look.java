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
package org.diediedie.actors.actions;
import org.diediedie.actors.*;
import org.diediedie.actors.actions.Action;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import java.util.List;
import java.util.ArrayList;
import org.newdawn.slick.util.FastTrig;
import java.util.Calendar;

/**
 * Looks at the immediate area in the current Direction, relaying
 * information back to the Enemy's FSM
 *
 * Look instances contain a 'lastLookTime' long variable that contains
 * the last time the current instance was 
 */
public class Look implements Action
{
    private boolean started, finished, viewCreated;
    //private float xViewStart, yViewStart;
    private View view = null;    
    private Line sightLine = null; 
    private Calendar cal;
    private long lastLookTime;
    
    /**
     * Resets all of the fields in this Look instance.
     */
    public Look()
    {
        reset();
    }
    
    /*
     * Resets all the vars in this Look instance.
     */ 
    @Override
    public void reset()
    {
        started = false;
        finished = false;
        viewCreated = false;
        sightLine = null;
        lastLookTime = 0;
        cal = Calendar.getInstance();
    }
    
    /**
     * Returns the last time this Look instance was called (zero if
     * not yet called).
     */
    public long getLastLookTime()
    {
        return lastLookTime;
    }
    
    /*
     * Perform this Look instance on the specified Enemy.
     */ 
    @Override
    public void perform(Enemy e)
    {
        if(!started)
        {
            started = true;
            update(e);
            viewCreated = true;
            System.out.println("starting Look.perform()");
        }
        else if(!finished)
        {
            update(e);
        }
        else
        {
            // finished, i guess?
        }
    }
    
    @Override
    public void draw(Graphics g)
    {
        if(viewCreated)
        {
            view.draw(g);
        }
    }
         
    /*
     * Updates the Look instance. Creates a View object from the Enemy's
     * eye position and analyses the content for the Player and other
     * suspicious entities.
     */
    @Override
    public void update(Enemy e)
    {
        view = new View(e);
        analyseView(e, view.getShape());   
    }
    
    /*
     * Analyses an Enemy's view Shape for various LevelObjects. 
     */ 
    private void analyseView(Enemy e, Shape sh)
    {
        //List<Actor> actors = e.getLevel().getActors();
        
        checkVisibleActors(e, sh, e.getLevel().getActors());
        checkVisiblePlayerObjects(e, sh, e.getLevel().getPlayer());
    }
    
    /*
     * Checks the visibility of Player pl's Objects on this Level.
     * 
     * So far: Projectiles.
     */ 
    private void checkVisiblePlayerObjects(Enemy e, Shape sh, Player pl)
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
                e.setSeenPlayerEvidence(true);
                //System.out.println(e + " can see " + pr);
            }
        }
    }
    
    /*
     * Checks for the visibility of the other Actors on the Level to 
     * this Enemy. 
     */
    private void checkVisibleActors(Enemy e, Shape sh, List<Actor> actors)
    {
        for(Actor a : actors)
        {
            if(a.hashCode() != e.hashCode())
            {
                if(actorIsVisible(e, sh, a))
                {
                    if(a.getClass().equals(
                       e.getLevel().getPlayer().getClass()))
                    {
                        e.setCanSeenPlayer(true);
                    }
                }
            }
        }
        e.setCanSeenPlayer(false);        
    }
    
    /*
     * Returns true if any of the four points of an Actor's containing
     * Rectangle are inside the view Shape sh.
     */ 
    private boolean actorInView(Shape sh, Actor a)
    {
        final Shape r = AnimCreator.getCurrentFrameRect(a);
        
        List<Point> points = new ArrayList<Point>();
        
        points.add(new Point(
            r.getX(), r.getY()));

        points.add(new Point(
            r.getX() + r.getWidth(), r.getY()));

        points.add(new Point(
            r.getX() + r.getWidth(), r.getY() + r.getHeight()));

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
     * Returns true if Actor a is visible to Enemy e.
     */ 
    public final boolean actorIsVisible(Enemy e, Shape sh, Actor a)
    {
        final float[] pos = AnimCreator.getCurrentFrameRect(a).getCenter();
        final Point p = new Point(pos[0], pos[1]);
        
        if(actorInView(sh, a) && !isViewBlocked(e, p))
        {
            //System.out.println(a + " | is visible to | " + e);
            e.setTimeLastSawPlayer(cal.getTime().getTime());
            return true;
        }  
        return false;
    }
    
    
    
    /*
     * Returns true if a line from Enemy e's eye position to the given
     * position collides with any obstacles on the Level.
     */ 
    private boolean isViewBlocked(Enemy e, Point pos)
    {
        // create line from the view start to the x/y coord pos. 
        // if unobstructed, return false        
        sightLine = new Line(e.getEyePosX(), e.getEyePosY(), pos.x, pos.y);
       
        if(e.getLevel().collides(sightLine))
        {
            sightLine = null;
            return true;
        }
        
        return false;
    }
         
    @Override
    public boolean hasStarted() { return started; }
    
    @Override
    public boolean hasFinished() { return finished; }
    
    /*
     * View - constructs the geometry describing the field-of-view from
     * the Enemy's eye position to the Level that s/he exists in. 
     * 
     * This View is used to calculate whether or not the Enemy can 
     * see an object, platform or Actor on the Level.  
     */ 
    class View 
    {
        //private final int EYE_ANG_UP = 50, EYE_ANG_DOWN = 140; 
        // now using stored radians instead of degrees
        private final float EYE_ANG_UP = 0.872664625f, 
                            EYE_ANG_DOWN = 2.44346095f; 
        
        private Line topLine, botLine;
        private Path fovShape;

        private float viewSize;

        
        private Color color = Color.white;
        
        // view geometry components
        private float xViewStart, yViewStart, /*topEndX,*/
        			  topEndY, endX, botEndY;
          
       /**
        * Constructs a view from a Enemy's facing direction and position 
        */
        View(Enemy e)
        {
            viewSize = e.getViewSize();
            constructFOV(e);
            //System.out.println("constructed view");
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
        
        /*
         * Create FOV Lines
         */ 
        private void constructFOV(Enemy e)
        {            
            xViewStart = e.getEyePosX();
            yViewStart = e.getEyePosY();
            
            
            if(e.getFacing().equals(Direction.LEFT))
            {
                //System.out.println(e + " left view");
                endX = fastSin(xViewStart, -EYE_ANG_UP);
            }
            else if(e.getFacing().equals(Direction.RIGHT))
            {
               //System.out.println(e + " right view");
                endX = fastSin(xViewStart, EYE_ANG_UP);    
            }
            
            topEndY = fastCos(yViewStart, EYE_ANG_UP);
            botEndY = fastCos(yViewStart, EYE_ANG_DOWN);   
                     
            fovShape = new Path(xViewStart, yViewStart);
            fovShape.lineTo(endX, topEndY);
            fovShape.lineTo(endX, botEndY);
            fovShape.close();
        }       
               
        /*
         * For testing purposes, draw the field of vision lines.
         */ 
        public void draw(Graphics g)
        {
            g.setColor(color);
            
            g.draw(fovShape);
            if(sightLine != null)
            {
                g.draw(sightLine);
            }
        } 
    }
}
