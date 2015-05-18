/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class FallingLeaf extends DynamicGameObject {
	public static final float FALLINGLEAF_WIDTH = 1;
	public static final float FALLINGLEAF_HEIGHT = 0.6f;
	public static final float FALLINGLEAF_VELOCITY = 0;

	float stateTime = 0;

	public FallingLeaf(float x, float y) {
		super(x, y, FALLINGLEAF_WIDTH, FALLINGLEAF_HEIGHT);
		velocity.set(FALLINGLEAF_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(FALLINGLEAF_WIDTH / 2,
				FALLINGLEAF_HEIGHT / 2);

		if (position.x < FALLINGLEAF_WIDTH / 2) {
			position.x = FALLINGLEAF_WIDTH / 2;
			velocity.x = FALLINGLEAF_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - FALLINGLEAF_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - FALLINGLEAF_WIDTH / 2;
			velocity.x = -FALLINGLEAF_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
