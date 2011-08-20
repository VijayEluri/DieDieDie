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

import java.util.Date;
import org.diediedie.Level;
import org.diediedie.actors.Actor;
import org.diediedie.actors.actions.Look;
import org.diediedie.actors.statemachine.BlueyFSM;
import org.diediedie.actors.statemachine.StateMachine;
import org.diediedie.actors.tools.Direction;
import org.diediedie.actors.tools.Mover;
import org.diediedie.actors.Enemy;
import java.util.Set;
import java.util.HashSet;
import org.diediedie.Tile;
import org.diediedie.actors.tools.AnimCreator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/*
 * Blue stick-enemy wielding 2 pistols. 
 */
public class Bluey extends Object implements Enemy, Observer 
{
	public static final int MAX_HEALTH = 50;

	// viewSize - size of the triangular view shape used for Looking for
	
	private final float viewSize = 500f;
	public final String leftStandPath = "data/bluey_standing_left.png";
	private Graphics g;
	private Date date;
	public final String[] leftWalkPaths =  {"data/bluey_walk_left_1.png",
											"data/bluey_walk_left_2.png", 
											"data/bluey_walk_left_3.png",
											"data/bluey_walk_left_4.png", 
											"data/bluey_walk_left_5.png",
											"data/bluey_walk_left_6.png"};
	
	public final float MAX_Y_SPEED = 20.5f, WALK_SPEED = 1f, RUN_SPEED = 3.1f,
			JUMP_SPEED = -5.5f, EYE_OFFSET_HEIGHT = 5f;

	private long timeLastSawPlayer = 0L;

	private boolean canJump = false, canSeePlayer = false, moving = false,
					hasSeenPlayer = false, seenPlayerEvidence = false;

	private int health, width, height;
	private Direction facing = null;
	private Level level;

	private float lookSeconds = 1.5f;
	// ^ number of seconds spent Looking when in Patrol State.

	private long lastInfoCallTime = 0;
	// ^ used to store the time of the last call to printInfo()

	private Set<LevelObject> visibleObjects;

	private float xPos, 
				  yPos, 
				  tileHeight, 
				  moveSpeed = 0, 
				  oldX, 
				  oldY,
				  xSpeed = 0, 
				  ySpeed = 0;

	private Animation leftWalkAnim, rightWalkAnim, leftStandAnim,
			rightStandAnim, currentAnim = null;

	private StateMachine stateMachine;

	private boolean hitByPlayer = false;

	//private Look look;

	/**
	 * Constructor. The object is associated with a Level and is positioned as
	 * near to Tile t on it as possible.
	 */
	public Bluey(Level l, Tile t) 
	{
		tileHeight = t.tileHeight;
		createAnimations();
		health = MAX_HEALTH;
		//look = new Look(this);
		canSeePlayer = false;
		hasSeenPlayer = false;

		stateMachine = new BlueyFSM(this);
		visibleObjects = new HashSet<LevelObject>();
		setLevel(l);
		xPos = t.xPos;
		yPos = t.yPos;
		yPos -= (AnimCreator.getCurrentFrameRect(this).getHeight() - t.tileHeight);
		yPos--;
		System.out.println("new Bluey enemy at " + xPos + ", " + yPos);
	}

	@Override
	public StateMachine getFSM() 
	{
		return stateMachine;
	}

	@Override
	public float getLookSeconds() 
	{
		return lookSeconds;
	}

	@Override
	public Set<LevelObject> getVisibleObjects()
	{
		return visibleObjects;
	}

	@Override
	public void setLevel(Level l) 
	{
		level = l;
	}

	public void setMoveSpeed(float f)
	{
		moveSpeed = f;
		System.out.println("bluey setMoveSpeed: " + f);
	}

	/*
	 * public void setMoving(boolean m) { moving = m; }
	 */
	@Override
	public Level getLevel()
	{
		return level;
	}

	@Override
	public boolean hitByPlayer()
	{
		return hitByPlayer;
	}

	public void setAsVisibleObject(LevelObject lo) 
	{
		visibleObjects.add(lo);
	}

	@Override
	public final float getWalkSpeed()
	{
		return WALK_SPEED;
	}

	@Override
	public float getViewSize()
	{
		return viewSize;
	}

	@Override
	public final float getRunSpeed() 
	{
		return RUN_SPEED;
	}

