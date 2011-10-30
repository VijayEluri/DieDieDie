package org.diediedie.level.objects;

import java.util.List;


/*
 * 
 */
public interface Instrument 
{
	public void play(int noteIndex);
	
	/*
	 * Returns a List containing the notes that
	 * this Instrument can play.
	 */
	public List<Note> getNotes();
}
