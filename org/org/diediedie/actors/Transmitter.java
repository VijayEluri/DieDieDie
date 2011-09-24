package org.diediedie.actors;

import java.util.List;

/*
 * A LevelObject that can send Signals to one or more
 * SignalReceivers.
 */
public interface Transmitter extends Targetable
{
	void transmit(Signal s, List<SignalReceiver> srs);
}
