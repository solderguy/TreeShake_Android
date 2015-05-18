/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Leaf extends DynamicGameObject {
	public static final float LEAVES_WIDTH = 1;
	public static final float LEAVES_HEIGHT = 0.6f;
	public static final float LEAVES_VELOCITY = 0;

	float stateTime = 0;

	public Leaf(float x, float y) {
		super(x, y, LEAVES_WIDTH, LEAVES_HEIGHT);
		velocity.set(LEAVES_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(LEAVES_WIDTH / 2,
				LEAVES_HEIGHT / 2);

		velocity.x = LEAVES_VELOCITY;
		stateTime += deltaTime;
	}
}