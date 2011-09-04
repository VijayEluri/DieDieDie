package org.diediedie;

import java.util.BitSet;

import org.diediedie.actors.tools.AnimCreator;
import org.diediedie.actors.tools.CollideMask;
import org.newdawn.slick.Image;

/*
 * Tests that CollideMasks produce the correct masks for images
 */
class CollideMaskTest
{
	private static Image getTestImage(String path)
	{
		Image testImage = AnimCreator.loadImage(path);
		assert testImage != null;
		return testImage;
	}
	/*
	 * Checks that a completely transparent image creates
	 * a CollisionMask of the correct size with each bit
	 * set to 'false'.
	 */
	public static void test_entirely_transparent_image()
	{
		System.out.println(
			"\ntest_entirely_transparent_image :\n");
		Image testImage = getTestImage(
			"data/transparency_10x10.png");
		assert testImage != null;
		
		/*
		 * First check that the image is DEFINITELY 
		 * COMPLETELY transparent
		 */
		for(int x = 0; x < testImage.getWidth(); ++x)
		{
			for(int y = 0; y < testImage.getHeight(); ++y)
			{
				if(!testImage.getColor(x, y).equals(CollideMask.TRANSPARENT))
				{
					System.out.println(
						"\tArgh! Found colour " + testImage.getColor(x, y)
						+ " at " + x + ", " + y
						+ "\n\t CANNOT RUN TEST!");
					assert false;
				}
			}
		}
		
		/*
		 * Now create a collide mask and check that every
		 * bit is false.
		 */
		boolean[][] bits = new CollideMask(testImage).getBits();
		
		for(int x = 0; x < bits.length; ++x)
		{
			for(int y = 0; y < bits[x].length; ++y)
			{
				assert bits[x][y] == false;
			}
		}
		System.out.println("PASS");
	}
	
	public static void test_no_transparent_pixels_image()
	{
		System.out.println("\ntest_no_transparent_pixels_image :\n");
		
		Image testImage = getTestImage(
				"data/no_transparent_pixels_10x10.png");
		/*
		 * Check there are no transparent pixels in the 
		 * image.
		 */
		for(int x = 0; x < testImage.getWidth(); ++x)
		{
			for(int y = 0; y < testImage.getHeight(); ++y)
			{
				assert !testImage.getColor(x, y).equals(
						CollideMask.TRANSPARENT);
			}
		}	
		
		/*
		 * Check that all returned bits == true
		 */
		boolean[][] bits = new CollideMask(testImage).getBits();
		
		int wrongs = 0;
		
		for(int x = 0; x < bits.length; ++ x)
		{
			for(int y = 0; y < bits[x].length; ++y)
			{
				if(bits[x][y] == false)
				{
					++wrongs;
					System.out.println(
						"\tpixel at " + x + ", " + y + " is FALSE!!");
				}
				else
				{
					System.out.println(
						"\tpixel at " + x + ", " + y + " is true (ok)");
				}
			}
		}
		if(wrongs > 0)
		{
			System.out.println("FAIL : " + wrongs + " incorrect bits.");
			assert false;
		}
		System.out.println("PASS");
	}
	
	public static void test_top_left_not_transparent_image()
	{
		System.out.println("\ntest_top_left_not_transparent_image :\n");
		
		Image testImage = getTestImage(
				"data/top_left_not_transparent.png");
		assert testImage != null;
		
		/*
		 * Check that only the top left of the image is 
		 * non-transparent.
		 */
		for(int x = 0; x < testImage.getWidth(); ++x)
		{
			for(int y = 0; y < testImage.getHeight(); ++y)
			{
				if(x == 0 && y == 0)
				{
					assert !testImage.getColor(x, y).equals(
							CollideMask.TRANSPARENT);
				}
				else
				{
					assert testImage.getColor(x, y).equals(
							CollideMask.TRANSPARENT);
				}
			}
		}
		/*
		 * Now check the bits. Only the first bit in the first row
		 * should be true;
		 */
		boolean[][] bits = new CollideMask(testImage).getBits();
		
		for(int x = 0; x < bits.length; ++x)
		{
			for(int y = 0; y < bits[x].length; ++y)
			{
				if(x == 0 && y == 0)
				{
					assert bits[x][y] == true;
				}
				else
				{
					assert bits[x][y] == false;
				}
			}
		}
		
		System.out.println("PASS");
	}
	
	public static void test_get_rect_bits()
	{
		
	}
}