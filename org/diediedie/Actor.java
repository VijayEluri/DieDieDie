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
 
package org.diediedie.actors;
import org.diediedie.actors.Direction;
import org.diediedie.Level;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
 
public interface Actor
{            
    public static final int ANIM_DURATION = 100;
    
    Animation getCurrentAnim();    
    void draw(Graphics g);
    void update();
    boolean canJump();
    void setJump(boolean b);
    Level getLevel();
       
    void setLevel(Level l);
    float getX();
    float getY();    
    void setX(float x);
    void setY(float y);
    void jump();
    void resetAccelX();
    void applySpeed(Direction d);
    void applyGravity();

    Direction getFacing();
    

    void setYSpeed(float y);
    void setXSpeed(float x);
    
    float getYSpeed();
    float getXSpeed();
    float getMaxFallSpeed();
    float getJumpSpeed();
    float getMoveSpeed();
    
    int getHealth();
    void die();
}  
