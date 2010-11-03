package org.diediedie.actors;

public interface Enemy
{
    void setStates();
    void attack();
    void flee();
    
    /**
     * Returns the Direction the Enemy is facing
     */ 
    Direction getFacing();
    boolean canSeePlayer(float playerX, float playerY);       
}
