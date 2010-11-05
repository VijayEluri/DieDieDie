package org.diediedie.actors.actions;

import org.diediedie.actors.actions.Action;
import org.diediedie.actors.Actor;


/**
 *
 */
public class Look implements Action
{
    private boolean started = false, finished = false;
    
    public void setUp(Actor a)
    {
        
    }
    public void performAction()
    {
        
    }
    
    public boolean hasStarted()
    {
        return started;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }

}