	@Override
	public void setFacing(Direction d)
	{
		facing = d;
		if (d.equals(Direction.LEFT)) 
		{
			currentAnim = leftWalkAnim;
		} else {
			currentAnim = rightWalkAnim;
		}
	}

	@Override
	public boolean hasSeenPlayerEvidence()
	{
		return seenPlayerEvidence;
	}

	@Override
	public void setSeenPlayerEvidence(boolean b) 
	{
		seenPlayerEvidence = b;
	}

	@Override
	public void setX(float x) 
	{
		xPos = x;
	}

	@Override
	public void setY(float y) 
	{
		yPos = y;
	}

	@Override
	public void setCanJump(boolean b) 
	{
		canJump = b;
		// System.out.println("bluey: canJump == " + canJump);
	}

	@Override
	public void resetAccelX()
	{
		// do nothing for now
	}

	@Override
	public void resetAccelY() 
	{
		// do nothing for now
	}

	@Override
	public float getMoveSpeed()
	{
		return moveSpeed;
	}

	@Override
	public float getJumpSpeed() 
	{
		return JUMP_SPEED;
	}

	@Override
	public void setYSpeed(float f) 
	{
		ySpeed = f;
	}

	@Override
	public void setXSpeed(float f)
	{
		xSpeed = f;
	}

	@Override
	public Direction getFacing() 
	{
		return facing;
	}

	@Override
	public float getHealth() 
	{
		return health;
	}

	@Override
	public float getYSpeed() 
	{
		return ySpeed;
	}

	@Override
	public float getXSpeed() 
	{
		return xSpeed;
	}

	@Override
	public boolean canJump()
	{
		return canJump;
	}

	private void createAnimations()
	{
		System.out.println("bluey -> creating animations");

		Image leftStand1 = AnimCreator.loadImage(leftStandPath);
		Image rightStand1 = leftStand1.getFlippedCopy(true, false);

		// standing anims
		Image[] leftStandImages = { leftStand1 };
		Image[] rightStandImages = { rightStand1 };

		leftStandAnim = new Animation(leftStandImages, Actor.ANIM_DURATION,
				true);
		rightStandAnim = new Animation(rightStandImages, Actor.ANIM_DURATION,
				true);

		// walking anims
		Image[] leftWalkImages = AnimCreator.getImagesFromPaths(leftWalkPaths)
				.toArray(rightStandImages);
		Image[] rightWalkImages = AnimCreator.getHorizontallyFlippedCopy(
				leftWalkImages).toArray(rightStandImages);
		leftWalkAnim = new Animation(leftWalkImages, Actor.ANIM_DURATION, true);
		rightWalkAnim = new Animation(rightWalkImages, Actor.ANIM_DURATION,
				true);

		facing = Direction.LEFT;
		currentAnim = leftStandAnim;
		// All images (should be) are the same size. Save the height / width
		// from any animation frame as a reference for later.
		width = leftStandImages[0].getWidth();
		height = leftStandImages[0].getHeight();
	}

	@Override
	public int getWidth() 
	{
		return width;
	}

	@Override
	public int getHeight() 
	{
		return height;
	}

	/*
	 * Returns the height of Bluey's Tile in the Map Editor, Tiled.
	 */
	public float getTileHeight() 
	{
		return tileHeight;
	}

	@Override
	/**
	 * Instructs the enemy to jump
	 */
	public void jump() 
	{
		if (canJump()) 
		{
			System.out.println("bluey jump");
			ySpeed = JUMP_SPEED;
			canJump = false;
		}
	}

	@Override
	public Shape getZone()
	{
		return getLevel().getActorZone(this);
	}

	@Override
	public void update()
	{
		if(health == 0) 
		{
			System.out.println(this + " is dead!");
			return;
		}
		
		updatePosition();
		updateProjectiles();
		stateMachine.update();
		// printInfo(5);
	}

	/*
	 * Prints out information about this enemy with a gap of at least as long as
	 * the interval specified.
	 * 
	 * This means that you can stick a call to this method in (for example) in
	 * the main game loop and it won't actually print anything unless at least
	 * 'interval' seconds have passed since you last called it.
	 */
	public void printInfo(int seconds) 
	{
		date = new Date();
		long now = date.getTime();

		if ((now - lastInfoCallTime) >= seconds * 1000)
		{
			System.out.println("Bluey : hashcode " + hashCode()
					+ "\n\thealth\t\t" + health + "\n\tseenPlayer\t"
					+ hasSeenPlayer() + "\n\tseenEvidence\t"
					+ hasSeenPlayerEvidence() + "\n\tcanSeePlayer\t"
					+ canSeePlayer()
			// Add any extra debugging info here :)
					);
			lastInfoCallTime = now;
		}
	}

