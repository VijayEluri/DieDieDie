package org.diediedie.actors;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public interface Actor
{            
    Rectangle getCurrentFrameRect();
    float getX();
    float getY();
    void update();
}  
