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
 
package diediedie.level;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import diediedie.level.NavMesh;
import diediedie.level.NavMesh.MeshMaker;
import diediedie.level.Direction;
import diediedie.level.actors.Actor;
import diediedie.level.actors.Bluey;
import diediedie.util.AnimCreator;
import diediedie.level.actors.Player;
// Still using the TiledMap from Slick. So there. >#:-)
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;
import pulpcore.math.Rect;
import pulpcore.sprite.Sprite;
import pulpcore.image.CoreGraphics;
import pulpcore.CoreSystem;
import pulpcore.scene.Scene2D;
import org.newdawn.slick.SlickException;
/**
 * A Level in a DieDieDie.
 */ 
public class Level extends Scene2D
{
    // Obviously, the exit point of the map.
    public float exitX, exitY;
    
    // Friction. Lower number means greater friction (hence being < 1).
    // The horizontal speed is multiplied (i.e. shrunk) by this.
    public static final float FRICTION = 0.91f;
    
    // It's the other way around with gravity because it's added to the
    // Actors' vertical speed (ySpeed)
    public float gravity;
    
    // The initial direction the player is facing.
    public final int playerFacing;
    
    private String name, tileSetsPath;
    
    // I had the idea of adding a navigation mesh to levels for dealing
    // with AI pathfinding. Might happen, one day... :D
    private NavMesh navMesh;
    
    private final String VIS_STR = "isvisible", TRUE_STR = "true", 
                         FALSE_STR = "false", PLATFORM_STR = "platforms";
    
    private final int NOT_PRESENT = 0;
    
    private MapLayer collisionLayer, objectLayer, backgroundLayer, 
                     platformLayer;
        
    private Player player;
    private List/*<Actor>*/ enemies;
    private Tile playerTile = null;
    private TiledMap map;
    /**
     * Create a Level
     */ 
    public Level(String name, 
                 InputStream in, 
                 String tileSetsPath, 
                 int facing, float grav) throws SlickException
    {
        super();
        this.map = new TiledMap(name, tileSetsPath);
        this.tileSetsPath = tileSetsPath;
        this.name = name;
        this.gravity = grav;
        this.playerFacing = facing;
        
        CoreSystem.print("Level " + name + " has " 
                        + map.getLayerCount() 
                        + " tile layers");
        
        collisionLayer = createMapLayer(
                            map.getLayerIndex("collisions"));
        objectLayer = createMapLayer(
                        map.getLayerIndex("objects"));
        platformLayer = createMapLayer(
                            map.getLayerIndex("platforms"));
        backgroundLayer = createMapLayer(
                            map.getLayerIndex("background"));    
        enemies = new ArrayList();
        sortObjects();
        createNavMesh();
    }   
    
    private void createNavMesh()
    {
        navMesh = MeshMaker.generateMesh(this);
    }
        
    public void associatePlayer(Player p)
    {
        add(p.getSprite());
        player = p;        
    }
    
    public MapLayer getBackgroundLayer()
    {
        return backgroundLayer;
    }
    
    /**
     * Returns the Line in the nav mesh that this actor is in/on, if
     * one exists
     */ 
    public Rect getActorZone(Actor a)
    {
        Rect r = AnimCreator.getCurrentFrameRect(a);
        Rect[] rects = 
            (Rect[])getNavMesh().getNegativeSpace().toArray();
        for(int i = 0; i < rects.length; ++i)
        {
            if(rects[i].intersects(r))
            {
                return rects[i];
            }
        }
        return null;
    }
    
    public Player getPlayer()
    {
        return player;
    }
    
    public MapLayer getCollisionLayer()
    {
        return collisionLayer;
    }
    
    /**
     * Returns the Tile associated with the Player's start position on 
     * this Level.
     */ 
    public Tile getPlayerTile()
    {
        return playerTile;   
    }
    
    /*
     * Returns all enemies + the player
     */ 
    //public List<Actor> getActors()
    public List getActors()
    {
        //List<Actor> actors = new ArrayList<Actor>(enemies);
        List actors = new ArrayList(enemies);
        actors.add(player);
        return actors;
    }
    
    /**
     * Updates the contents of the level, if any.
     */ 
    public void update()
    {
        updateEnemies();
    }
    
    
    
    /**
     * Updates the behaviour and position etc of all enemies on the
     * level
     */ 
    public void updateEnemies()
    {
        /*for(Actor a : enemies)
        {
            a.update();
        }*/
        java.util.Iterator it = enemies.iterator();
        while(it.hasNext())
        {
            Actor a = (Actor)it.next();
            a.update();
        }
    }

