/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Woodpecker extends DynamicGameObject {
	public static final float WOODPECKER_WIDTH = 1;
	public static final float WOODPECKER_HEIGHT = 0.6f;
	public static final float WOODPECKER_VELOCITY = 0;

	float stateTime = 0;

	public Woodpecker(float x, float y) {
		super(x, y, WOODPECKER_WIDTH, WOODPECKER_HEIGHT);
		velocity.set(WOODPECKER_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(WOODPECKER_WIDTH / 2,
				WOODPECKER_HEIGHT / 2);

		if (position.x < WOODPECKER_WIDTH / 2) {
			position.x = WOODPECKER_WIDTH / 2;
			velocity.x = WOODPECKER_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - WOODPECKER_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - WOODPECKER_WIDTH / 2;
			velocity.x = -WOODPECKER_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
