package org.diediedie.actors;

import java.util.ArrayList;
import java.util.List;

public class Bagpipes implements PlayerItem, Instrument
{
	private List<Note> notes = new ArrayList<Note>();
	
	
	
	public Bagpipes()
	{
		
	}
	
	
	@Override
	public void play(Note note)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Note getNoteAt(int index)
	{
		// TODO Auto-generated method stub
		return null;
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
		
	}


	@Override
	public void putAway()
	{
		// TODO Auto-generated method stub
		
	}

}
