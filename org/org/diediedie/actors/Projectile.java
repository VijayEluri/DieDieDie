package org.diediedie.actors;
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
    float getMovementAngle();
    float getFacingAngle();
    Shape getShape();
    float getGravityLine();    
    float getGravity();
    float getMaxGravity();
    float getOldStartX();
    float getOldStartY();
    float getDamage();
    void increaseGravityEffect();
    boolean isGoingDown();
    boolean collidedWithEnemy();
    float getEndX();
    float getEndY();    
    float getAirRes();
    void calculateEndPos();
    void adjustFacingAngle();
}
