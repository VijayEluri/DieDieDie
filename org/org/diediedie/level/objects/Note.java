package org.diediedie.level.objects;

import org.diediedie.level.actors.LevelSound;
import org.newdawn.slick.SlickException;


enum Name
{
	C, D, E, F, G, A, B
}

enum Type
{
	Natural, Sharp, Flat
}


/*
 * A note played by an Instrument.
 */
public class Note extends LevelSound
{
	public final Name name;
	public final Type type;
	public final int octave;
	
	public Note(Name n, Type t, int oct, String path) throws SlickException
	{
		super(path);
		name = n;
		type = t;
		octave = oct;
	}
	
	/*
	 * Two Notes are the same if they have the same
	 * pitch name and type (e.g. they are both C sharp,
	 * despite possibly being different objects and having
	 * different slick.Sound objects)
	 */
	@Override
	public boolean equals(Object other)
	{
		Note n = (Note)other;
		if(this.name == n.name 
				&& 
		   this.type == n.type)
		{
			return true;
		}
		return false;
	}
	
	/*
	 * A more picky version of equals() that demands
	 * matching octaves as well.
	 */
	public boolean equalSameOctave(Object other)
	{
		Note n = (Note)other;
		if(equals(n))
		{
			return n.octave == octave;
		}
		return false;
	}
}