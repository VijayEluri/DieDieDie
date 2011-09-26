package org.diediedie.actors;

import java.util.List;

/*
 * 
 */
public interface Instrument
{
	public void play(Note note);
	public Note getNoteAt(int index);
	public List<Note> getNotes();
	
	public void hold();
	public void putAway();
}
