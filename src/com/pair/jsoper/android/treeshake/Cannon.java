/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Cannon extends DynamicGameObject {
	public static final float CANNON_VELOCITY = 20;
	public static final float CANNON_WIDTH = 0.4f;
	public static final float CANNON_HEIGHT = 0.4f;

	public Cannon(float x, float y) {
		super(x, y, CANNON_WIDTH, CANNON_HEIGHT);
	}

	public void update(float deltaTime) {
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(velocity.x * deltaTime, 0.0f);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		if (position.x < 0.5)
			position.x = 0.5f;
		if (position.x > World.WORLD_WIDTH - 0.5f)
			position.x = World.WORLD_WIDTH - 0.5f;
	}

}
