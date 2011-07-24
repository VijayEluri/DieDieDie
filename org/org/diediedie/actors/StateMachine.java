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

import org.diediedie.actors.State;

/**
 * A Finite Staet Machine for Enemy behavior.
 *
 * Each State has a series of internal Actions which will be executed 
 * depending on the occurence of certain kinds of events.
 */ 
public interface StateMachine
{
    boolean isFSMRunning();
    State getState();
    
    void createStates();
    void setInitialState();
    void startFSM();
    void changeState(State nextState);
    void stopFSM();
}

