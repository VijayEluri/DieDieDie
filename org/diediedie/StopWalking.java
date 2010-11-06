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

class StopWalking implements Action
{  
    private boolean started, finished;
    private Enemy host = null;
    
    public StopWalking()
    {
    
    }
    
    @Override
    public void perform(Enemy e)
    {
        
    }
    
    @Override
    public void update(Enemy host)
    {
        
    }
    
    @Override
    public boolean hasFinished() 
    {
        return started;
    }
    
    @Override
    public boolean hasStarted() 
    {
        return started;
    }
    
    @Override
    public String toString()
    {
        return "StopWalking";
    }
}