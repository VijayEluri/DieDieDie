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

import diediedie.level.actors.Actor;
import diediedie.level.LevelObject;
import diediedie.level.Direction;
import java.util.Set;
import pulpcore.image.CoreGraphics;

/**
 * Enemy. Actors with a few methods related to 'AI'.
 */ 
public interface Enemy extends Actor
{
    float getViewSize();
    void setMoveSpeed(float f);
    void setMoving(boolean b);
    float getEyePosX();
    float getEyePosY();
    boolean canSeePlayer();
    boolean hasSeenPlayer();
    boolean hasSeenPlayerEvidence();
    void setSeenPlayerEvidence(boolean b);
    void setHasSeenPlayer(boolean b);
    void setCanSeenPlayer(boolean b);
    
    Set<LevelObject> getVisibleObjects();
    void addVisibleObject(LevelObject lo);
    
    boolean isMoving();  
    //CoreGraphics getGraphics();
    float getWalkSpeed();
    float getRunSpeed();
}
