package org.diediedie.level.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import org.newdawn.slick.Image;

/*
 * An (sometimes) interactable object that toggles
 * or calls another object (such as an Elevator).
 * 
 * A switch moves an object between two states.
 */
public class Switch extends BaseTransmitter 
{
	protected SignalReceiver target;
	protected List<Signal> signals;

	
	public Switch(Properties p)
	{
		super(p);
		System.out.println("creating switch " + getName());
		String switchTypeStr = (String) props.get("switchtype");
		signals = new ArrayList<Signal>();
		
		if(switchTypeStr.equals("updown"))
		{
			signals.add(Signal.Up);
			signals.add(Signal.Down);
		}
		else
		{
			System.out.println(
				"Unexpected switch type " + switchTypeStr);
			System.exit(-1);
		}
	}
	
	@Override
	public void transmit(Signal s, List<SignalReceiver> srs)
	{
		if(!isValidSignal(s))
		{
			return;
		}
		
		// TODO
	}
	
	
	
	private boolean isValidSignal(Signal s) 
	{
		if(signals.contains(s))
		{
			return true;
		}
		return false;
	}




	@Override
	public void addTarget(SignalReceiver sr) 
	{
		target = sr;		
	}
}
