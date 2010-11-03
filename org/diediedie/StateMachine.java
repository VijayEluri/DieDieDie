package org.diediedie.actors;

import org.diediedie.actors.State;
import java.util.List;
import java.util.ArrayList;

public class StateMachine
{
    private State currentState = null;
    
    List<State> states;
    /**
     * Creates a state machine with a list of states
     */ 
    public StateMachine(List<State> possibleStates)
    {
        states = possibleStates;
    }
    
    public void setState(State nextState)
    {
        
    }
    
    public State getState()
    {
        return null;
        //return currentState;
    }
    
}
