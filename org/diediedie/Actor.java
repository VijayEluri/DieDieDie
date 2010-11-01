package org.diediedie.actors;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public interface Actor
{            
    public static int ANIM_DURATION = 150; 
    Rectangle getCurrentFrameRect();
    float getX();
    float getY();
    void update();
}  
