package org.diediedie;

import java.util.Properties;

import org.diediedie.actors.LevelObject;
import org.diediedie.actors.Radio;

/*
 * 
 */
public class LevelObjectFactory 
{
	enum Type 
	{
		Radio, Elevator // elevator not refactored into here yet
	}
	
	public static LevelObject createObject(Properties props) 
	{
		if(props.get("type").equals(Type.Radio.toString()))
		{
			System.out.println("LevelObjectFactory found a radio");
			return new Radio(props);
		}
		return null;
	}
	
}
