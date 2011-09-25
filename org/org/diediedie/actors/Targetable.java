package org.diediedie.actors;

import java.util.List;

/*
 * Super-interface of all LevelObjects that can be 
 * set to 'target' another. e.g. Switches targetting 
 * a Door. 
 * 
 * This is used for parsing the tiled map object layers.
 */
public interface Targetable extends LevelObject
{
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
