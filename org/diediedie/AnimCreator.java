/*
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

package org.diediedie.actors;

import java.util.List;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Utility class with Animation creation and Image handling methods.
 */ 
public class AnimCreator
{
    /*
     * Utility function that creates and returns an animation in which
     * each frame lasts the amount set in 'duration', based upon 
     * the list of file paths given. If flip is true, flip the images 
     * on load. 
     */ 
    public static Animation createAnimFromPaths(int duration, 
                                    boolean autoUpdate, String... paths)
    {
        List<Image> images = getImagesFromPaths(paths);
        Image[] imgArr = new Image[images.size()];
        images.toArray(imgArr);
        return new Animation(imgArr, duration, autoUpdate);
    }  
                             
    /**
     * Load an Image from a given path String.
     */ 
    public static Image loadImage(String path)
    {
        try
        {
            return new Image(new FileInputStream(
                                    new File(path)), path, false);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SlickException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Returns a cloned, scaled version of a given list by a given
     * scale. 
     */ 
    public static List<Image> scaleImages(float scale, Image... imgs)
    {
        List<Image> scaled = getEmptyImageList();
        
        for(Image i : imgs)
        {
            scaled.add(i.getScaledCopy(scale));
        }
        
        return scaled;
    }
    
    public static List<Image> getHorizontallyFlippedCopy(Image[] images)
    {
        List<Image> flipped = getEmptyImageList();        
        for(Image i : images)
        {
            flipped.add(i.getFlippedCopy(true, false));
        }
        return flipped;
    }
    
    public static List<Image> getEmptyImageList()
    {
        return new ArrayList<Image>();
    }
    
    /*
     * Returns an array of Images from a List of paths to image files. 
     */
    public static List<Image> getImagesFromPaths(String... paths)                                     
    {
        List<Image> images = getEmptyImageList();
        for(String p : paths)
        {
            images.add(loadImage(p));               
        }
        return images;
    }
    /**
     * Returns an Animation frame's rectangular bounding box.
     */ 
    public static Rectangle getCurrentFrameRect(Actor actr)
    {
        Image img = actr.getCurrentAnim().getCurrentFrame();
        return new Rectangle(actr.getX(), actr.getY(), img.getWidth(), 
                             img.getHeight());
    }
}
