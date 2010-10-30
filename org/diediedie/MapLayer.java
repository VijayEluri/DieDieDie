package org.diediedie;

import java.io.*;
import java.util.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;


public class MapLayer 
{
    protected List<Tile> tiles;
    protected int index;
    protected boolean isVisible;

    public MapLayer(List<Tile> layerTiles, final int in, 
                    boolean visible)
    {
        tiles = layerTiles;
        index = in;
        isVisible = visible;
        
        System.out.println("MapLayer: tileCount " + tiles.size() +
                            ", index " + index + ", visible: " + 
                            isVisible);
    }

    

}
