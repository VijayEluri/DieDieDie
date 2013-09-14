package org.diediedie;

import java.util.ArrayList;
import java.util.List;

import org.diediedie.level.MapLayer;
import org.newdawn.slick.Graphics;

enum GraphicsMode 
{
    NORMAL (null, Graphics.MODE_NORMAL),
    BLEND ("blend", Graphics.MODE_ALPHA_BLEND),
    ADD ("add", Graphics.MODE_ADD),
    SCREEN ("screen", Graphics.MODE_SCREEN),
    MULTIPLY ("multiply", Graphics.MODE_COLOR_MULTIPLY),
    MAP ("map", Graphics.MODE_ALPHA_MAP);
    
    public final String name;
    public final Integer modeNumber;

    GraphicsMode(String name, int modeNumber)
    {
        this.name = name;
        this.modeNumber = modeNumber;
    }
} 


/*
 * Optional base class that implements basic
 * routines most sub-classes will need. 
 */
public abstract class BaseLayer implements LevelLayer
{
    protected Graphics graphics;
    protected MapLayer mapLayer;
    protected int drawMode;
    protected final String DRAW_MODE_STRING = "drawmode";
    private List<Entity> objects;	
    
    public BaseLayer(MapLayer ml) 
    {
        mapLayer = ml;
        parseGraphicsMode();
        objects = new ArrayList<Entity>();
    }
    
    @Override
    public List<Entity> getObjects()
    {
        return objects;
    }

    @Override
    public String getName()
    {
        return mapLayer.getName();
    }
    
    protected void drawObjects(Graphics g)
    {
        for(Entity l : objects)
        {
            l.draw(g);
        }
    }
    
    public void draw(Graphics g)
    {
        drawScenery(g);
        drawObjects(g);
    }
    
    public void setObjects(List<Entity> l)
    {
        objects = l;
        assert objects != null;
    }
    
    private void parseGraphicsMode() 
    {
        assert mapLayer != null;
        final String drawStr = mapLayer.getLevel().getLayerProperty(
                                 mapLayer.getIndex(), DRAW_MODE_STRING, 
                                 null);
        
        //System.out.println("parseGraphicsMode layer " 
          //                 + mapLayer.getIndex() + ", " + drawStr);
        
        if (drawStr == null)
        {
            drawMode = GraphicsMode.NORMAL.modeNumber;
        }
        else 
        {
            for (GraphicsMode gm : GraphicsMode.values())
            {
                if (drawStr.equals(gm.name))
                {
                    drawMode = gm.modeNumber;
                }
            }
       }  
    }
    
    /*
     * Draws the content of the TiledMap Layer
     * in the draw mode parsed from the map.
     */
    protected void drawScenery(Graphics g) 
    {
        mapLayer.getLevel().render(0, 0, mapLayer.getIndex());
        g.setDrawMode(Graphics.MODE_NORMAL);
    }
}
