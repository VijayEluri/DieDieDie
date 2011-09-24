package org.diediedie.actors;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public abstract class BaseTargetableObject extends BaseLevelObject 
										   implements Targetable
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
	
	public BaseTargetableObject(Properties p)
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
