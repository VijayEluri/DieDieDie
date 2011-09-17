package org.diediedie;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.diediedie.actors.Actor;
import org.diediedie.actors.Bluey;
import org.diediedie.actors.Enemy;
import org.diediedie.actors.Player;
import org.diediedie.actors.tools.Direction;
import org.newdawn.slick.Graphics;


/*
 * Contains the Player, NPCs, Enemies, items.
 * 
 * (Doesn't include 'interactable scenery' such as Elevators.
 */
public class PlayerLayer implements DrawableLayer, UpdatableLayer
{
	protected MapLayer mapLayer;
	
	protected List<ObjectLayer> objectLayers;
	protected List<Enemy> enemiesLiving;
	protected List<Actor> npcs;
	protected List<ArrowBouncer> bouncers;

	protected Tile startTile;

	protected List<Tile> exitTiles; 

	protected Player player;
	
	public PlayerLayer(MapLayer ml)
	{
		mapLayer = ml;
		assert !mapLayer.visible;
		parsePlayerLayerTiles();
	}
	
	public void setObjectLayers(List<ObjectLayer> objLayers)
	{
		objectLayers = objLayers;
	}
	
	/**
     * Draw using Graphics object g any visible enemiesLiving. 
     */ 
    private void drawEnemies(Graphics g)
    {
        for(Enemy e : enemiesLiving)
        {
            e.draw(g);
        }
    }
    
	@Override
	public void update() 
	{
		updateObjects();
		player.update();	
		updateEnemies();
	}

	private void updateObjects() 
	{
		for(ObjectLayer o : objectLayers)
		{
			o.update();
		}
	}

	private void drawPlayer(Graphics g)
	{
		player.draw(g);
	}

	@Override
	public void draw(/*int x, int y,*/ Graphics g) 
	{
		drawObjectLayers(g);
		drawEnemies(g);
		drawPlayer(g);
	}
	
	private void drawObjectLayers(Graphics g)
	{
		for(ObjectLayer o : objectLayers)
		{
			o.draw(g);
		}
	}


	/*
	 * Returns only the enemies still living on this layer.
	 */
	public List<Enemy> getLivingEnemies()
	{
		return enemiesLiving;
	}
	
	/*
	 * Parse the Player tiles (start, exit...)
	 */
	private void parsePlayerLayerTiles()
	{
		exitTiles = new ArrayList<Tile>();
		enemiesLiving = new ArrayList<Enemy>();
		
		for(Tile t : mapLayer.tiles)
	    {
			if(t.properties.get("type").equalsIgnoreCase("exit"))
	        {
	            exitTiles.add(t);
	        }
			else if(t.properties.get("type").equalsIgnoreCase("start"))
			{
				startTile = t;
			}
	    }
		player = new Player(mapLayer.level, startTile);
		assert startTile != null;
	}
	
	/**
     * Updates the behaviour and position etc of all enemiesLiving on the
     * level
     */ 
    private void updateEnemies()
    {
        ListIterator<Enemy> lit = enemiesLiving.listIterator();
        
        while(lit.hasNext())
        {
            Enemy e = lit.next();
            if(e.getHealth() <= 0 || e.isOutOfBounds())
            {
                e.die();
                System.out.println("Removing " + e);
                lit.remove();
            }
            else
            {
                e.update();
            }
        }
    }
}
