package org.diediedie.level.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.diediedie.level.Level;
import org.diediedie.level.actors.BaseLevelObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/*
 * 
 */
public class Bagpipes extends BaseLevelObject 
	                  implements PlayerItem, Instrument, Holdable
{
	private List<Note> notes = new ArrayList<Note>();
		
	public Bagpipes(Properties p)
	{
		parseBaseObject(p);
		name = "Bagpipes" + hashCode(); 
		System.out.println("new " + this);
	}
	
	@Override
	public List<Note> getNotes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hold() 
	{
		// TODO Auto-generated method stub
		System.out.println(this + " hold()");
		startDrone();
	}

	private void startDrone() 
	{
		// TODO Auto-generated method stub
		System.out.println(this + " started playing drone");
		
	}

	@Override
	public void putAway()
	{
		// TODO Auto-generated method stub
		System.out.println(this + " putAway()");
		stopDrone();
	}

	private void stopDrone() 
	{
		// TODO Auto-generated method stub
		System.out.println(this + " stopped playing drone");
	}

	@Override
	public String getName() 
	{	
		return "Bagpipes" + hashCode();
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play(int noteIndex)
	{
		// TODO Auto-generated method stub
		System.out.println(this + " play() note " + noteIndex);
	}
}
