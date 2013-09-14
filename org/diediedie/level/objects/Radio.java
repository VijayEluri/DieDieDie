package org.diediedie.level.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.diediedie.level.Tile;
import org.diediedie.level.Level;
import org.diediedie.level.actors.BaseLevelObject;
import org.diediedie.level.actors.tools.AnimCreator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/*
 * A Radio used for two-way communication. Both
 * transmissions occur simultaneously
 */
public class Radio extends BaseLevelObject implements Transmitter, 
													   SignalReceiver	   
{
	public enum Color
	{
		Red, Blue, Green
	}
	
	protected Radio.Color visColor;

	protected Transmitter source;
	protected List<SignalReceiver> receivers;
	protected List<String> targetNames;
	
	public Radio(Properties p) 
	{
		// perhaps refactor some of this into BaseLevelObject
		System.out.println("Creating Radio "/*+ p*/);
		parseBaseObject(p);
		System.out.println("position : " + getX() + ", " + getY());
		visColor = getVisColor((String)p.get("visColor"));
		System.out.println("Radio.Color : " + visColor);
		receivers = new ArrayList<SignalReceiver>();
		getTargetString();
	}
	
	public void getTargetString()
	{
		String[] names = ((String) props.get("targets")).split(" ");
		assert names != null;
		targetNames = Arrays.asList(names);
		assert targetNames != null;
	}
	
	/*
	 * Get the visible colour of this Radio.
	 * 
	 */
	public Radio.Color getVisColor(String s)
	{
		for(Radio.Color c : Radio.Color.values())
		{
			if(s.equalsIgnoreCase(c.toString()))
			{
				return c;
			}
		}
		return null;
	}
	
	
	@Override
	public void draw(Graphics g)
	{
		//System.out.println("drawing radio :" + name);
		super.draw(g);
		// draw visColor on top?
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	}

	/*
	 * Receive a signal. 
	 * 
	 * This will only do something if it's the 
	 * correct type of Signal for this Radio.
	 */
	@Override
	public void receive(Signal s) 
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void transmit(Signal s, List<SignalReceiver> srs) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getTargetNames()
	{
		return targetNames;
	}
	
	/*
	 * 
	 */
	@Override
	public void addTarget(SignalReceiver sr)
	{
		receivers.add(sr);
	}
	
}