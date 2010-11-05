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

package org.diediedie.actors.actions;

import org.diediedie.actors.actions.Action;
import org.diediedie.actors.Enemy;


/**
 * Enemy State Action. Makes the Enemy walk along in the current
 */ 
public class Walk implements Action
{   
    private boolean started, finished;
    private Enemy host = null;
    
    
    public Walk()
    {
        started = false;
        finished = false;
    }

    @Override
    public void perform(Enemy host)
    {
        System.out.println("performing Walk for " 
            + new Throwable().fillInStackTrace()
                            .getStackTrace()[1].getClassName());
        started = true;
        
        host.setMoveSpeed(host.getWalkSpeed());
        host.setMoving(true);
    }    
    
    @Override
    public boolean hasStarted()    
    {
        return started;
    }
    @Override
    public boolean hasFinished() 
    {
        return finished;
    }


    public String toString()
    {
        return "Walk";
    }

}
