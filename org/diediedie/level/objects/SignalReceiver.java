package org.diediedie.level.objects;

import org.diediedie.Entity;

public interface SignalReceiver extends Entity
{
	void receive(Signal s);
}
