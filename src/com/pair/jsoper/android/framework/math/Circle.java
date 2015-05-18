/**
 * @author Mario Zechner
 * 
 * Gaming framework provided from book "Beginning Android Games"
 */

package com.pair.jsoper.android.framework.math;

public class Circle {
	public final Vector2 center = new Vector2();
	public float radius;

	public Circle(float x, float y, float radius) {
		this.center.set(x, y);
		this.radius = radius;
	}
}
