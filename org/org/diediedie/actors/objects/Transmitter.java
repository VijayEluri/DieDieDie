package org.diediedie.actors.objects;

import java.util.List;


/*
 * A LevelObject that can send Signals to one or more
 * SignalReceivers.
 */
public interface Transmitter
{
    
	void transmit(Signal s, List<SignalReceiver> srs);

    /*
    	 * Used during Level initialization, to
    	 * hook this transmitter up with receivers read
    	 * from the map file.
    	 * 
    	 * The format must be a list of space-separated names,
    	 * under the property name 'targets'.
    	 */
	List<String> getTargetNames();
	void addTarget(SignalReceiver sr);
}
