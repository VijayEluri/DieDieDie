package org.diediedie.actors;

/*
 * Super-Interface for all LevelObjects that can 
 * be interacted with by Callers(Actor)
 */
public interface Callable extends LevelObject 
{
	/*
	 * call - Requires a calling object, for
	 * interaction 
	 * */
	void call(Caller c);
}