    /*
     * Sorts the object layer into separate structures
     */ 
    private void sortObjects()
    {
        //for(Tile t : objectLayer.tiles)
        for(int i = 0; i < objectLayer.tiles.size(); ++i)
        {
            Tile t = (Tile)objectLayer.tiles.get(i);
            try
            {                
                if(t.properties.get("type").equals("exit"))
                {
                    exitX = t.xPos;
                    exitY = t.yPos;
                }
                else if(
                    t.properties.get("type").equals("start"))
                {
                    playerTile = t;
                }
                else if(
                    t.properties.get("type").equals("enemy"))
                {
                    System.out.print("read enemy... ");
                    CoreSystem.print(" name: " 
                                       + t.properties.get("name"));
                    
                    if(t.properties.get(
                        "name").equals("bluey"))
                    {                       
                        enemies.add(new Bluey(this, t));
                    }
                }
            }
            catch(NullPointerException e)
            {
                //CoreSystem.print("sortObjects: ignoring np exception");
                e.printStackTrace();
            }
        }
        CoreSystem.print("level has " + enemies.size() + " enemies");
    }
    
    /**
     * Returns the name of this Level.
     */ 
    public String toString()
    {
        return name;
    }
    
    public NavMesh getNavMesh()
    {
        return navMesh;
    }
    
    /*
     * Creates a map layer by retrieving the data from the Tiled file
     * at the given index
     */ 
    private MapLayer createMapLayer(int index)
    {
        List/**/ tiles = new ArrayList/**/();  
        for(int x = 0; x < map.getWidth(); x++)
        {
            for(int y = 0; y < map.getHeight(); y++)
            {
                if(map.getTileId(x, y, index) != NOT_PRESENT)
                {
                    tiles.add(new Tile(map, x, y, index));
                }
            }
        }   
        
        final boolean VISIBLE; 
        final String v = map.getLayerProperty(
                                index, VIS_STR, Tile.NULL);
        
        if(v.equals(TRUE_STR))
        {
            VISIBLE = true;
        }
        else
        {
            VISIBLE = false;
        }
        return new MapLayer(tiles, index, VISIBLE);
    }
    
    /**
     * Returns true if two Shapes intersect.
     */ 
/*
    public boolean intersection(Shape p1, Shape p2)
    {
        return p1.intersects(p2);
    }
*/
        
/*
    public void render(int x, int y)
    {// old slick code
        // keep the ordering!
        render(x, y, backgroundLayer.index);
        render(x, y, platformLayer.index);
    }
*/
    
    /**
     * Draw using CoreGraphics object g this Level at 0,0 plus any 
     * inhabiting enemies. 
     */ 
/*
    public void draw(CoreGraphics g)
    { //old slick c ode
        render(0, 0);
        navMesh.draw(g);
        drawEnemies(g);
    }
    
*/
    /**
     * Draw using CoreGraphics object g any visible enemies. 
     */ 
    private void drawEnemies(CoreGraphics g)
    {
        //for(Actor a : enemies)
        for(int i = 0; i < enemies.size(); ++i)
        {
            Actor a = (Actor)enemies.get(i);
            a.draw(g);
        }
    }
    
    /**
     * Returns true if Rect r intersects with a collision tile on the
     * Level.
     */
    public boolean collides(Sprite s)//Rect r)
    {
        //for(Tile t : collisionLayer.tiles)
        for(int i = 0; i < collisionLayer.tiles.size(); ++i)
        {
            Tile t = (Tile)collisionLayer.tiles.get(i);
            //if(t.getRect().intersects(s))
            if(t.intersects(s))
            {
                
                CoreSystem.print("[Rect [origin " + 
                                    new Throwable().fillInStackTrace()
                                    .getStackTrace()[3].getFileName()
                                      + "] collision with Tile " +
                                    t.xCoord + ", " + t.yCoord
                                    + "]"); 
                return true;
            }
        }
        return false;
    }
    
   /*
    * Returns true if the x / y coordinate position supplied is inside
    * a collision tile
    */
    public boolean isInCollisionTile(int x, int y)
    {
        //for(Tile t : collisionLayer.tiles)
        for(int i = 0; i < collisionLayer.tiles.size(); ++i)
        {
            Tile t = (Tile)collisionLayer.tiles.get(i);
            
            if(t.getRect().contains(x, y))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the collision Tile containing the given point, or null.
     */
    public Tile getCollisionTileAt(/*float*/int x, /*float*/int y)
    {
        //for(Tile t : collisionLayer.tiles)
        for(int i = 0; i < collisionLayer.tiles.size(); ++i)
        {
            Tile t = (Tile)collisionLayer.tiles.get(i);
            if(t.getRect().contains(x, y))
            {
                return t;
            }   
        }
        return null;
    }
    
}
