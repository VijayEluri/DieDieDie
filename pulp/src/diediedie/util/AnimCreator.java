/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *      
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301, USA.
 */

package diediedie.util;

import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import pulpcore.image.AnimatedImage;
import pulpcore.image.CoreImage;
import pulpcore.math.Rect;
import diediedie.level.actors.Actor;

/**
 * Utility class with Animation creation and CoreImage handling methods.
 */ 
public class AnimCreator
{
    /*
     * Utility function that creates and returns an animation in which
     * each frame lasts the amount set in 'duration', based upon 
     * the list of file paths given. If flip is true, flip the images 
     * on load. 
     */ 
    public static AnimatedImage createAnimFromPaths(int duration, 
                        /*boolean autoUpdate,*/ String... paths)
    {
        List<CoreImage> images = getImagesFromPaths(paths);
        CoreImage[] imgArr = new CoreImage[images.size()];
        images.toArray(imgArr);
        Animation anim = new Animation(imgArr); //, duration, autoUpdate);
        anim.setFrameDuration(duration);
    }  
                             
    /**
     * Load an CoreImage from a given path String.
     */ 
    public static CoreImage loadImage(String path)
    {
        try
        {
            return new CoreImage(new FileInputStream(
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
    public static List<CoreImage> scaleImages(float scale, CoreImage... imgs)
    {
        List<CoreImage> scaled = getEmptyImageList();
        
        for(CoreImage i : imgs)
        {
            scaled.add(i.getScaledCopy(scale));
        }
        
        return scaled;
    }
    
    public static List<CoreImage> getHorizontallyFlippedCopy(CoreImage[] images)
    {
        List<CoreImage> flipped = getEmptyImageList();        
        for(CoreImage i : images)
        {
            //flipped.add(i.getFlippedCopy(true, false));
            flipped.add(i.mirror());
        }
        return flipped;
    }
    
    public static List<CoreImage> getEmptyImageList()
    {
        return new ArrayList<CoreImage>();
    }
    
    /*
     * Returns an array of Images from a List of paths to image files. 
     */
    public static List<CoreImage> getImagesFromPaths(String... paths)                                     
    {
        List<CoreImage> images = getEmptyImageList();
        for(String p : paths)
        {
            images.add(loadImage(p));               
        }
        return images;
    }
    /**
     * Returns an Animation frame's rectangular bounding box.
     */ 
    public static Rect getCurrentFrameRect(Actor actr)
    {
        CoreImage img = actr.getCurrentAnim().getCurrentFrame();
        return new Rect(actr.getX(), actr.getY(), img.getWidth(), 
                             img.getHeight());
    }
}
