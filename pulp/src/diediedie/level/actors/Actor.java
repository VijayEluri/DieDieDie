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
 
package diediedie.level.actors;
import diediedie.level.LevelObject;
import diediedie.level.Direction;
import diediedie.level.MovableObject;
import pulpcore.image.CoreGraphics;
import pulpcore.image.AnimatedImage;
import pulpcore.math.Rect;

import java.util.Set;

/*
 * Actor - interface for Players and Enemies.
 */
public interface Actor extends MovableObject
{            
    public static int ANIM_DURATION = 100;
    void applySpeed(Direction d);
    boolean canJump();
    void die();
    AnimatedImage getCurrentAnim();
    Direction getFacing();
    int getHealth();    
    float getJumpSpeed();
    float getMoveSpeed();    
    float getXSpeed();
    float getYSpeed();
    Rect getZone();    
    void setJump(boolean b);
    void jump();
    void setFacing(Direction d);
    void resetAccelX();
}  
