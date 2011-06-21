package org.diediedie.actors;


/*
 * Defines the special methods for all non-player LevelObjects that use
 * Look Actions.
 */ 
public interface Observer
{
    // Returns the time in seconds that this Observer should spend
    // performing their Look Action. Note this will depend on their current state. 
    int getLookSeconds();
}
