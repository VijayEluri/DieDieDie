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
import org.diediedie.Level;
import org.diediedie.actors.State;
 
public class Patrol implements State
{
    private Actor host = null;
    private Level level = null;
    private boolean running = false, stopped = false;
    
    /**
     * Associate this State with an Actor
     */ 
    public Patrol(Actor a)
    {
        host = a;
    }  
    
    @Override
    public Actor getHost()
    {
        return host;
    }
    
    @Override
    public void start()
    {
        running = true;
    }
    
    @Override
    public void stop()
    {
        running = false;
    }
    
    @Override
    public boolean isRunning()
    {
        return running;
    }

    @Override
    public boolean isStopped()
    {
        return stopped;       
    }
    
    @Override
    public String toString()
    {
        return "Patrol";
    }
}
