package org.diediedie.actors;

import java.util.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.io.*;

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
