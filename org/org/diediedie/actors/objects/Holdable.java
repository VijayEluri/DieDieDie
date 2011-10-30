package org.diediedie.actors.objects;

import org.diediedie.Drawable;

/*
 * Any object that can *VISIBLY* be held by a game character.
 * 
 * This is ONLY for drawing / animating purposes.
 */
public interface Holdable extends Drawable
{
	public void hold();
	public void putAway();
}
