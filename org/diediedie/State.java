package org.diediedie.actors;

public interface State
{
    public void start();
    public void stop();
    public void changeState(State next);
}
