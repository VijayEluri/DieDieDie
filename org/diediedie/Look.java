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
import org.diediedie.actors.actions.Action;
import org.diediedie.actors.AnimCreator;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Player;
import org.diediedie.actors.Actor;
import org.diediedie.Tile;
import org.diediedie.actors.Direction;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import java.util.List;
import java.util.ArrayList;
import org.newdawn.slick.util.FastTrig;
import java.lang.Math;
import org.newdawn.slick.geom.GeomUtil;

/**
 * Look at the immediate area in the current Direction. 
 */
public class Look implements Action
{
    private boolean started, finished, viewCreated;
    private float xViewStart, yViewStart;
    private View view = null;    
    private Line sightLine = null; 
        
    /**
     * Look!
     */
    public Look()
    {
        reset();
    }
    
    /*
     * Resets all the vars
     */ 
    private void reset()
    {
        started = false;
        finished = false;
        viewCreated = false;
        sightLine = null;
    }
    
    /*
     * Rem: perform() is the entry point for States. 
     */ 
    @Override
    public void perform(Enemy e)
    {
        if(!started && !finished)
        {
            started = true;
            update(e);
            viewCreated = true;
            System.out.println("Look.perform()");
        }
        else if(started && !finished)
        {
            update(e);
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
        
    }
    
    
    /*
     * Checks for the visibility of the other Actors on the Level to 
     * this Enemy. 
     */
    private void checkVisibleActors(Enemy e, Shape sh, List<Actor> actors)
    {
        for(Actor a : actors)
        {
            if(actorIsVisible(e, sh, a))
            {
                if(a.getClass().equals(e.getLevel().getPlayer().getClass()))
                {
                    e.setCanSeenPlayer(true);
                    System.out.println("Player is visible to  " + e + "!");
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
      
        final float[][] points = 
        {
            { r.getX(), r.getY() },
            { r.getX() + r.getWidth(), r.getY() },
            { r.getX() + r.getWidth(), r.getY() + r.getHeight() },
            { r.getX(), r.getY() + r.getHeight()}
        };
            
        for(final float[] p : points)
        {
            if(sh.contains(p[0], p[1]))
            {
                return true;
            }
        }        
        return false;
    }
    
    /*
     * Returns true if Actor a is visible to Enemy e.
     */ 
    public boolean actorIsVisible(Enemy e, Shape sh, Actor a)
    {
        if(actorInView(sh, a) && !isViewBlocked(e, sh, a))
        {
            return true;
        }  
        return false;
    }
    
    private boolean isViewBlocked(Enemy e, Shape sh, Actor a)
    {
        // create line from the view start to the Player. 
        // if unobstructed, return false
         
        final float[] pos = AnimCreator.getCurrentFrameRect(a).getCenter();
        sightLine = new Line(e.getEyePosX(), e.getEyePosY(), pos[0], pos[1]);
       
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
        //private final int EYE_ANG_UP = 45, EYE_ANG_DOWN = 135; 
        // now using stored radians instead of degrees
        private final float EYE_ANG_UP = 0.7853982f, 
                            EYE_ANG_DOWN = 2.3561945f;
        
        private Line topLine, botLine;
        private Path fovShape;
        //private final int LINE_LEN = 350;
        private float viewSize;
       // private Transform trans;
        
        // view geometry components
        private float xViewStart, yViewStart, topEndX, topEndY, 
                      endX, botEndY;
          
       /**
        * Constructs a view from a Enemy's facing direction and position 
        */
        View(Enemy e)
        {
            constructFOV(e);
            //System.out.println("constructed view");
        } 
        
        public Shape getShape()
        {
            return (Shape)fovShape;
        }
        
        private float fastSin(float x, float radians)
        {
            return x + viewSize * (float)FastTrig.sin(radians);
        }

        private float fastCos(float y, float radians)
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
            viewSize = e.getViewSize();
            
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
            g.setColor(Color.white);
            g.draw(fovShape);
            if(sightLine != null)
            {
                g.draw(sightLine);
            }
        } 
    }
}
