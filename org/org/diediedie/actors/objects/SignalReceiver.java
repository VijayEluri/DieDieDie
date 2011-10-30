package org.diediedie.actors.objects;

import org.diediedie.Entity;

public interface SignalReceiver extends Entity
{
	void receive(Signal s);
}
