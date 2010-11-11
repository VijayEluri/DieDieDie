package org.diediedie.actors;
import org.diediedie.actors.Direction;
import org.diediedie.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

// arrow / bullet etc
public interface Projectile extends MovableObject
{
    void updatePosition();
    void updateAiming(float mouseX, float mouseY);
    
    void stop();
    void release(float power);
    
    boolean isFlying();
    float getAngle();

    float getGravityLine();    
    float getGravity();
    float getMaxGravity();
    float getGravityIncr();
    void increaseGravityEffect();
    
    boolean isGoingDown();
    
    float getEndX();
    float getEndY();    
    float getAirRes();
    
    void calculateEndPos();
    void adjustFacingAngle();
}
