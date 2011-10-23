package org.diediedie.actors;

import java.util.List;

/*
 * 
 */
public interface Instrument 
{
	public void play(int noteIndex);
	public List<Note> getNotes();
}
