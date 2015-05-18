/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Apple extends DynamicGameObject {
	public static final float APPLE_WIDTH = 1;
	public static final float APPLE_HEIGHT = 0.6f;
	public static final float APPLE_VELOCITY = 0;
	public short status;
	public static final short APPLE_ON_TREE = 1;
	public static final short APPLE_FALLING = 2;
	public static final short APPLE_HIT_CANNON = 3;
	public static final short APPLE_OFFSCREEN = 4;
	public double ballastStartTime = 0;

	float stateTime = 0;

	public Apple(float x, float y) {
		super(x, y, APPLE_WIDTH, APPLE_HEIGHT);
		velocity.set(APPLE_VELOCITY, 0);
		status = APPLE_ON_TREE;
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(APPLE_WIDTH / 2, APPLE_HEIGHT / 2);

		velocity.x = APPLE_VELOCITY;
		stateTime += deltaTime;
	}
}