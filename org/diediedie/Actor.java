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
import org.diediedie.actors.LevelObject;
import org.diediedie.actors.Direction;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Animation;

public interface Actor extends MovableObject
{            
    public static final int ANIM_DURATION = 100;
    int getHealth();
    
    Animation getCurrentAnim();
    
    boolean canJump();
    
    void setJump(boolean b);
    void applySpeed(Direction d);
    void jump();
    void die();
    void setFacing(Direction d);
    void resetAccelX();
    
    Direction getFacing();
    
    float getJumpSpeed();
    float getMoveSpeed();    
    float getYSpeed();
    float getXSpeed();
    
}  
