package org.diediedie.actors;

public interface SignalReceiver extends LevelObject
{
	void receive(Signal s);
}
