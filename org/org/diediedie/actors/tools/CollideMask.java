package org.diediedie.actors.tools;


import org.diediedie.Drawable;
import org.diediedie.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CollideMask 
{	
	public final int X_SIZE, Y_SIZE;
	
	// We'll store the mask in BitSets. Each 
	private boolean[][] bitRows;
	
	// constant transparent colour for comparisons 
	public static final Color TRANSPARENT = new Color(
		0.0f, 0.0f, 0.0f, 0.0f);

	public CollideMask(Image i)
	{
		/*System.out.println(
				"BitMaskImage.createMask() from image : "
				+ i);*/
		X_SIZE = i.getWidth();
		Y_SIZE = i.getHeight();
		createMask(i);
	}

	private void createMask(Image i) 
	{
		bitRows = new boolean[X_SIZE][Y_SIZE];
		
		/*System.out.println(
				"Creating Mask for image size " +
				i.getWidth() + " by " + i.getHeight());
		System.out.println("creating " + Y_SIZE + " bit rows...");*/
		
		for(int x = 0; x < X_SIZE; ++x)
		{
			for(int y = 0; y < Y_SIZE; ++y)
			{
				if(!i.getColor(x, y).equals(TRANSPARENT))
				{
					bitRows[x][y] = true;
					/*System.out.println(
						"\tPixel " + x + ", " + y 
						+ " not transparent, setting true");*/
				}
			}
		}
	}
	
	
	
	/*
	 * Returns only the bits inside the specified rectanglular area
	 * of the mask.
	 */
	public boolean[] getRectBits(final int xPos, 
							  	 final int widthBits, 
							  	 final int yPos, 
							  	 final int heightBits)
	{
		if(heightBits <= 0 || widthBits <= 0)
		{
			return new boolean[0];
			
		}
/*		System.out.println(
			"getRectBits : xPos " + xPos 
			+ ", width " + widthBits 
			+ ", ypos " + yPos
			+ ", height " + heightBits);
		
*/
		boolean[] bits = new boolean[widthBits*heightBits];
		
		/*System.out.println(
			"CollideMask.getRectBits : will return "
			+ bits.length + " bits");*/
		
		int i = 0;

		for(int x = xPos; x < xPos + widthBits; ++x)
		{
			for(int y = yPos; y < yPos + heightBits; ++y)
			{
				bits[i++] = bitRows[x][y];
			}
		}
		return bits;
	}
	
	public boolean[][] getBits()
	{
		return bitRows;
	}
	
}
