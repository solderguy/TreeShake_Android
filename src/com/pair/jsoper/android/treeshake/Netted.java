/**
 * @author John Soper
 * @version 1   Nov 24 2014
 */

package com.pair.jsoper.android.treeshake;

import com.pair.jsoper.android.framework.DynamicGameObject;

public class Netted extends DynamicGameObject {
	public static final float NETTED_WIDTH = 1;
	public static final float NETTED_HEIGHT = 0.6f;
	public static final float NETTED_VELOCITY = 0;

	float stateTime = 0;

	public Netted(float x, float y) {
		super(x, y, NETTED_WIDTH, NETTED_HEIGHT);
		velocity.set(NETTED_VELOCITY, 0);
	}

	public void update(float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(NETTED_WIDTH / 2, NETTED_HEIGHT / 2);

		velocity.x = NETTED_VELOCITY;
		stateTime += deltaTime;
	}
}