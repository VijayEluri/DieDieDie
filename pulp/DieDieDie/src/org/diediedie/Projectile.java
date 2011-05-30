package org.diediedie.actors;
import org.diediedie.actors.Direction;
import org.diediedie.Level;
import pulpcore.image.CoreGraphics;
import pulpcore.animation.Animation;
import org.newdawn.slick.geom.*;

/**
 * Public Interface for all moving weaponary. 
 **/
public interface Projectile extends MovableObject
{
    void updatePosition();
    void updateAiming(float mouseX, float mouseY);
    
    void stop();
    void release(float power);
    
    boolean isFlying();
    float getAngle();
    
    Shape getShape();
    
    float getGravityLine();    
    float getGravity();
    float getMaxGravity();

    void increaseGravityEffect();
    
    boolean isGoingDown();
    
    float getEndX();
    float getEndY();    
    float getAirRes();
    
    void calculateEndPos();
    void adjustFacingAngle();
}
