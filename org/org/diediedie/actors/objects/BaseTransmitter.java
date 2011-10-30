package org.diediedie.actors.objects;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.diediedie.actors.BaseLevelObject;

public abstract class BaseTransmitter extends BaseLevelObject 
										   implements Transmitter
{
	/*
	 * targetNames - names of the targets that this
	 * object needs to connect to, as parsed from
	 * the space-separated property string from the 
	 * map file. 
	 * 
	 * This is needed to connect this object to the
	 * others in its 'network' after all the Level's
	 * objects have been created.
	 */
	List<String> targetNames;
	
	public BaseTransmitter(Properties p)
	{
		parseBaseObject(p);
		getTargetStrings();
	}
	
	@Override
	public List<String> getTargetNames()
	{
		return targetNames;
	}
	
	/*
	 * Parses the 'targets' field from the map object's properties
	 */
	private void getTargetStrings()
	{
		String[] names = ((String) props.get("targets")).split(" ");
		assert names != null;
		targetNames = Arrays.asList(names);
		assert targetNames != null;
	}
}
