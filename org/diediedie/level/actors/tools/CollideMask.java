package org.diediedie.level.actors.tools;

import java.util.BitSet;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class CollideMask 
{	
	public final int X_SIZE, Y_SIZE;
	
	// We'll store the mask in BitSets. Each 
	private BitSet[] bitRows;
	
	// constant transparent colour for comparisons 
	public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);

	public CollideMask(Image i)
	{
		System.out.println(
				"BitMaskImage.createMask() from image : " 
				+ i);
		X_SIZE = i.getWidth();
		Y_SIZE = i.getHeight();
		createMask(i);		
	}

	private void createMask(Image i) 
	{
		bitRows = new BitSet[Y_SIZE];
		
		for(int y = 0; y < Y_SIZE; ++y)
		{
			// create one bitset for each row of size X_SIZE
			bitRows[y] = new BitSet(X_SIZE);
			
			for(int x = 0; x < X_SIZE; ++x)
			{
				if(!i.getColor(x, y).equals(TRANSPARENT))
				{
					bitRows[y].set(x);
				}
			}
		}
	}
	
	/*
	 * Returns only the bits inside the specified rectanglular area
	 * of the mask.
	 */
	public BitSet getRectBits(final int xPos, 
							  final int widthBits, 
							  final int yPos, 
						      final int heightBits)
	{
		int i = 0;
		
		BitSet bs = new BitSet(widthBits*heightBits);
		
		for(int y = yPos; y < yPos + heightBits; ++y)
		{
			for(int x = xPos; x < xPos + widthBits; ++x)
			{
				bs.set(i, bitRows[y].get(x));
			}
			++i;
		}
		
		return bs;
	}
}
