package org.diediedie.actors;

public interface Enemy
{
    void attack();
    
    void flee();
    
    boolean canSeePlayer(float playerX, float playerY);
    
    
    
}
