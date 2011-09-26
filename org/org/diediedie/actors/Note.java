package org.diediedie.actors;


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
public class Note
{
	private LevelSound sound;
	public final Name name;
	public final Type type;
	public final int octave;
	
	public Note(Name n, Type t, int oct, LevelSound s)
	{
		name = n;
		type = t;
		sound = s;
		octave = oct;
	}
	
	public LevelSound getLevelSound()
	{
		return sound;
	}
	
	public void play()
	{
		sound.play();
	}
}