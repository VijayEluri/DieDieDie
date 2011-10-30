package org.diediedie.level.actions;
import org.diediedie.level.actors.Actor;
import org.diediedie.level.actors.tools.Direction;
import org.newdawn.slick.Graphics;

public class StartMoving extends Object implements Action
{
	
	Actor host;
	boolean started, finished;
	Direction d;
	
	public StartMoving()
	{
		reset();
	}
	
	@Override
	public void reset()
	{
		host = null;
		started = false;
		finished = false;
		d = null;
	}
	
	public void setHost(Actor mo)
	{
		host = mo;
	}
	
	public void setDirection(Direction dir)
	{
		d = dir;
	}
	
	@Override
	public void perform() 
	{
		host.applySpeed(d);
		started = true;
		// as this is a one-shot Action, we set finished as well
		finished = true;
	}

	@Override
	public boolean hasStarted()
	{
		return started;
	}

	@Override
	public boolean hasFinished()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g)
	{
		// TODO Auto-generated method stub
		
	}


}
