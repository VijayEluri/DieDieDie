package org.diediedie.level.actors;

import java.util.Properties;
import java.util.Set;

import org.diediedie.Entity;
import org.diediedie.level.Point;
import org.diediedie.level.actors.statemachine.StateMachine;
import org.diediedie.level.actors.tools.Direction;
import org.diediedie.level.objects.Projectile;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/*
 * A grunt on a Factory Level that does some inane, repetitive 
 * task.
 */
public class FactoryGrunt extends BaseLevelObject implements Enemy
{
    
    public FactoryGrunt(Properties p)
    {
        System.out.println("new FactoryGrunt");
        parseBaseObject(p);
    }
    
    @Override
    public void applySpeed(Direction d)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canJump()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void die()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Animation getCurrentAnim()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Direction getFacing()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float getHealth()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setCanJump(boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void startJump()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFacing(Direction d)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int getWidth()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHeight()
    {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public Rectangle getCollisionBox()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOutOfBounds()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setOutOfBounds(boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public float getXSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getYSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setYSpeed(float y)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setXSpeed(float x)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public float getMaxFallSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void resetAccelX()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetAccelY()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<Entity> getVisibleObjects()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float getLookSeconds()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getEyePosX()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getEyePosY()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setCanSeePlayer(boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setTimeLastSawPlayer(long time)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAsVisibleObject(Entity lo)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canSeePlayer()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasSeenPlayer()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setLocationLastSeenPlayer(Shape sh)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Shape getLocationLastSeenPlayer()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasSeenPlayerEvidence()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setSeenPlayerEvidence(boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setHasSeenPlayer(boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public long getTimeLastSawPlayer()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getViewSize()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isMoving()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public float getWalkSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getRunSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void doCollision(Projectile p)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Graphics getGraphics()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StateMachine getFSM()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hitByPlayer()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setGoto(Point p)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Point getGoto()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
