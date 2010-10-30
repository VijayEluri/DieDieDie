package org.diediedie;

import java.io.*;
import java.util.*;
import java.lang.Math.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Enemy implements Actor
{
    private float xPos, yPos;
    
    public Enemy()
    {
        
    }

    public void update()
    {
        
    }
    /**
     * Returns the x position
     */ 
    public float getX() { return xPos; }
    /**
     * Returns the y position
     */ 
    public float getY() { return yPos; } 

}
