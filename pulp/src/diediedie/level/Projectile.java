/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */
package diediedie.level;
import diediedie.level.Direction;
import diediedie.level.Level;
import diediedie.level.MovableObject;
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
    
    //Shape getShape();
    
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
