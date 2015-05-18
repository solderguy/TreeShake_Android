/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class BirdFlying extends DynamicGameObject {
	public static final float BIRDFLYING_WIDTH = 1;
	public static final float BIRDFLYING_HEIGHT = 0.6f;
	public static final float BIRDFLYING_VELOCITY = 0;

	float stateTime = 0;

	public BirdFlying(float x, float y) {
		super(x, y, BIRDFLYING_WIDTH, BIRDFLYING_HEIGHT);
		velocity.set(BIRDFLYING_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(BIRDFLYING_WIDTH / 2,
				BIRDFLYING_HEIGHT / 2);

		if (position.x < BIRDFLYING_WIDTH / 2) {
			position.x = BIRDFLYING_WIDTH / 2;
			velocity.x = BIRDFLYING_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - BIRDFLYING_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - BIRDFLYING_WIDTH / 2;
			velocity.x = -BIRDFLYING_VELOCITY;
		}
		stateTime += deltaTime;
	}
}