package org.diediedie.actors;

import java.util.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
                                         
 /*   public static Animation createAnimFromImages(int duration, 
                                    boolean autoUpdate, 
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
    /*
     * Returns an array of Images from a List of paths to image files. 
     */
    public static List<Image> getImagesFromPaths(String... paths)                                     
    {
        List<Image> images = new ArrayList<Image>();
        
        for(String p : paths)
        {
            images.add(loadImage(p));               
        }
        return images;
    }
}
