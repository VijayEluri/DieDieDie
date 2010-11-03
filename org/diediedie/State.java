package org.diediedie.actors;

public interface State
{
    /*
     * Begin execution
     */ 
    public void start();
    /**
     * End execution
     */ 
    public void stop();
    public String toString();
}
