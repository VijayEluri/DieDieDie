package org.diediedie.actors;

/*
 * A LevelObject that can send and receive Signals.
 * 
 * Must be connected to at least one other Transmitter 
 * to be functional.
 */
public interface Transmitter extends LevelObject
{
	void transmit(Signal s);
	void receiveTransmission(Signal s);
	
	/*
	 * Connects two transmitters together
	 */
	void connect(Transmitter t);
}