	/*
	 * Called by Collider when a Projectile, p, impacts an Enemy (in the case of
	 * Arrows, this is when the arrow head hits the Enemy, not any other part,
	 * which will inflict no damage).
	 */
	@Override
	public void doCollision(Projectile p)
	{
		System.out.println(this + "doCollision " + p);
		final float damage = p.getDamage();
		hitByPlayer = true;
		// DevTools.printMethods(p);

		this.health -= damage;
		System.out.println("Projectile caused " + damage + " damage");
		System.out.println("\t" + this + " health : " + this.health);
	}

	public void updatePosition() 
	{
		Mover.applyGravity((Actor) this);
		applyFriction();

		if (isMoving())
		{
			applySpeed(facing);
			if (!Mover.move(this))
			{
				setStandingAnim();
				moving = false;
			}
		}
	}

	// this is just a dupe of the one from Player, give or take...
	private void setStandingAnim() 
	{
		if (getFacing().equals(Direction.RIGHT)) 
		{
			currentAnim = rightStandAnim;
		} else if (getFacing().equals(Direction.LEFT)) 
		{
			currentAnim = leftStandAnim;
		} else
			throw new IllegalStateException(
					"standing dir neither left or right");
	}

	// Woah there!
	private void applyFriction() 
	{
		xSpeed *= Level.FRICTION;
	}

	@Override
	public void applySpeed(Direction dir)
	{
		//System.out.println("Bluey.applySpeed() facing " + dir);
		
		setFacing(dir);
		if(dir.equals(Direction.RIGHT)) 
		{
			xSpeed = moveSpeed;
		} 
		else if(dir.equals(Direction.LEFT)) 
		{
			xSpeed = -moveSpeed;
		}
	}

	@Override
	public float getEyePosX() 
	{
		return xPos + (AnimCreator.getCurrentFrameRect(this).getWidth() / 2);
	}

	@Override
	public float getEyePosY() 
	{
		return yPos + EYE_OFFSET_HEIGHT;
	}

	@Override
	public boolean isMoving() 
	{
		return (xSpeed > 0) || (ySpeed > 0);
	}

	@Override
	public void setHasSeenPlayer(boolean b) 
	{
		hasSeenPlayer = b;
	}

	@Override
	public void setCanSeePlayer(boolean b) 
	{
		canSeePlayer = b;

		if (b && !hasSeenPlayer())
		{
			setHasSeenPlayer(true);
		}
	}

	/*
	 * Returns True if the
	 */
	@Override
	public boolean canSeePlayer()
	{
		return canSeePlayer;
	}

	@Override
	public long getTimeLastSawPlayer()
	{
		return timeLastSawPlayer;
	}

	@Override
	public void setTimeLastSawPlayer(long time) 
	{
		timeLastSawPlayer = time;
	}

	@Override
	public boolean hasSeenPlayer()
	{
		return hasSeenPlayer;
	}

	@Override
	public float getMaxFallSpeed()
	{
		return MAX_Y_SPEED;
	}

	@Override
	public void die() {
		System.out.println("Die, you, " + this);
		health = 0;
		// n i
	}

	/*
     *
     */
	private void updateProjectiles() 
	{
		// for ...
	}

	@Override
	public Animation getCurrentAnim() {
		return currentAnim;
	}

	@Override
	public void draw(Graphics g)
	{
		// System.out.println("drawing Bluey: " + getX() + ", " + getY());
		g.drawAnimation(currentAnim, getX(), getY());
		drawProjectiles(g);
		//look.draw(g);
		// this is just debug code to draw; it won't be here for ever :)
		/*
		 * if (currentState.getClass().equals(patrol.getClass())) { Action act =
		 * patrol.getCurrentAction(); act.draw(g); }
		 */
	}

	public Graphics getGraphics()
	{
		return g;
	}

	private void drawProjectiles(Graphics g) 
	{

	}

	public float getX()
	{
		return xPos;
	}

	public float getY()
	{
		return yPos;
	}

}
