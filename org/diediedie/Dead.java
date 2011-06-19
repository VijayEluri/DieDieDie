package org.diediedie.actors;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.actions.Action;
import java.lang.Class;

public class Dead implements State
{
    Actor host = null;
    boolean started = false;
    Action currentAction = null;
    
    public Dead(Actor actor)
    {
        host = actor;
    }
    
    public Actor getHost()
    {
        return host;
    }
    
    public String toString()
    {
        return "Dead";
    }
    
    public void enter()
    {
        started = true;    
    }
        
    public void exit()
    {
        // probably won't be needed...?
    }
    
    public boolean isRunning()
    {
        return started;
    }    
      
    public void update()
    {
        // rot?
        
    }
    
    public Class getCurrentActionType()
    {
        return currentAction.getClass();
    }    
    
    public Action getCurrentAction()
    {
        return currentAction;
    }






}
