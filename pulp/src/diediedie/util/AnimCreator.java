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
                        String[] paths)
    {
        List images = getImagesFromPaths(paths);
        CoreImage[] imgArr = new CoreImage[images.size()];
        images.toArray(imgArr);
        //Animation anim = new Animation(imgArr); //, duration, autoUpdate);
        AnimatedImage anim = new AnimatedImage(imgArr); //, duration, autoUpdate);
        //anim.setFrameDuration(duration);
        boolean loop = true;
        anim.setFrameDuration(duration, loop);
        return anim;
    }  
                             
/*
    *
     * Load an CoreImage from a given path String.
   
*/  
    public static CoreImage loadImage(String path)
    {
        return CoreImage.load(path);
    }
    
    /**
     * Returns a cloned, scaled version of a given list by a given
     * scale. 
     
    public static List scaleImages(float scale, CoreImage... imgs)
    {
        List scaled = getEmptyImageList();
        
        for(CoreImage i : imgs)
        {
            scaled.add(i.getScaledCopy(scale));
        }
        
        return scaled;
    }*/ 
    
    public static List getHorizontallyFlippedCopy(CoreImage[] images)
    {
        List flipped = getEmptyImageList();        
        //for(CoreImage i : images)
        for(int i = 0; i < image.length; ++i)
        {
            //flipped.add(i.getFlippedCopy(true, false));
            flipped.add(images.get(i).mirror());
        }
        return flipped;
    }
    
    public static List getEmptyImageList()
    {
        return new ArrayList();
    }
    
    /*
     * Returns an array of Images from a List of paths to image files. 
     */
    public static List getImagesFromPaths(String[] paths)                                     
    {
        List images = getEmptyImageList();
        //for(String p : paths)
        for(int i = 0; i < paths.length; ++i)
        {
            images.add(loadImage(paths.get(i)));               
        }
        return images;
    }
    /**
     * Returns an Animation frame's rectangular bounding box.
     */ 
    public static Rect getCurrentFrameRect(Actor actr)
    {
        CoreImage img = actr.getCurrentImage();//.getCurrentFrame();
        return new Rect((int)actr.getX(), (int)actr.getY(), 
                        img.getWidth(), img.getHeight());
    }
}
