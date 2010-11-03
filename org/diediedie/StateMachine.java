package org.diediedie.actors;

import org.diediedie.actors.State;
import java.util.List;
import java.util.ArrayList;

/**
 * A basic (finite) state machine
 */ 
public interface StateMachine
{
    
    public void setState(State nextState);  
    
    /*
     * Returns the current state
     */     
    public State getState();    
}
