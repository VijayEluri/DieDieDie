package org.diediedie.actors;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public interface Actor
{            
    static final int ANIM_DURATION = 100;
        
    Animation getCurrentAnim();
    void draw(Graphics g);
    float getX();
    float getY();
    void update();
    void applyGravity();
    int getHealth();
        
    void die();
}  
