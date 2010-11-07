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
import org.diediedie.actors.Enemy;
import org.diediedie.Tile;
import org.diediedie.actors.Direction;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import java.util.List;
import java.util.ArrayList;
import org.newdawn.slick.util.FastTrig;
import java.lang.Math;

/**
 * Look at the immediate area in the current Direction. 
 */
public class Look implements Action
{
    private boolean started, finished;
    private float xViewStart, yViewStart;
    private View view = null;
    
    
    // a list of the collision tiles (walls, floors)
    //private List<Tile> surfacesSeen = null;
    
    /**
     * Look!
     */
    public Look()
    {
        reset();
    }
    
    private void reset()
    {
        started = false;
        finished = false;
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
            System.out.println("Look.perform() ");
            
            
            assert(view != null);
        }
        else if(started && !finished)
        {
            update(e);
        }
    }
    
    @Override
    public void draw(Graphics g)
    {
        if(view != null)
        {
            view.draw(g);
        }
    }
         
    @Override
    public void update(Enemy e)
    {
        //System.out.println("\t updating Look action for " + e);
        view = new View(e);
        //view.draw();
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
        private Line fovTop = null, fovBot = null;
        private final int LINE_LEN = 350;
        
        // view geometry components
        private float xViewStart, yViewStart, topEndX, topEndY, //botEndX, 
                      endX, botEndY;
          
       /**
        * Constructs a view from a Enemy's facing direction and position 
        */
        View(Enemy e)
        {
            constructFOV(e);
        } 
        
        private float fastSin(float x, float radians)
        {
            return x + LINE_LEN * (float)FastTrig.sin(radians);
        }

        private float fastCos(float y, float radians)
        {
            return y - LINE_LEN * (float)FastTrig.cos(radians);
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
                endX = fastSin(xViewStart, /*-LINE_LEN, */ -EYE_ANG_UP);
                
            }
            else if(e.getFacing().equals(Direction.RIGHT))
            {
               //System.out.println(e + " right view");
                endX = fastSin(xViewStart, /*-LINE_LEN, */ EYE_ANG_UP);    
            }
                        
            topEndY = fastCos(yViewStart, EYE_ANG_UP);
            botEndY = fastCos(yViewStart, EYE_ANG_DOWN);
          /*  System.out.println("fov top: " + xViewStart + ", " + 
                        yViewStart + " to " + endX +  
            */
            //fovTop = new Line(xViewStart, yViewStart, topEndX, topEndY);
        }
        
        
        
        
        /*
         * For testing purposes, draw the field of vision lines.
         */ 
        public void draw(Graphics g)
        {
           /* g.drawGradientLine(xViewStart, yViewStart, color, topEndX, topEndY,
                                color);            */
            g.drawLine(xViewStart, yViewStart, endX, topEndY);            
            g.drawLine(xViewStart, yViewStart, endX, botEndY);            
        } 
    }
}
