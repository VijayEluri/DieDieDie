package org.diediedie.level.actors.tools;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class MaskedAnimation extends Animation
{
	CollideMask[] masks;
	
	public MaskedAnimation(Image[] images, 
						   int animDuration,
						   boolean autoUpdate) 
	{
		super(images, animDuration, autoUpdate);
		createMasks(images);
	}

	private void createMasks(Image[] images) 
	{
		masks = new CollideMask[images.length];
		for(int i = 0; i < images.length; ++i)
		{
			masks[i] = new CollideMask(images[i]);
		}
	}
	
	/*
	 * Return the mask for the current animation 
	 * frame image.
	 */
	public CollideMask getCurrentFrameMask()
	{
		return masks[getFrame()];
	}
}
