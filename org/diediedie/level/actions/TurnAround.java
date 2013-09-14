package org.diediedie.level.actions;

import org.diediedie.level.actors.Actor;
import org.diediedie.level.actors.tools.Direction;
import org.newdawn.slick.Graphics;

public class TurnAround extends Object implements Action 
{
	private Actor host;
	private boolean started;
	private boolean finished;
	
	public void setHost(Actor a)
	{
		host = a;
	}
	
	@Override
	public void perform() 
	{
		Direction newDir = null, d = host.getFacing();
		if(d == Direction.LEFT)
		{
			newDir = Direction.RIGHT;
		}
		else if(d == Direction.RIGHT)
		{
			newDir = Direction.LEFT;
		}
		else
		{
			System.out.println("\n\tARGH! " + "TurnAround.perform() "
					+ "found bad Direction : " + d + "\n"); 
			System.exit(-1);
		}
		host.setFacing(newDir);
		started = true;
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
		return finished;
	}

	@Override
	public void update() 
	{
		// not used
	}

	@Override
	public void draw(Graphics g)
	{
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
