package org.diediedie.level.actions;

import org.newdawn.slick.Graphics;

public class StopMoving extends Object implements Action
{
	boolean started = false, finished = false;
	
	@Override
	public void perform() 
	{
		started = true;
		
	}

	@Override
	public boolean hasStarted() 
	{
		return started;
	}

	@Override
	public boolean hasFinished() 
	{
		return finished;
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void draw(Graphics g)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() 
	{
		// TODO Auto-generated method stub

	}

}
